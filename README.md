## API documentation
Responses to queries are in JSON format.


### GET /employee/${id}
Searches for an employee using his/her ID.
If the employee can't be found it will return a HTTP 404 Not Found response
#### Endpoint Details
HTTP Method: PUT
Endpoint: /employee/${id}


### GET /employee?name=${name}&surname=${surname}&grade=${grade}&salary=${salary}
Searches for employees with the supplied properties
#### Endpoint Details
HTTP Method: GET
Endpoint Examples:
/employee?name=Dan&surname=Y&grade=5&salary=1000
/employee?name=Dan
/employee?surname=Y


### PUT /employee/${id}
If id exists it will update an existing employee.
If the id doesn't exist it will return a HTTP 404 response
#### Endpoint Details
HTTP Method: PUT
Endpoint: /employee/${id}
Request Body Parameters:
name : String
surname : String
grade : Integer
salary : Integer


### POST /employee
Creates a new employee
If validation on the input fails it will return a HTTP 500 response
#### Endpoint Details
HTTP Method: POST
Endpoint: /employee
Request Body Parameters:
name : String
surname : String
grade : Integer
salary : Integer


### DELETE /employee/${id}
Delete a employee
Does nothing if the employee doesn't exists
#### Endpoint Details
HTTP Method: POST
Endpoint: /employee/${id}


## Tech Stack
Java 8
Spring Boot
Spring Data
Spring MVC
H2 in-memory database


## Challenge Assumptions
Due to the nature of this challenge I had to make some assumptions.
If this was a work task, I would reach out to my colleagues to clarify these assumptions.

### Assumptions:
- ID, name, surname, grade and salary are required fields
- Grade is at most a 32-bit integer, so the primitive int type is sufficient to express all possible grade values
- Salary is currency agnostic
- I won't assume what the maximum salary value can be, so I'll need to use BigInteger to represent this value

P.S. Feel free to reach out to me to discuss any code and design decisions that I have taken in this application