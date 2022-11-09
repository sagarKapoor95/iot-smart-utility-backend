package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.logs.model.ResourceAlreadyExistsException;
import iot.configuration.AWSDynamoDbBean;
import iot.repository.*;
import iot.service.CentralIoTHubService;
import iot.service.DeviceService;
import iot.service.LoginSignUpService;
import iot.service.ResourceUtilizationPlanService;
import iot.utility.JsonUtil;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Get resource utilization plan controller.
 */
public class GetResourceUtilizationPlanController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    /**
     * The Resource utilization plan service.
     */
    ResourceUtilizationPlanService resourceUtilizationPlanService;
    /**
     * The Sign up service.
     */
    LoginSignUpService signUpService;
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        init();
        final var headers = getHeaders();
        final var token = input.getHeaders().get("token");
        final var response = new APIGatewayProxyResponseEvent().withHeaders(headers);
        final var id = input.getPathParameters().get("deviceId");

        if (token == null || token.equals("") || !this.signUpService.validateToken(token)) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("Unauthorized user")
                    .withStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        try {
            final var devices = resourceUtilizationPlanService.getResourceUtilizationPlanEntity(id);
            final var body = JsonUtil.serialize(devices);
            return response
                    .withBody(body)
                    .withStatusCode(200);
        } catch (ResourceAlreadyExistsException e) {
            return new APIGatewayProxyResponseEvent()
                    .withHeaders(headers)
                    .withBody("utilization plan not found")
                    .withStatusCode(HttpStatus.SC_NOT_FOUND);
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
        final var repository = new ResourceUtilizationPlanRepository(table);
        UserAndCentralIoTHubMappingRepo userAndCentralIoTHubMappingRepo = new UserAndCentralIoTHubMappingRepo(table);
        CentralIoTHubRepository centralIoTHubRepository = new CentralIoTHubRepository(table);
        final var centralIoTHubService =
                new CentralIoTHubService(userAndCentralIoTHubMappingRepo, centralIoTHubRepository, signUpService, null, null);
        final var hubAndDeviceMappingRepository = new HubAndDeviceMappingRepository(table);
        this.signUpService = new LoginSignUpService(userRepository);
        final var deviceService =
                new DeviceService(new DeviceInfoRepository(table), centralIoTHubService, hubAndDeviceMappingRepository) ;
        this.resourceUtilizationPlanService = new ResourceUtilizationPlanService(repository, deviceService);
    }
}
