package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.processor.DailyConsumptionProcessor;

import java.util.HashMap;
import java.util.Map;

public class PlanConsumptionProcessorController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    DailyConsumptionProcessor processor;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {

        final var headers = getHeaders();
        final var response =
                new APIGatewayProxyResponseEvent().withHeaders(headers);

        return response
                .withBody("sagar")
                .withStatusCode(200);
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }
}
