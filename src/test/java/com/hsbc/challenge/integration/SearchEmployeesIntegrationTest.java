package com.hsbc.challenge.integration;

import com.hsbc.challenge.employee.Employee;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchEmployeesIntegrationTest extends BaseEmployeeIntegrationTest {

    @Test
    public void shouldGetEmployeeByIdWhenEmployeeExists() throws JSONException {
        Employee employee = new Employee("A", "B", 1, BigInteger.valueOf(235600));

        employeeRepo.save(employee);

        ResponseEntity<String> response = sendToSearchByIdEndpoint(employee.getId());

        String expected = String.format("{id:%s, name:%s, surname:%s, grade:%s, salary:%s}",
                employee.getId(), employee.getName(), employee.getSurname(), employee.getGrade(), employee.getSalary());
        JSONAssert.assertEquals(expected, response.getBody(), true);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldNotGetEmployeeWhenEmployeeIdNotInDatabase() {
        ResponseEntity<String> response = sendToSearchByIdEndpoint(ThreadLocalRandom.current().nextLong(10000L, Long.MAX_VALUE));

        assertFalse(response.getBody().contains("name"));
        assertFalse(response.getBody().contains("surname"));
        assertFalse(response.getBody().contains("grade"));
        assertFalse(response.getBody().contains("salary"));
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetEmployeesByName() throws JSONException {
        String name = "MatchOnName";
        Employee employeeToGet1 = new Employee(name, "OK", 1, BigInteger.valueOf(125000));
        Employee employeeToGet2 = new Employee(name, "A", 3, BigInteger.valueOf(32500));
        Employee employeeToGet3 = new Employee(name, "B", 1, BigInteger.valueOf(235000));
        Employee employeeToNotGet = new Employee("WrongName", "OK", 1, BigInteger.valueOf(125000));

        List<Employee> employeesToGet = Arrays.asList(employeeToGet1, employeeToGet2, employeeToGet3);
        employeesToGet.forEach(employeeRepo::save);
        employeeRepo.save(employeeToNotGet);

        Employee employee = new Employee(name, null, null, null);
        ResponseEntity<String> response = sendToSearchByAttributesEndpoint(employee);

        assertJSONArrayContainsEmployees(response.getBody(), employeesToGet);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetEmployeesBySalary() throws JSONException {
        BigInteger salary = BigInteger.valueOf(3265);
        Employee employeeToGet1 = new Employee("Match", "Salary", 4, salary);
        Employee employeeToGet2 = new Employee("Me", "Too", 5, salary);
        Employee employeeToNotGet = new Employee("Wrong", "Salary", 10, BigInteger.valueOf(7720));

        List<Employee> employeesToGet = Arrays.asList(employeeToGet1, employeeToGet2);
        employeesToGet.forEach(employeeRepo::save);
        employeeRepo.save(employeeToNotGet);

        Employee employee = new Employee(null, null, null, salary);
        ResponseEntity<String> response = sendToSearchByAttributesEndpoint(employee);

        assertJSONArrayContainsEmployees(response.getBody(), employeesToGet);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetEmployeesWhenSearchingWithTwoProperties() throws JSONException {
        String name = "SomeNameForNameAndGradeMatch";
        int grade = 3;
        Employee employeeToGet1 = new Employee(name, "OK", grade, BigInteger.valueOf(225000));
        Employee employeeToGet2 = new Employee(name, "OK", grade, BigInteger.valueOf(125000));
        Employee employeeToNotGet = new Employee(name, "OK", 1, BigInteger.valueOf(125000));

        List<Employee> employeesToGet = Arrays.asList(employeeToGet1, employeeToGet2);
        employeesToGet.forEach(employeeRepo::save);
        employeeRepo.save(employeeToNotGet);

        Employee employee = new Employee(name, null, grade, null);
        ResponseEntity<String> response = sendToSearchByAttributesEndpoint(employee);

        assertJSONArrayContainsEmployees(response.getBody(), employeesToGet);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetEmployeesWhenSearchingWithThreeProperties() throws JSONException {
        String name = "SomeNameForThreePropertyMatch";
        String surname = "TesterMan";
        int grade = 6;
        Employee employeeToGet1 = new Employee(name, surname, grade, BigInteger.valueOf(1000));
        Employee employeeToGet2 = new Employee(name, surname, grade, BigInteger.valueOf(2000));
        Employee employeeToGet3 = new Employee(name, surname, grade, BigInteger.valueOf(3000));
        Employee employeeToGet4 = new Employee(name, surname, grade, BigInteger.valueOf(1000));
        Employee employeeToNotGet1 = new Employee(name, surname, 5, BigInteger.valueOf(1000));
        Employee employeeToNotGet2 = new Employee(name, "wrongSurname", grade, BigInteger.valueOf(1000));

        List<Employee> employeesToGet = Arrays.asList(employeeToGet1, employeeToGet2, employeeToGet3, employeeToGet4);
        employeesToGet.forEach(employeeRepo::save);
        employeeRepo.save(employeeToNotGet1);
        employeeRepo.save(employeeToNotGet2);

        Employee employee = new Employee(name, surname, grade, null);
        ResponseEntity<String> response = sendToSearchByAttributesEndpoint(employee);

        assertJSONArrayContainsEmployees(response.getBody(), employeesToGet);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetEmployeesWhenSearchingWithAllProperties() throws JSONException {
        String name = "FourPropertyMatch";
        String surname = "TesterMan";
        int grade = 8;
        BigInteger salary = BigInteger.valueOf(3500);
        Employee employeeToGet1 = new Employee(name, surname, grade, salary);
        Employee employeeToGet2 = new Employee(name, surname, grade, salary);
        Employee employeeToGet3 = new Employee(name, surname, grade, salary);
        Employee employeeToGet4 = new Employee(name, surname, grade, salary);
        Employee employeeToNotGet1 = new Employee(name, surname, 0, salary);
        Employee employeeToNotGet2 = new Employee(name, "wrongSurname", grade, salary);
        Employee employeeToNotGet3 = new Employee("wrongName", surname, grade, salary);
        Employee employeeToNotGet4 = new Employee(name, surname, grade, BigInteger.valueOf(1235));

        List<Employee> employeesToGet = Arrays.asList(employeeToGet1, employeeToGet2, employeeToGet3, employeeToGet4);
        employeesToGet.forEach(employeeRepo::save);
        employeeRepo.save(employeeToNotGet1);
        employeeRepo.save(employeeToNotGet2);
        employeeRepo.save(employeeToNotGet3);
        employeeRepo.save(employeeToNotGet4);

        Employee employee = new Employee(name, surname, grade, salary);
        ResponseEntity<String> response = sendToSearchByAttributesEndpoint(employee);

        assertJSONArrayContainsEmployees(response.getBody(), employeesToGet);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetPageOfEmployeesWhenSearchingWithNoProperties() throws JSONException {
        Employee employeeToGet1 = new Employee("A", "AA", 0, BigInteger.valueOf(1000));
        Employee employeeToGet2 = new Employee("B", "BB", 1, BigInteger.valueOf(2000));
        Employee employeeToGet3 = new Employee("C", "CC", 2, BigInteger.valueOf(3000));
        Employee employeeToGet4 = new Employee("D", "DD", 3, BigInteger.valueOf(4000));

        List<Employee> employeesToGet = Arrays.asList(employeeToGet1, employeeToGet2, employeeToGet3, employeeToGet4);
        employeesToGet.forEach(employeeRepo::save);

        Employee employee = new Employee(null, null, null, null);
        ResponseEntity<String> response = sendToSearchByAttributesEndpoint(employee);

        //TODO: this test needs it own database instance if we want to check the JSON content, because other tests send messages to the database get returned by this search
        assertTrue(new JSONObject(response.getBody()).getJSONArray("content").length() >= employeesToGet.size());
        assertEquals(200, response.getStatusCodeValue());
    }

    private ResponseEntity<String> sendToSearchByIdEndpoint(Long id) {
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                createURLForEndpoint("//employee/" + id),
                HttpMethod.GET, entity, String.class);
    }

    private ResponseEntity<String> sendToSearchByAttributesEndpoint(Employee employee) {
        String endPointSuffix = "";
        endPointSuffix += addAttributeToEndpoint(employee.getName(), "name");
        endPointSuffix += addAttributeToEndpoint(employee.getSurname(), "surname");
        endPointSuffix += addAttributeToEndpoint(employee.getGrade(), "grade");
        endPointSuffix += addAttributeToEndpoint(employee.getSalary(), "salary");
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                createURLForEndpoint("//employee?" + endPointSuffix),
                HttpMethod.GET, entity, String.class);
    }

    private String addAttributeToEndpoint(Object attribute, String attributeName) {
        return attribute != null ? String.format("%s=%s&",attributeName, attribute) : "";
    }

    private void assertJSONArrayContainsEmployees(String json, List<Employee> expectedEmployees) throws JSONException {
        String expected = "[";
        for (Employee emp : expectedEmployees) {
            expected += String.format("{name:%s, surname:%s, grade:%s, salary:%s},",
                    emp.getName(), emp.getSurname(), emp.getGrade(), emp.getSalary());
        }
        expected = expected.substring(0, expected.length()-1);
        expected += "]";
        JSONAssert.assertEquals(expected, new JSONObject(json).getJSONArray("content"), false);
    }
}
