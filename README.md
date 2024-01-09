# Rabbit project

This is a RabbitMQ producer-consumer project built with Spring Boot 3 in Java.

## Overview

This project demonstrates the implementation of a RabbitMQ-based messaging system with a consumer and producer.

### Features

- **Messaging System**: Utilizes RabbitMQ as a message broker.
- **Producer**: Sends messages to a specific queue.
- **Consumer**: Listens to the queue and processes received messages.

## Installation

Follow these steps to set up and run the project locally:

### Prerequisites

- Java Development Kit (JDK 21)
- Maven
- Docker
- RabbitMQ (docker image)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Scoobie02/rabbit.git 

2. Navigate to the project directory:
   ```bash
   cd rabbit
   
3. Build the project:
   ```bash
   mvn clean install

## License and Author

- Author: Wojciech Skubała
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```