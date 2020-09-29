### Start zookeeper

    bin/zookeeper-server-start.sh config/zookeeper.properties

### Start kafka server

    kafka-server-start.sh config/server.properties

**_ CLI _**

**TOPIC**

### Create a topic

    kafka-topics.sh --zookeeper  127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1

### List topics

    kafka-topics.sh --zookeeper 127.0.0.1:2181 --list

### Delete topic

    kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second_topic --delete

### Describe topic

    kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second_topic --describe


**KAFKA CONSOLE PRODUCER**

### Start Producer
    kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first_topic --producer-property acks=all


### Start Producer with topic not created.
    kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first_topic
        
<!-- It will create a topic with the name with default config( partion=1 and replication factor=1) -->

<!-- To change the default properties  go to config/server.properties and update the values like : num.partitions=3 -and restart kafka server -->

**KAFKA CONSOLE CONSUMER**

### Start Consumer

    kafka_2.12-2.6.0]$ kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic 
    
    (--from-beginning)// for start from beginning

### Consumer Group
    kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application

### List consumer group
    kafka-consumer-groups --bootstrap-server loaclhost:9092 --list

### To view offset of portitions of consumer groups

    kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --describe --group my-first-application

<!-- Consumer group 'my-first-application' has no active members.

GROUP                TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
my-first-application first_topic     0          4               4               0               -               -               -
my-first-application first_topic     1          3               3               0               -               -               -
my-first-application first_topic     2          3               3               0               -               -   -->

### Reset offset for a consumer group for a topic 
    kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --group my-first-application --reset-offsets --to-earliest --execute --topic first_topic

<!-- GROUP                          TOPIC                          PARTITION  NEW-OFFSET     
my-first-application           first_topic                    0          0              
my-first-application           first_topic                    1          0              
my-first-application           first_topic                    2          0  -->


### Reset offset for a consumer group

	kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --group kafka-demo-elasticsearch --reset-offsets --execute --to-earliest --topic twitter_tweets
