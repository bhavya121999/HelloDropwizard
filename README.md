# ServerCode
## Prerequisities

1. JDK : 11 or later
2. Maven: 3.6.0
3. Dropwizard: 1.3
4. Elasticsearch: 7.3
5. Docker : 19.03.5



## Running the Application

1. Clone the project.

2. Create a run/debug configuration.

* Open the Run/Debug Configuration dialog.
* In the Run/Debug Configuration dialog, click (+) on the toolbar or press ```Alt+Insert```.
* From the list select the desired configuration type i.e. Application.
* For a new run/debug configuration:
     - Specify its name in the Name field. 
     - In the Configuration tab, specify the class that contains the main() method.
     - Set Program Arguments as ```server config.yml```.
     - Set up your Working Directory.
     - Apply the changes and close the dialog.
3. Start the elasticsearch server. If elasticsearch is setup properly in your system then when you access it through your browser using 
```localhost:9200``` the default page of elasticsearch will open up.

4. Run the application.

## API Documentation
To test the API , use any rest client like postman etc.

### Create a new index<br />

`curl -XPOST 'http://{{hostname}}:9090/users' -H 'Content-Type: application/json' -d '`

```
        {
         "settings" : {
           "number_of_shards" : 1
                 },
        "mappings" : {
            "properties" : {
               "field1" : { "type" : "text" }
                    }
            }
        }
	```
```  
 
  After hitting this API new index **users** must be created.      
  The settings and mappings definition can be taken directly from the project through ```users.mapping``` which is present 
  under the ```resources``` package. 


### Index Document <br />



`curl -XPOST 'http://{{hostname}}:9090/users/{id}' -H 'Content-Type: application/json' -d '`

```
            {
                "user_id": "gupta121",
                "firstName": "Bhavya",
                "lastName": "Gupta",
                "status": "true"
            }
```

   After hitting this API user document will be created.

### Get document by id<br />

`curl -XGET 'http://{{hostname}}:9200/users/_doc/{id}' -H 'Content-Type: application/json' -d '`


   After hitting this api the document corresponding to the id specified in the URL will be obtained.

### Search Request<br />


`curl -XGET 'http://{{hostname}}:9200/users/_search' -H 'Content-Type: application/json' -d '`


 ``` {
    "query": {
      "match" : {
        "status" : "true"
                }
               }
             }
	     
	     ```

```
   After hitting this API the document that matches the search parameter will be seen as a response.
   
   
   ## Dockerize Dropwizard Application
   
   1. Add the following **maven shade plugin** configuration.
   
   ```
  <!-- Maven Shade Plugin -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>2.3</version>
				<executions>
					<!-- Run shade goal on package phase -->
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<artifactSet>
								<excludes>
									<exclude>org.apache.logging.log4j:log4j-api</exclude>
									<exclude>org.apache.logging.log4j:log4j-core</exclude>
								</excludes>
							</artifactSet>
							<transformers>
								<transformer implementation=
													 "org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
							</transformers>

						</configuration>

					</execution>
				</executions>
			</plugin>

```
2. After executing ```mvn package``` you will have the following directory structure.

![z1](https://user-images.githubusercontent.com/46423346/74933477-06e07180-540a-11ea-8aa3-b90b50a9a418.png)

3. Run your application using ```java -jar target/galaxy-1.0-SNAPSHOT.jar server <your-config>.yml```

4. To dockerize your application, create a file named as ```Dockerfile``` and put it inside root of your project.
5. Add the following code in the Dockerfile

```
FROM openjdk:12-alpine
COPY target/galaxy-*.jar /galaxy.jar/
CMD ["java" , "-jar" , "/galaxy.jar"]
```
6. Now, go to command terminal to build the image of the Dockerfile

Run the following command:

```sudo docker build -t marco/irock:1.0-SNAPSHOT .```


***NOTE:  Image name is ```irock``` and you can even mention its version lets say ```1.0-SNAPSHOT```***

![z2](https://user-images.githubusercontent.com/46423346/74934418-2f696b00-540c-11ea-8c94-fd10fe4aa4de.png)

7. After executing the above command, your image is built.

8. Now go back to your IDE and stop your application, because now you want to run it on the docker container.

9. Go back to terminal, and run the following command

```sudo docker run -d -p 9098:9098 marco/irock:1.0-SNAPSHOT```

You will see that, you will get a docker container id.

![2a](https://user-images.githubusercontent.com/46423346/74934685-c6362780-540c-11ea-96f4-7571fb921be5.png)
   

10. You can stop the docker container by running:

```docker container stop <id>```


