# ServerCode
## Prerequisities

1. JDK : 11 or later
2. Maven: 3.6.0
3. Dropwizard: 1.3
4. Elasticsearch: 7.3



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

#### Create a new index<br />
For eg. users <br />
This can be done as:<br />
URL :-```http://{{hostname}}:9090/users```<br />
header :- content-type :- application/json<br />
method :- post<br />
body :- {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "settings" : {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;     "number_of_shards" : 1<br />
   &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;    },<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "mappings" : {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        "properties" : {<br />
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           "field1" : { "type" : "text" }<br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;        }<br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;     }<br />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   }<br />  
  
  After hitting this API new index **users** must be created.      
  The settings and mappings definition can be taken directly from the project through ```users.mapping``` which is present 
  under the ```resources``` package. 

#### Index document<br /> 
URL :- ```http://{{hostname}}:9090/users/{id}```<br />
header :- content-type :- application/json<br />
method :- post<br />
body :- {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "user_id": "gupta121",<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  "firstName": "Bhavya",<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  "lastName": "Gupta",<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  "status": "true"<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    }<br />

   After hitting this API user document will be created.

#### Get document by id<br />
URL :-```http://{{hostname}}:9200/users/_doc/{id}```<br />
header :- content-type :- application/json<br />
method :- get<br />
   After hitting this api the document corresponding to the id specified in the URL will be obtained.

#### Search Request<br />
URL :-```http://{{hostname}}:9200/users/_search```<br />
header :- content-type :- application/json<br />
method :- get<br />
body :-{<br />
&nbsp;&nbsp;&nbsp;    "query": {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        "match" : {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           "status" : "true"<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        }<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   }<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br />

   After hitting this API the document that matches the search parameter will be seen as a response.

