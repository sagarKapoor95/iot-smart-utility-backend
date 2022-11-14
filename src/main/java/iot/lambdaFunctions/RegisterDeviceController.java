package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.converter.DeviceInfoConverter;
import iot.exception.CentralHubNotFoundException;
import iot.repository.*;
import iot.request.RegisterDeviceRequest;
import iot.configuration.AWSDynamoDbBean;
import iot.service.CentralIoTHubService;
import iot.service.DeviceService;
import iot.service.LoginSignUpService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The Register device controller.
 */
public class RegisterDeviceController implements RequestHandler<APIGatewayProxyRequestEvent,
        APIGatewayProxyResponseEvent> {
    private DeviceService deviceService;
    private LoginSignUpService signUpService;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        init();
        final var headers = getHeaders();
        final var request = JsonUtil.deSerialize(input.getBody(), RegisterDeviceRequest.class);
        final var response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        final var token = input.getHeaders().get("token");
        final var hubId = input.getPathParameters().get("hubId");

        if(token == null || token.equals("") || !this.signUpService.validateToken(token)) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        try {
            final var deviceInfo = this.deviceService.registerDevice(hubId, request);
            final var body =  JsonUtil.serialize(deviceInfo);
            return response
                    .withBody(body)
                    .withStatusCode(200);
        } catch (CentralHubNotFoundException e) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody(e.getMessage())
                    .withStatusCode(HttpStatus.SC_BAD_REQUEST);
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
        final var hubAndDeviceMappingRepository = new HubAndDeviceMappingRepository(table);
        final var centralIoTHubRepository = new CentralIoTHubRepository(table);
        final var userRepository = new UserRepository(table);
        this.signUpService = new LoginSignUpService(userRepository);
        this.deviceService =
                new DeviceService(new DeviceInfoRepository(table), centralIoTHubRepository, hubAndDeviceMappingRepository, null, null);
    }
}
