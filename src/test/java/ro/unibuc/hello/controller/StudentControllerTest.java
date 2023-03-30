@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testCreateStudent() {
        // Mock the student object that will be returned by the repository save method
        Student mockStudent = new Student("John Doe", "johndoe@example.com", 25, new double[]{3.5, 4.0});
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(mockStudent);

        // Call the controller method with mock request parameters
        Student createdStudent = studentController.createStudent("John Doe", "johndoe@example.com", 25, new double[]{3.5, 4.0});

        // Verify that the repository save method was called with the correct student object
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(argumentCaptor.capture());
        Student capturedStudent = argumentCaptor.getValue();
        Assert.assertEquals("John Doe", capturedStudent.getName());
        Assert.assertEquals("johndoe@example.com", capturedStudent.getEmail());
        Assert.assertEquals(25, capturedStudent.getAge());
        Assert.assertArrayEquals(new double[]{3.5, 4.0}, capturedStudent.getGrades(), 0.001);

        // Verify that the created student object matches the mock student object returned by the repository
        Assert.assertEquals(mockStudent.getId(), createdStudent.getId());
        Assert.assertEquals(mockStudent.getName(), createdStudent.getName());
        Assert.assertEquals(mockStudent.getEmail(), createdStudent.getEmail());
        Assert.assertEquals(mockStudent.getAge(), createdStudent.getAge());
        Assert.assertArrayEquals(mockStudent.getGrades(), createdStudent.getGrades(), 0.001);
        Assert.assertEquals(mockStudent.getAverageGrade(), createdStudent.getAverageGrade(), 0.001);
    }
}
