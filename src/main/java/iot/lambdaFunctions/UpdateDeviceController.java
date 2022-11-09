package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.converter.DeviceInfoConverter;
import iot.repository.DeviceInfoRepository;
import iot.repository.UserRepository;
import iot.request.UpdateDeviceRequest;
import iot.service.LoginSignUpService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The Update device controller.
 */
public class UpdateDeviceController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>  {
    private DeviceInfoRepository deviceInfoRepository;
    private LoginSignUpService signUpService;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        init();
        final var headers = getHeaders();
        final var request = JsonUtil.deSerialize(input.getBody(), UpdateDeviceRequest.class);
        assert request != null;
        var deviceInfoEntity = deviceInfoRepository.getDeviceInfo(request.getId());

        final var token = input.getHeaders().get("token");

        if(token == null || token.equals("") || !this.signUpService.validateToken(token)) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        if (deviceInfoEntity == null) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Device id not found")
                    .withStatusCode(404);
        }

        deviceInfoEntity = DeviceInfoConverter.toDeviceInfoEntity(request, deviceInfoEntity);
        final var deviceInfo = this.deviceInfoRepository.saveDeviceInfo(deviceInfoEntity);

        final var response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            final var body =  JsonUtil.serialize(deviceInfo);
            return response
                    .withBody(body)
                    .withStatusCode(200);
        } catch (Exception e) {
            final var body = Map.of("body", "internal server error",
                    "error", e.toString());

            return response
                    .withBody(JsonUtil.serialize(body))
                    .withStatusCode(500);
        }
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }

    private void init() {
        final var table = AWSDynamoDbBean.connectDynamoDB();
        this.deviceInfoRepository = new DeviceInfoRepository(table);
        final var userRepository = new UserRepository(table);
        this.signUpService = new LoginSignUpService(userRepository);
    }
}
