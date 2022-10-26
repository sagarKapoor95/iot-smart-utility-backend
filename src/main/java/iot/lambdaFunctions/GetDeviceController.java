package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.repository.DeviceInfoRepository;
import iot.utility.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * The Get device controller.
 */
public class GetDeviceController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private DeviceInfoRepository deviceInfoRepository;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        init();
        Map<String, String> headers = getHeaders();
        System.out.println(input);
        final var id = input.getPathParameters().get("id");
        final var deviceInfo = this.deviceInfoRepository.getDeviceInfo(id);

        if (deviceInfo == null) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Device id not found")
                    .withStatusCode(404);
        }

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
    }
}
