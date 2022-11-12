package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.bo.DevicesInfo;
import iot.configuration.AWSDynamoDbBean;
import iot.exception.CentralHubNotFoundException;
import iot.repository.DevicesInfoRepository;
import iot.service.DevicesInfoService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class RegisterDevicesDataController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    DevicesInfoService service;
    private final static String fixedToken = "gy93ud5w";

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        init();
        final var request = JsonUtil.deSerialize(input.getBody(), DevicesInfo.class);
        final var headers = getHeaders();
        final var token = input.getHeaders().get("token");
        final var response = new APIGatewayProxyResponseEvent().withHeaders(headers);


        if (token == null || !token.equals(fixedToken)) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        try {
            final var data = service.registerDevicesInfo(request);
            final var body = JsonUtil.serialize(data);
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
        final var repository = new DevicesInfoRepository(table);
        this.service = new DevicesInfoService(repository);
    }
}
