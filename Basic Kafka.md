# Basic Kafka Learning Documentation
This documentation contains steps to learn the basics of Apache Kafka using the CentOS-based Linux operating system

## Deploy kafka service using Systemd
### 1.Install & Configuration
* Install java/jdk
  ```
  sudo yum install java-1.8.0-openjdk
  ```
* Check Java version
* Download kafka using wget
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
### 2.Create init Service Kafka using Systemd
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
