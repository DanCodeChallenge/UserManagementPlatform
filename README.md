## How to run
1. Need gradle installed, if gradle is not installed used the gradle wrapper
2. run command `gradle bootRun`<br>
Note: by default it runs on http://localhost:8080/

## API documentation
This API responds in JSON format and accepts Request Bodies in JSON format.<br>
The API is case sensitive.<br>
<br>

### GET /employee/${id}
Searches for an employee using his/her ID.<br>
If the employee can't be found it will return a HTTP 404 Not Found response
##### Endpoint Details
HTTP Method: `GET`<br>
Endpoint: `/employee/${id}`<br>
<br>

### GET /employee?name=${name}&surname=${surname}&grade=${grade}&salary=${salary}&page=${pageNumber}
Searches for employees with the supplied properties.
##### Endpoint Details
HTTP Method: `GET`<br>
Endpoint Examples:<br>
`/employee?name=Dan&surname=Y&grade=5&salary=1000&page=0`<br>
`/employee?name=Dan&page=1`<br>
`/employee?surname=Y`<br>
`/employee?surname=Bale&grade=4`<br>
<br>

### POST /employee
Creates a new employee<br>
If validation on the input fails it will return a HTTP 400 response,
##### Endpoint Details
HTTP Method: `POST`<br>
Endpoint: `/employee`<br>
Request Body JSON Parameters:<br>
`name : String`<br>
`surname : String`<br>
`grade : Integer`<br>
`salary : Integer`<br>
Example Request Body:<br>
```
{
  "name": "Jack",
  "surname": "Bale",
  "grade": 5,
  "salary": 55000
}
```
Note: The request needs to have all four properties, otherwise an HTTP 500 response is returned and the request is not processed.<br>
<br>

### PUT /employee/${id}
If id exists it will update an existing employee.<br>
If the id doesn't exist it will return a HTTP 404 response.
##### Endpoint Details
HTTP Method: `PUT`<br>
Endpoint: `/employee/${id}`<br>
Request Body JSON Parameters:<br>
`name : String`<br>
`surname : String`<br>
`grade : Integer`<br>
`salary : Integer`<br>
Example Request Body:
```
{
  "name": "Daniel",
  "surname": "A",
  "grade": "1",
  "salary": "15000"
}
```
<br>
### DELETE /employee/${id}
Delete an employee.<br>
Returns a HTTP 500 response if the employee you are trying to delete doesn't exists.
##### Endpoint Details
HTTP Method: POST<br>
Endpoint: /employee/${id}<br>
<br>

## Tech Stack
Java 8<br>
Spring Boot<br>
Spring Data<br>
Spring MVC<br>
H2 in-memory database


## Challenge Assumptions
Due to the nature of this challenge I had to make some assumptions.<br>
If this was a work task, I would reach out to my colleagues to clarify these assumptions.

### Assumptions:
- ID, name, surname, grade and salary are required fields
- Grade is at most a 32-bit integer, so the primitive int type is sufficient to express all possible grade values
- Salary is currency agnostic
- I won't assume what the maximum salary value can be, so I'll need to use BigInteger to represent this value

P.S. Feel free to reach out to me to discuss any code and design decisions that I have taken in this application