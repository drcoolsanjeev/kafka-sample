package com.github.com.drcoolsanjeev.kafka.streams;

import com.google.gson.JsonParser;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class StreamsFilterTweets {

    //gson library
    private static JsonParser jsonParser = new JsonParser();
    private static  Integer extractUserFollowerFromTweetd(String tweetJson){
        // use gson library
        try{
            return jsonParser.parse(tweetJson).getAsJsonObject().get("user").getAsJsonObject().get("followers_count").getAsInt();
        } catch (NullPointerException e) {
            return 0;
        }

    }

    public static void main(String[] args) {
        // Create propertiews

        String bootstrapServers="127.0.0.1:9092";
        String groupId = "kafka-demo-elasticsearch";

        Properties properties =new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG,"demo-kafka-streams");
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());


//        Create a topology
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        // input topic
        KStream<String,String> inputTopic = streamsBuilder.stream("tweeter_tweets");
        KStream<String,String> filteredStream = inputTopic.filter(
                // filter for tweets which has a user of over 1000 followers
                (k,jsonTweet)-> extractUserFollowerFromTweetd(jsonTweet)>1000);

        filteredStream.to("important_tweets");

//        Build a topology
        KafkaStreams kafkaStreams = new KafkaStreams(
                streamsBuilder.build(),properties
        );

//        Start a application
        kafkaStreams.start();
    }
}
