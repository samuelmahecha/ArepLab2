## Web Framework Development for REST Services and Static File Management

### Overview

This project enhances a basic web server to develop a versatile web framework capable of serving static files and providing REST services. The framework allows developers to define REST endpoints using lambda functions, manage query parameters, and specify the location of static files efficiently. This implementation provides a robust foundation for building dynamic web applications and APIs.

### Key Features

- **GET Static Method for REST Services**: The framework introduces a method that allows developers to define RESTful services in a streamlined manner using lambda functions. This method, get(), enables the mapping of specific URL paths to custom logic that handles incoming requests and generates responses. Developers can easily create routes that respond to HTTP GET requests, facilitating the implementation of various web service functionalities with minimal boilerplate code.
- **Query Value Extraction Mechanism**: The framework includes a robust mechanism for extracting query parameters from HTTP GET requests. This feature allows developers to access and utilize query parameters directly within their route handlers. By parsing the query string from incoming requests, the framework enables the dynamic generation of responses based on the parameters provided, supporting a wide range of interactive and parameterized web applications.
- **Static File Location Specification**: A configuration method, staticfiles(), is provided to specify the directory where static files are stored. This feature simplifies the organization and management of static resources, such as HTML, CSS, JavaScript, and image files. By setting the static file directory, the framework automatically serves files from the specified location, ensuring that static content is easily accessible and properly managed.
- **Static File Handling**: The framework supports comprehensive static file handling, including the serving of various file types such as HTML documents, CSS stylesheets, JavaScript scripts, and image files. It determines the appropriate MIME type for each file based on its extension and responds with the correct content type, ensuring that static resources are delivered accurately to clients.
- **REST Services Handling**: In addition to static file serving, the framework is designed to handle HTTP GET and POST requests for RESTful services. This feature enables the creation of dynamic web applications with backend services that respond to different types of HTTP requests. By providing support for both GET and POST methods, the framework allows developers to build interactive applications that can handle various types of client interactions and data submissions.

### Components

1. **SimpleWebServer**: Core class for initializing and managing the web server. It registers routes and handles incoming requests.
   
2. **ClientHandler**: Processes client connections, manages request parsing, and dispatches to appropriate route handlers.

3. **RouteHandler**: Functional interface used to define actions for specific routes.
   
4. **Request**: Encapsulates HTTP request data, including query parameters.
   
5.  **Response**: Provides methods for constructing and sending HTTP responses.

## Project Setup

This guide outlines the steps to set up and run your project.

**Prerequisites**

Before proceeding, ensure you have the following software installed on your system:

* **Java** (version 1.8.0 or higher): Download and install Java from the official website: https://www.oracle.com/java/technologies/downloads/
    * Verify your installation by running:
        ```bash
        mvn -version
        ```
      You should see output similar to:

        ```
        Apache Maven 3.x.x (unique identifier)
        Maven home: /path/to/maven
        Java version: 1.8.0, vendor: Oracle Corporation
        Java home: /path/to/java/jdk1.8.0.jdk/Contents/Home
        ...
        ```
* **Git:** Install Git by following the instructions on the official Git website: https://git-scm.com/downloads
    * Verify your installation by running:
        ```bash
        git --version
        ```
      This should output your Git version, for example:

        ```
        git version 2.34.1
        ```

**Installing the Project**

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/samuelmahecha/ArepLab2.git
    ```
2. **Navigate to the Project Directory:**
    ```bash
    cd ArepLab2
    ```

**Building the Project**

1. **Compile and Package:**
    * Run the following command to build the project using Maven:
        ```bash
        mvn package
        ```
    * This will compile your code and create a JAR file in the `target` directory. You should see output similar to:

        ```
        [INFO] Building jar:(https://github.com/samuelmahecha/Lab1Arep.git-1.0-SNAPSHOT.jar
        [INFO] BUILD SUCCESS
        ```

**Running the Application**

1. **Execute the JAR:**
    * Run the following command to launch your application:
        ```bash
        mvn exec:java -Dexec.mainClass="org.example.SimpleWebServer"

        ```
    * Alternatively, run the SimpleWebServer class directly from your IDE or execute the compiled JAR file. 
   
2. **Expected Output:**
    * You should see a message similar to:

        ```
        Ready to receive on port 8080...
        ```
    * This indicates that your server is running and listening on port 8080.

**Testing**

1. **Open your web browser:**
    * Navigate to `(http://localhost:8080/App/hello?name=Samuel)` in your browser.
    * If everything is set up correctly, you should see "Hello Samuel".
2. **Test the PI response:**
    * Navigate to `((http://localhost:8080/App/pi))` in your browser.
    * If everything is set up correctly, you should see "3.141592653589793"
3. **Try the static files:**
    * Navigate to some of this links `http://localhost:8080/index.html` 'http://localhost:8080/sunset.jpg' 'http://localhost:8080/example.html' 'http://localhost:8080/example.png' in your browser.
    * If everything is set up correctly, you should see the page you search.

    
## Dependencies
- **Maven**: The project uses Maven to manage dependencies and build the project.
- **JUnit 4**: The project uses JUnit 4 for unit testing.

---------

## Implementation
- The implementation includes:

    * Routing: Define and manage HTTP routes with the get() method.
    * Static File Serving: Serve static files from a specified directory.
    * Query Parameter Handling: Extract and use query parameters in REST service handlers.
      
**Get Any Name**
![image](https://github.com/user-attachments/assets/581f0e37-4995-4a63-97ee-f10a4dbc1a0c)
---------
**Get PI Calculation**
![image](https://github.com/user-attachments/assets/6458deb1-ff32-4e85-a224-ff367d32fa50)
---------
**Example Static File**
![image](https://github.com/user-attachments/assets/c6b874aa-45dd-41e3-932e-3b05a865dfc8)
---------
**Example Static File**
![image](https://github.com/user-attachments/assets/831fec62-f03d-4433-9713-fd2153311d3a)

---------

## Interaction Flow

### Server Initialization
  * The SimpleWebServer class initializes the server and sets up necessary configurations. This includes defining routes using the get() method and specifying the directory for static files with staticfiles(). During initialization, the server prepares to accept incoming connections and handle HTTP requests.

### Client Connection
  * The server listens for incoming client connections on a specified port (e.g., port 8080). When a client connects, a new Socket object is created to manage the communication with that client.

### Client Request Handling
  * The ClientHandler class processes each client request in a separate thread. It reads the incoming HTTP request, parses the request line to determine the HTTP method and requested resource. The request may include query parameters, which are extracted and made available for processing.
  * Static File Requests:
    * If the requested resource matches a static file path, the ClientHandler uses the handleGetRequest() method to locate and serve the file from the directory specified by staticfiles(). The file is read, and the appropriate MIME type is set based on the file extension. The file content is then sent back to the client with an HTTP 200 OK response.
  * REST Service Requests:
    * If the request matches a defined route, the ClientHandler retrieves the corresponding RouteHandler from the route map. The RouteHandler is a lambda function that processes the request and generates a response. The handler is invoked with a Request object containing query parameters and a Response object for constructing the HTTP response. The result from the handler is sent back to the client.

#### Response Generation
  * After processing the request, whether for a static file or a REST service, the server constructs an HTTP response. For static files, this includes setting the content type and content length headers, followed by sending the file data. For REST services, the server sends the response generated by the RouteHandler, including the appropriate status code and content type.

#### Connection Termination
1. After the response is sent, the server closes the connection with the client and waits for new connections. This process continues in a loop, handling multiple client requests concurrently using a thread pool.


## Relationships Between Classes

#### SimpleWebServer
* **Purpose:** Sets up and manages the server.
* **Relationships:**
  * Manages a map of routes (URL paths to RouteHandler functions).
  * Initializes and configures the ServerSocket.
  * Uses ClientHandler to handle individual client connections.

#### ClientHandler
* **Purpose:** Processes client requests and generates responses.
* **Relationships:**
  * Receives a Socket object for communication with the client.
  * Accesses the route map provided by SimpleWebServer.
  * Uses Request to parse query parameters.
  * Constructs and sends responses.

#### RouteHandler
* **Purpose:** Functional interface for handling requests at specific routes.
* **Relationships:**
  * Implemented as lambda functions or method references in SimpleWebServer.
  * Takes Request and Response objects as parameters.

#### Request
* **Purpose:** Encapsulates and provides access to HTTP request data.
* **Relationships:**
  * Contains query string parameters.
  * Provides methods for retrieving query parameter values.

#### Response
* **Purpose:** Provides methods for constructing and sending HTTP responses.
* **Relationships:**
  * Placeholder for future expansion.
----------

## Generating Project Documentation

1. **Generate the Site**
    - Run the following command to generate the site documentation:
      ```sh
      mvn site
      ```

2. **Add Javadoc Plugin for Documentation**
    - Add the Javadoc plugin to the `reporting` section of the `pom.xml`:
      ```xml
      <project>
        ...
        <reporting>
          <plugins>
            <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-javadoc-plugin</artifactId>
              <version>2.10.1</version>
              <configuration>
                ...
              </configuration>
            </plugin>
          </plugins>
        </reporting>
        ...
      </project>
      ```

    - To generate Javadoc as an independent element, add the plugin in the `build` section of the `pom.xml`:
      ```xml
      <project>
        ...
        <build>
          <plugins>
            <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-javadoc-plugin</artifactId>
              <version>2.10.1</version>
              <configuration>
                ...
              </configuration>
            </plugin>
          </plugins>
        </build>
        ...
      </project>
      ```

3. **Generate Javadoc Commands**
    - Use the following commands to generate Javadocs:
      ```sh
      mvn javadoc:javadoc
      mvn javadoc:jar
      mvn javadoc:aggregate
      mvn javadoc:aggregate-jar
      mvn javadoc:test-javadoc
      mvn javadoc:test-jar
      mvn javadoc:test-aggregate
      mvn javadoc:test-aggregate-jar
      ```

---------

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details.

----------
## Author
Jose Samuel Mahecha Alarcon - @samuelmahecha
