package iot.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

import static iot.configuration.ConfigConstant.*;

/**
 * The Beans.
 */
public class AWSDynamoDbBean {

    /**
     * Connect dynamo db table.
     *
     * @return the dynamodb table
     */
    public static Table connectDynamoDB() {
        AWSCredentials cred = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        final var client = new AmazonDynamoDBClient(cred);
        client.setRegion(REGION);
        client.withEndpoint(AWS_DYNAMODB_URL);
        final var db = new DynamoDB(client);
        return db.getTable(DYNAMODB_TABLE_NAME);
    }
}
