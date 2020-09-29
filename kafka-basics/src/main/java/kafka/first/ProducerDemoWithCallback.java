package kafka.first;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {
    public static void main(String[] args) {
        String bootstrapServers="127.0.0.1:9092";

        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);
        // Create Producer properties
        Properties properties =new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String,String> producer= new KafkaProducer<String, String>(properties);
        for(int i=0;i<10;i++){

            // Create a producer record
            ProducerRecord<String,String> record = new ProducerRecord<String, String>("first_topic","hello world!!!" + Integer.toString(i));

            // send data - asynchronous
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e==null){
                        // the record is successfull
                        logger.info("REcieve new metadata. \n"+
                                "Topic: "+recordMetadata.topic()+"\n"+
                                "Partition: "+recordMetadata.partition()+"\n"+
                                "Offset: "+recordMetadata.offset()+"\n"+
                                "Timestamp: "+recordMetadata.timestamp());

                    } else {
                        logger.error("Error while producing",e);
                    }
                }
            });

        }

        // flush data
        producer.flush();

        // flush and close producer
        producer.close();
    }
}
