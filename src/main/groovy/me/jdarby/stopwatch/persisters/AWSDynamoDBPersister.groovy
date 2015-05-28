package me.jdarby.stopwatch.persisters

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.ItemCollection
import com.amazonaws.services.dynamodbv2.document.QueryOutcome
import com.amazonaws.services.dynamodbv2.document.Table
import com.google.common.base.Stopwatch
import me.jdarby.stopwatch.StopwatchRecord

import java.time.Instant

/**
 * Created by jdarby on 5/24/15.
 */
class AWSDynamoDBPersister implements Persister {

    DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
            new ProfileCredentialsProvider()))
    Table stopwatches = dynamoDB.getTable('Stopwatch')

    @Override
    def addRecord(StopwatchRecord stopwatchRecord) {
        insert(stopwatchRecord)
    }

    def insert(StopwatchRecord stopwatchRecord) {
        Item item = new Item()
            .withPrimaryKey('id', stopwatchRecord.id)
            .withString('startTime', stopwatchRecord.startTime.toString())
        if(stopwatchRecord?.endTime != null) item.withString('endTime', stopwatchRecord?.endTime?.toString())
        if(stopwatchRecord?.duration != null) item.withNumber('duration', stopwatchRecord?.duration)
        if(stopwatchRecord?.parentId != null) item.withString('parentId', stopwatchRecord?.parentId)
        stopwatches.putItem(item)
    }

    @Override
    StopwatchRecord getById(String id) {
        Item item = stopwatches.getItem('id', id)
        String startTimeString = item.getString('startTime')
        Instant startTime = startTimeString == null ? null : Instant.parse(startTimeString)
        String endTimeString = item.getString('endTime')
        Instant endTime = endTimeString == null ? null : Instant.parse(endTimeString)
        String durationString = item.getString('duration')
        Long duration = durationString == null ? null : item.getLong('duration')
        String parentId = item.getString('parentId')
        new StopwatchRecord(id: id, startTime: startTime, endTime: endTime, duration: duration, parentId: parentId)
    }

    @Override
    List<StopwatchRecord> getChildrenByParentId(String parentId) {
        ItemCollection<QueryOutcome> items = stopwatches.getIndex('parentId-index').query('parentId', parentId)
        items.asList().collect { Item item ->
            String startTimeString = item.getString('startTime')
            Instant startTime = startTimeString == null ? null : Instant.parse(startTimeString)
            String endTimeString = item.getString('endTime')
            Instant endTime = endTimeString == null ? null : Instant.parse(endTimeString)
            String durationString = item.getString('duration')
            Long duration = durationString == null ? null : item.getLong('duration')
            new StopwatchRecord(id: item.getString('id'), startTime: startTime, endTime: endTime, duration: duration,
                parentId: parentId)
        }
    }

}
