# Basic Kafka Learning Documentation
This documentation contains steps to learn the basics of Apache Kafka using the CentOS-based Linux operating system

## Deploy kafka service using Systemd
### 1. Install & Configuration
#### Install java/jdk
Apache Kafka requires Java to be installed. You can install OpenJDK using the following command
  ```
  sudo yum install java-1.8.0-openjdk
  ```
Check java version 
```
java -version
```
![java version](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/0e74fad5-d7b7-4b8a-997e-ca8daba71385)

#### Add User
* Create a Kafka User
  Create a user **Kafka** for running Kafka. User name can be costumized
  ```
  sudo useradd kafka -m
  ```
* Create password for user that we create
  ```
  sudo passwd kafka
  ```
* Add user **Kafka** to sudo group. User name can be costumized
  ```
  sudo adduser kafka 
  ```
* Login to user **Kafka** 
  ```
  sudo su - kafka
  ```
#### Install Apache Kafka
* Create directory for kafka installer
  ```
  mkdir ~/Downloads
  ```
* Download kafka using wget
  You can download the latest version of Apache Kafka use wget to download it directly to your server.
  You can see version from this web https://kafka.apache.org/downloads
  ```
  wget https://downloads.apache.org/kafka/3.7.0/kafka_2.12-3.7.0.tgz
  ```
* Create kafka folder and change directory to kafka folder
  ```
  mkdir ~/kafka && cd ~/kafka
  ```
* Extract kafka tar from download folder to kafka folder (now at kafka folder)
  ```
  tar -xzf ~/Downloads/kafka_2.12-3.7.0.tgz --strip 1
  ```
  ![tar kafka zip](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/f53802da-626e-4e23-9177-3eee4df0e74b)

### 2. Edit Kafka Configuration 
The server.properties file is a configuration file used by Apache Kafka brokers
- Mengubah file configuration di file server.properties
- broker.id: Each Kafka broker in a cluster must have a unique ID
- listeners:  Specifies the network interfaces and ports that the broker should listen on for incoming connections from producers and consumers.
- log.dirs: Specifies the directory where Kafka stores its log files, which contain the records published to topics

In this case, I will only edit the log.dirs section. Before it, determine where the Kafka log directory will be stored. I will create new directory **data/kafka-logs**
Create new directory 
```
mkdir -p ~/data/kafka-logs
```
To edit server.properties file use the command below
```
sudo nano config/server.properties
```
Change log.dirs setting as below
```
log.dirs=/home/kafka/data/kafka-logs
```

### 3.Create init Service Kafka using Systemd
Using systemd to manage Apache Kafka on CentOS Linux makes it easier to handle the Kafka service, including starting, stopping, and automatically restarting it if it fails.
To run Apache Kafka using systemd along with Zookeeper, it is necessary to create separate systemd unit files for both Kafka and Zookeeper.
* Create a systemd Unit File for Zookeeper
  ```
  sudo nano /etc/systemd/system/zookeeper.service
  ```
  /home/kafka/kafka/ merupakan path direktori file
  * Add the following content to the file:
    ```
    [Unit]
    Description=Apache Zookeeper server
    Documentation=http://zookeeper.apache.org
    Requires=network.target remote-fs.target
    After=network.target remote-fs.target
    
    [Service]
    Type=simple
    User=kafka
    ExecStart=/home/kafka/kafka/bin/zookeeper-server-start.sh /home/kafka/kafka/config/zookeeper.properties
    ExecStop=/home/kafka/kafka/bin/zookeeper-server-stop.sh
    Restart=on-abnormal
    
    [Install]
    WantedBy=multi-user.target
    ```
  
* Create a systemd Unit File for Kafka
  ```
  sudo nano /etc/systemd/system/kafka.service
  ```
  * Add the following content to the file:
    ```
    [Unit]
    Description=Apache Kafka Server
    Documentation=http://kafka.apache.org/documentation.html
    Requires=zookeeper.service
    After=zookeeper.service
    
    [Service]
    Type=simple
    User=kafka
    ExecStart=/home/kafka/kafka/bin/kafka-server-start.sh /home/kafka/kafka/config/server.properties
    ExecStop=/home/kafka/kafka/bin/kafka-server-stop.sh
    Restart=on-failure
    
    [Install]
    WantedBy=multi-user.target
    ```
    
### 4.Manage kafka Service to start kafka
* Reload systemd
  Reload systemd to pick up the changes made to the unit files
  ```
  sudo systemctl daemon-reload
  ```
* Start zookeeper
  ```
  sudo systemctl start zookeeper
  ```
* Enable zookeeper
  ```
  sudo systemctl enable zookeeper
  ```
* Check status zookeeper
  ```
  sudo systemctl status zookeeper
  ```
  ![ss system status zookeeper](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/3643f67d-7bb9-469e-a312-b5c25ac97f23)
  
* Start Kafka
  ```
  sudo systemctl start kafka
  ```
* Enable Kafka
  ```
  sudo systemctl enable kafka
  ```
* Check status Kafka
  ```
  sudo systemctl status kafka
  ```
  ![ss system status kafka](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/efa46cb6-7f2c-42db-a976-d93e4f7858cb)


### 5.Create Topic, Test Produce Data, Consume Data and Delete Topic using CLI
* Create Topic
   **first-topic** is name of the topic, can be customized
  ```
  bin/kafka-topics.sh --create --topic first-topic --bootstrap-server localhost:9092
  ```
  ![ss create topic 2](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/6c94591c-6429-4aa6-8b29-48e168393136)
  
* Write some events into the topic (Produce Data)
  ```
  bin/kafka-console-producer.sh --topic first-topic --bootstrap-server localhost:9092
  ```
  ![ss write event 2](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/20a6c0af-a689-4da5-ab27-e375b10b2522)
  
* Read the events (Consume Data)
  ```
  bin/kafka-console-consumer.sh --topic first-topic --from-beginning --bootstrap-server localhost:9092
  ```
  ![ss read event 2](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/c5b70514-924f-4043-8861-f8c84be9a8b6)

* Delete Topic
  ```
  bin/kafka-topics.sh --delete --topic first-topic --bootstrap-server localhost:9092
  ```
  
### 6.Zookeeper Quorum and Kafka Cluster id check
* Zookeeper Quorum
  - Zookeeper version: The version of ZooKeeper used
  - Latency min/avg/max: The minimum, average, and maximum latency experienced by the client.
  - Received: Total messages received by ZooKeeper
  - Sent: Total messages sent by ZooKeeper.
  - Connections: The number of currently active connections.
  - Outstanding: The number of messages that ZooKeeper has not responded to.
  - Zxid: Last transaction ID processed by ZooKeeper.
  - Mode: ZooKeeper mode, in this example is standalone.
  - Node count: The number of nodes (znodes) in the ZooKeeper ensemble.
  ```
  telnet localhost 2181
  ```
  ![ss zookeeper quorum 2](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/c1ae95bb-7923-44d2-9266-2fc216bb226a)

* Cluster Id Check use CLI
  - Topic: The name of the Kafka topic is "first-topic".
  - TopicId: The unique identifier (UUID) assigned to the topic. In this case, the TopicId is "4RiFCXatRjqXI5UL00SIpg".
  - PartitionCount: The topic has 1 partition.
  - ReplicationFactor: Each partition of the topic has a replication factor of 1, meaning there is only one copy of each partition.
  - Configs: Additional configurations for the topic. In this case, there are no specific configurations listed.
  - Partition: The details of the partitions within the topic.
    - Partition: The partition number. In this case, there is only one partition, numbered 0.
    - Leader: The ID of the Kafka broker that is currently the leader for this partition. In this case, broker with ID 0 is the leader.
    - Replicas: The list of Kafka brokers that replicate the data for this partition. In this case, there is only one replica, which is broker with ID 0.
    - Isr: The list of in-sync replicas for this partition. In this case, there is only one in-sync replica, which is also broker with ID 0.
  
  ```
  bin/kafka-topics.sh --describe --topic first-topic --bootstrap-server localhost:9092
  ```
  ![ss describe topic or cluster id check](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/c31424e1-4cf1-46e0-9dc4-b4bac55ce409)



