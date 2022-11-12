package iot.lambdaFunctions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import iot.configuration.AWSDynamoDbBean;
import iot.processor.DevicesInfoProcessor;
import iot.repository.*;
import org.apache.http.HttpStatus;

public class DeviceInfoProcessorController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    DevicesInfoProcessor processor;

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        init();
        processor.processDevicesInfo();
        return new APIGatewayProxyResponseEvent()
                .withBody("Unauthorized user")
                .withStatusCode(HttpStatus.SC_OK);
    }

    private void init() {
        final var table = AWSDynamoDbBean.connectDynamoDB();
        final var repository = new DevicesInfoRepository(table);
        this.processor = new DevicesInfoProcessor(repository);
    }
}
