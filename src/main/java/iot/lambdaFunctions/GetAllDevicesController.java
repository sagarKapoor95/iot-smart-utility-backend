package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.exception.UserNotFoundException;
import iot.repository.*;
import iot.service.CentralIoTHubService;
import iot.service.DeviceService;
import iot.service.LoginSignUpService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Get all devices controller.
 */
public class GetAllDevicesController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private CentralIoTHubService centralIoTHubService;
    private LoginSignUpService signUpService;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        init();
        final var headers = getHeaders();
        final var token = input.getHeaders().get("token");
        final var response = new APIGatewayProxyResponseEvent().withHeaders(headers);

        if (token == null || token.equals("") || !this.signUpService.validateToken(token)) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        try {
            final var devices = centralIoTHubService.getAllDevices(token);
            final var body = JsonUtil.serialize(devices);
            return response
                    .withBody(body)
                    .withStatusCode(200);
        } catch (UserNotFoundException e) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
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
        final var userRepository = new UserRepository(table);
        final var userAndCentralIoTHubMappingRepo = new UserAndCentralIoTHubMappingRepo(table);
        final var deviceInfoRepository = new DeviceInfoRepository(table);
        final var hubAndDeviceMappingRepository = new HubAndDeviceMappingRepository(table);
        final var centralIoTHubRepository = new CentralIoTHubRepository(table);
        this.signUpService = new LoginSignUpService(userRepository);
        this.centralIoTHubService =
                new CentralIoTHubService(userAndCentralIoTHubMappingRepo, centralIoTHubRepository,
                        signUpService, deviceInfoRepository, hubAndDeviceMappingRepository, null);
    }
}
