package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.exception.UserNotFoundException;
import iot.repository.CentralIoTHubRepository;
import iot.repository.UserAndCentralIoTHubMappingRepo;
import iot.repository.UserRepository;
import iot.request.RegisterCentralIoTHubRequest;
import iot.service.CentralIoTHubService;
import iot.service.LoginSignUpService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Register central io t hub controller.
 */
public class RegisterCentralIoTHubController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private CentralIoTHubService centralIoTHubService;
    private LoginSignUpService signUpService;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        init();
        final var headers = getHeaders();
        final var token = input.getHeaders().get("token");
        final var response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        if (token == null || token.equals("") || this.signUpService.validateToken(token)) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        final var request =
                JsonUtil.deSerialize(input.getBody(), RegisterCentralIoTHubRequest.class);


        try {
            final var entity = centralIoTHubService.registerCentralIoTHub(token, request);
            final var body = JsonUtil.serialize(entity);
            return response
                    .withBody(body)
                    .withStatusCode(HttpStatus.SC_OK);
        } catch (UserNotFoundException exception) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody(exception.getMessage())
                    .withStatusCode(HttpStatus.SC_BAD_REQUEST);
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody(e.toString())
                    .withStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void init() {
        final var table = AWSDynamoDbBean.connectDynamoDB();
        final var userRepository = new UserRepository(table);
        this.signUpService = new LoginSignUpService(userRepository);
        UserAndCentralIoTHubMappingRepo userAndCentralIoTHubMappingRepo = new UserAndCentralIoTHubMappingRepo(table);
        CentralIoTHubRepository centralIoTHubRepository = new CentralIoTHubRepository(table);
        this.centralIoTHubService =
                new CentralIoTHubService(userAndCentralIoTHubMappingRepo, centralIoTHubRepository, signUpService, null, null);
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return headers;
    }
}
