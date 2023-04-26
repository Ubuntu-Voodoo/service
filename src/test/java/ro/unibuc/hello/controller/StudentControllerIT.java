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

@SpringBootTest
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

    private List<Student> testStudents;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Student student1 = new Student("John", "john@gmail.com", 20, new double[]{8, 9, 7.5});
        Student student2 = new Student("Alice", "alice@gmail.com", 21, new double[]{10, 9.5, 9});
        Student student3 = new Student("Bob", "bob@gmail.com", 22, new double[]{7, 8, 7});

        testStudents = Arrays.asList(student1, student2, student3);

        studentRepository.saveAll(testStudents);
    }

    @Test
    public void testCreateStudent() throws Exception {
        String name = "Mary";
        String email = "mary@gmail.com";
        int age = 19;
        double[] grades = new double[]{7, 8.5, 9};

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/student/create")
                .param("name", name)
                .param("email", email)
                .param("age", String.valueOf(age))
                .param("grades", Arrays.toString(grades))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Student student = studentRepository.findById(content).orElse(null);

        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
        assertEquals(age, student.getAge());
        assertEquals(Arrays.toString(grades), Arrays.toString(student.getGrades()));
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