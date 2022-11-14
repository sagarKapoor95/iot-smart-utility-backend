package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.processor.PlanConsumptionProcessor;
import iot.repository.*;
import iot.service.CentralIoTHubService;
import iot.service.DeviceService;
import iot.service.LoginSignUpService;
import iot.service.ResourceUtilizationPlanService;

import java.util.HashMap;
import java.util.Map;

public class PlanConsumptionProcessorController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    PlanConsumptionProcessor processor;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {

        final var headers = getHeaders();
        final var response =
                new APIGatewayProxyResponseEvent().withHeaders(headers);
        init();
        processor.processData();
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

    private void init() {
        final var table = AWSDynamoDbBean.connectDynamoDB();
        UserAndCentralIoTHubMappingRepo userAndCentralIoTHubMappingRepo = new UserAndCentralIoTHubMappingRepo(table);

        final var centralIoTHubRepository = new CentralIoTHubRepository(table);
        final var deviceInfoRepository = new DeviceInfoRepository(table);
        final var devicesInfoRepository = new DevicesInfoRepository(table);
        final var hubAndDeviceMappingRepository = new HubAndDeviceMappingRepository(table);
        final var repository = new ResourceUtilizationPlanRepository(table);
        final var userRepository = new UserRepository(table);

        final var signUpService = new LoginSignUpService(userRepository);
        final var resourceUtilizationPlanService = new ResourceUtilizationPlanService(repository, deviceInfoRepository);
        final var deviceService =
                new DeviceService(deviceInfoRepository, centralIoTHubRepository, hubAndDeviceMappingRepository, repository, devicesInfoRepository);
        final var centralIoTHubService =
                new CentralIoTHubService(userAndCentralIoTHubMappingRepo, centralIoTHubRepository, signUpService, deviceService, hubAndDeviceMappingRepository, resourceUtilizationPlanService);
        this.processor = new PlanConsumptionProcessor(centralIoTHubService, devicesInfoRepository);
    }
}
