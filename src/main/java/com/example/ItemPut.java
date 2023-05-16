package com.example;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import java.util.HashMap;

public class ItemPut {

    public static void main( String[] args) {

        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();



        // tableName - The Amazon DynamoDB table in which an item is placed
        // key - The key used in the Amazon DynamoDB table
        // keyval - The key value that represents the item to get - if not present, put will be create

        // albumTitle - The Album title (for example, AlbumTitle)
        // SongTitle - The song title (for example, SongTitle)
        // SongTitleVal - The value of the song title

        String tableName = "TT1";

        String key = "Id";
        String keyVal = "1";

        String albumTitle = "albumTitle";
        String albumTitleVal = "album1";
        String songTitle = "songTitle";
        String songTitleVal = "song1";

        HashMap<String,AttributeValue> itemValues = new HashMap<>();
        itemValues.put(key, AttributeValue.builder().s(keyVal).build());
        itemValues.put(songTitle, AttributeValue.builder().s(songTitleVal).build());
        itemValues.put(albumTitle, AttributeValue.builder().s(albumTitleVal).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(itemValues)
                .build();

        try {
            PutItemResponse response = ddb.putItem(request);
            System.out.println(tableName +" was successfully updated. The request id is "+response.responseMetadata().requestId());

        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The Amazon DynamoDB table \"%s\" can't be found.\n", tableName);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }



        ddb.close();
    }
}
