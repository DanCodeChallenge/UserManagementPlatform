package com.hsbc.challenge.integration;

import com.hsbc.challenge.Application;
import com.hsbc.challenge.employee.EmployeeRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration testing for employee related API endpoints
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseEmployeeIntegrationTest {

    @LocalServerPort
    protected int port;
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    protected HttpHeaders headers = new HttpHeaders();

    @Autowired
    protected EmployeeRepository employeeRepo;

    protected String createURLForEndpoint(String endpointUri) {
        return "http://localhost:" + port + endpointUri;
    }
}
