# Basic Kafka Learning Documentation
This documentation contains steps to learn the basics of Apache Kafka using the CentOS-based Linux operating system

## Deploy kafka service using Systemd
### 1. Install & Configuration
#### Install java/jdk
  Apache Kafka requires Java to be installed. You can install OpenJDK using the following command
  ```
  sudo yum install java-1.8.0-openjdk
  ```
#### Add User
* Create a Kafka User
  Create a user for running Kafka
  ```
  sudo useradd kafka -m
  ```
* create password for user that we create
  ```
  sudo passwd kafka
  ```
* login to user that we create
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
* Extract Apache Kafka tar
  ```
  tar -xzf kafka_2.12-3.7.0.tgz
  ```
* Move Kafka to folder /opt/kafka
  ```
  sudo mv kafka_2.13-2.8.0 /opt/kafka
  ```
* Set Permissions
  Set appropriate permissions to Kafka directories
  ```
  sudo chown -R kafka:kafka /opt/kafka
  ```

### 2.Create init Service Kafka using Systemd
Using systemd to manage Apache Kafka on CentOS Linux makes it easier to handle the Kafka service, including starting, stopping, and automatically restarting it if it fails.
To run Apache Kafka using systemd along with Zookeeper, it is necessary to create separate systemd unit files for both Kafka and Zookeeper.
* Create a systemd Unit File for Zookeeper
  ```
  sudo nano /etc/systemd/system/zookeeper.service
  ```
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
    ExecStart=/opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties
    ExecStop=/opt/kafka/bin/zookeeper-server-stop.sh
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
    ExecStart=/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties
    ExecStop=/opt/kafka/bin/kafka-server-stop.sh
    Restart=on-failure
    
    [Install]
    WantedBy=multi-user.target
    ```
### 3.Manage kafka Service to start kafka
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


### 4.Create Topic, Test Produce Data, Consume Data and Delete Topic using CLI
* Create Topic
  ```
  bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
  ```
  ![ss create topic](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/c18ebc62-e420-48b8-a89c-5ea212ca291b)

* Write some events into the topic (Produce Data)
  ```
  bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
  ```
  ![ss write event](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/67d76862-393b-4930-80d8-9ba4d4a8b4dd)

* Read the events (Consume Data)
  ```
  bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
  ```
  ![ss read event](https://github.com/mulia-dea/Learning-Kafka/assets/67699035/eae50e57-46f4-4917-a82f-45c9876192d8)

* Delete Topic
  ```
  ```
### Zookeeper Quorum and Kafka Cluster id check

