package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.repository.UserRepository;
import iot.request.SignUpRequest;
import iot.service.LoginSignUpService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Login controller.
 */
public class LoginController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private LoginSignUpService signUpService;


    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        init();
        final var request = JsonUtil.deSerialize(input.getBody(), SignUpRequest.class);
        final var token = this.signUpService.login(request.getUserName(), request.getPassword());
        final var headers = getHeaders();

        if (token == null) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        final var response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            final var body =  JsonUtil.serialize(token);
            return response
                    .withBody(body)
                    .withStatusCode(HttpStatus.SC_OK);
        } catch (Exception e) {
            final var body = Map.of("body", "internal server error",
                    "error", e.toString());

            return response
                    .withBody(JsonUtil.serialize(body))
                    .withStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
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
        final var userRepository = new UserRepository(table);
        this.signUpService = new LoginSignUpService(userRepository);
    }
}
