package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.processor.PlanConsumptionProcessor;
import iot.repository.*;
import iot.service.CentralIoTHubService;
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

        CentralIoTHubRepository centralIoTHubRepository = new CentralIoTHubRepository(table);
        DeviceInfoRepository deviceInfoRepository = new DeviceInfoRepository(table);
        DevicesInfoRepository devicesInfoRepository = new DevicesInfoRepository(table);
        HubAndDeviceMappingRepository hubAndDeviceMappingRepository = new HubAndDeviceMappingRepository(table);
        ResourceUtilizationPlanRepository repository = new ResourceUtilizationPlanRepository(table);
        final var userRepository = new UserRepository(table);
        final var signUpService = new LoginSignUpService(userRepository);
        ResourceUtilizationPlanService resourceUtilizationPlanService = new ResourceUtilizationPlanService(repository, deviceInfoRepository);
        final var centralIoTHubService =
                new CentralIoTHubService(userAndCentralIoTHubMappingRepo, centralIoTHubRepository, signUpService, deviceInfoRepository, hubAndDeviceMappingRepository, resourceUtilizationPlanService);
        this.processor = new PlanConsumptionProcessor(centralIoTHubService, devicesInfoRepository);
    }
}
