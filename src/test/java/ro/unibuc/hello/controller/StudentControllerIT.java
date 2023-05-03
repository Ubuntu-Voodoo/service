package ro.unibuc.hello.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.StudentRepository;
import ro.unibuc.hello.data.Student;
import ro.unibuc.hello.dto.StudentDTO;
import ro.unibuc.hello.controller.StudentController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IT")
public class StudentControllerIT {
    @Autowired
    StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void createStudent() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "John Doe");
        params.add("email", "johndoe@example.com");
        params.add("age", "25");
        params.add("grades", "9.5");
        params.add("grades", "8.7");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Student> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/student/create",
                request,
                Student.class);

        Student student = response.getBody();

        Assertions.assertNotNull(student);
        Assertions.assertNotNull(student.getId());
        Assertions.assertEquals(student.getName(), "John Doe");
        Assertions.assertEquals(student.getEmail(), "johndoe@example.com");
        Assertions.assertEquals(student.getAge(), 25);
        Assertions.assertEquals(student.getGrades().length, 2);
        Assertions.assertEquals(student.getGrades()[0], 9.5);
        Assertions.assertEquals(student.getGrades()[1], 8.7);
    }
    @Test
    public void EditStudent() {
       Student student = studentController.getStudent("642d31d5a249a13d08983f55");
       Student editedStudent = studentController.editStudent(student.getId(), "New Name", "newemail@gmail.com", 23);
       Assertions.assertEquals(student.getId(), "642d31d5a249a13d08983f55");
       Assertions.assertEquals(student.getName(), "New Name");
       Assertions.assertEquals(student.getEmail(), "newemail@gmail.com");
       Assertions.assertEquals(student.getAge(), 23); 
    }
    
    @Test
    public void testSortByAge() throws Exception {
        Student s1 = new Student("Alina", "alice@yahoo.com", 20, new double[] { 7.5, 8.0 });
        Student s2 = new Student("Bogdan", "bogdan@yahoo.com", 22, new double[] { 9.0, 8.5 });
        Student s3 = new Student("Cristian", "cristian@gmail.com", 18, new double[] { 6.5, 7.0 });
        List<Student> students = Arrays.asList(s1, s2, s3);
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(get("/student/sortByAge"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(students.size())))
            .andExpect(jsonPath("$[0].name", is("Cristian")))
            .andExpect(jsonPath("$[1].name", is("Alina")))
            .andExpect(jsonPath("$[2].name", is("Bogdan")));
    }
    
    @Test
    void getStudent() {
        Student student = studentController.getStudent("642d31d5a249a13d08983f55");
        Assertions.assertEquals(student.getId(), "642d31d5a249a13d08983f55");
        Assertions.assertEquals(student.getName(), "Ion Popescu");
        Assertions.assertEquals(student.getEmail(), "ion.popescu@gmail.come");
        Assertions.assertEquals(student.getAge(), 22);
    }
}
