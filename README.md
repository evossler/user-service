# user-service
Users and Project Memberships Service (A Coding Exercise Solution)

**Required components for running the application in development mode**

Building the project requires Java 9 or higher.  
In addition, the project can also be built into a Docker image and run using Docker.  Doing so requires Docker.

**Install**

Exact installation instructions for Java and Docker depend on your operating system and choice of package managers.  If you are using homebrew on OSX:

`brew cask install java`

`brew cask install docker`

Otherwise, instructions for installing a JDK can be found at https://openjdk.java.net/install/.  
And instructions for installing Docker can be found at https://docs.docker.com/v17.09/engine/installation/


**Start the internal application in development mode**

Clone the git repo:

```
$ git clone https://github.com/evossler/user-service.git
$ cd user-service
```
Run the tests and assemble the JAR:

```
$ ./gradlew test assemble
```

Run the service:

```
$ java -jar build/libs/user-service-1.0.0.jar
```

To see the output of the endpoint, visit http://localhost:8080/users in a web browser, or send an HTTP GET request to that URL using your favorite HTTP tool, such as Postman, SoapUI, or curl:

```
curl http://localhost:8080/users
```

**Optionally, you can build and Run the service as a Docker image (Requires Docker)**
```
$ ./gradlew assemble
$ docker build -t user-service .
$ docker run -p 8080:8080  user-service
```
If you run the docker container as shown here, the instructions for seeing the output of the endpoint are the same as given above.  If you would like, however, you could run the service on a different port, by changing the -p argument accordingly.

**Informational**
```
The problem description does not define requirements for the proper handling of some potential edge cases, so I have done 
what seems reasonable, and left relevant comments in the code.

If the same user (same "id" value) appears in both the /registeredusers and /unregisteredusers data from the external API we 
are taking our input from, only the registered one will appear in our output.  This case does not occur because the external 
service is a mock returning static data, but in the real world this condition could arise due to the underlying data changing 
as the two sets are being retrieved.

If a project membership relationship returned by the /projectmemberships API references a userId that does not correspond to a 
user seen in either /registeredusers data or the /unregisteredusers data, that project membership will not be reflected in our 
output.

Having no information about the expected volume of requests to this endpoint, nor any means of being notified when the data 
from the backing API changes, and having no business rules given about how fresh our results must be in the event of such 
changes, I have opted not to implement any cacheing.  Each request to the /users endpoint will result in data being fetched 
from the three backing API endpoints to compose the response.  Given that our backing API is a static mock, I suppose the data 
will never change, but I'm trying to imagine the real-world implications here.
