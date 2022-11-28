package iot.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.apache.http.impl.client.BasicCredentialsProvider;

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
        final var client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(AWS_DYNAMODB_URL, "us-east-1"))
                .build();
        final var db = new DynamoDB(client);
        return db.getTable(DYNAMODB_TABLE_NAME);
    }
}
