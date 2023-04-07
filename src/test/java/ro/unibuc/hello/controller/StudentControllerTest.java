import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import ro.unibuc.hello.data.StudentRepository;
import ro.unibuc.hello.data.Student;
import ro.unibuc.hello.dto.StudentDTO;
import ro.unibuc.hello.controller.StudentController;



@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;


    @Test
    public void testCreateStudent() {
        // Mock the student object that will be returned by the repository save method
        Student mockStudent = new Student("Ion Popescu", "ion.popescu@gmail.com", 22, new double[]{9.0, 8.0});
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(mockStudent);

        // Call the controller method with mock request parameters
        Student createdStudent = studentController.createStudent("Ion Popescu", "ion.popescu@gmail.com", 22, new double[]{9.0, 8.0});

        // Verify that the repository save method was called with the correct student object
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(argumentCaptor.capture());
        Student capturedStudent = argumentCaptor.getValue();

        Assert.assertEquals("Ion Popescu", capturedStudent.getName());
        Assert.assertEquals("ion.popescu@gmail.com", capturedStudent.getEmail());
        Assert.assertEquals(22, capturedStudent.getAge());
        Assert.assertArrayEquals(new double[]{9.0, 8.0}, capturedStudent.getGrades(), 0.001);

        // Verify that the created student object matches the mock student object returned by the repository
        Assert.assertEquals(mockStudent.getId(), createdStudent.getId());
        Assert.assertEquals(mockStudent.getName(), createdStudent.getName());
        Assert.assertEquals(mockStudent.getEmail(), createdStudent.getEmail());
        Assert.assertEquals(mockStudent.getAge(), createdStudent.getAge());
        Assert.assertArrayEquals(mockStudent.getGrades(), createdStudent.getGrades(), 0.001);
        Assert.assertEquals(mockStudent.getAverageGrade(), createdStudent.getAverageGrade(), 0.001);
    }

    @Test
    public void testGetStudent() {
        Student mockStudent = new Student("Ion Popescu", "ion.popescu@gmail.com", 22, new double[]{9.0, 8.0});

        String studentId = "642d31d5a249a13d08983f55";
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));

        Student retrievedStudent = studentController.getStudent(studentId);

        Assert.assertEquals(mockStudent.getId(), retrievedStudent.getId());
        Assert.assertEquals(mockStudent.getName(), retrievedStudent.getName());
        Assert.assertEquals(mockStudent.getEmail(), retrievedStudent.getEmail());
        Assert.assertEquals(mockStudent.getAge(), retrievedStudent.getAge());

    }
    
    @Test
    public void testEditStudent() {
        Student mockStudent = new Student("Ion Popescu", "ion.popescu@gmail.com", 22, new double[]{9.0, 8.0});

        String studentId = "642d31d5a249a13d08983f55";
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));

        Student editedStudent = studentController.editStudent(studentId, "New Name", "newemail@gmail.com", 23);

        Assert.assertEquals(mockStudent.getId(), editedStudent.getId());
        Assert.assertEquals("New Name", editedStudent.getName());
        Assert.assertEquals("newemail@gmail.com", editedStudent.getEmail());
        Assert.assertEquals(23, editedStudent.getAge());
        Assert.assertArrayEquals(mockStudent.getGrades(), editedStudent.getGrades(), 0.001);

        Mockito.verify(studentRepository).findById(studentId);
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
    }
    @Test
    public void testDeleteStudent() {
        Student mockStudent = new Student("Ion Popescu", "ion.popescu@gmail.com", 22, new double[]{9.0, 8.0});

        String studentId = "642d31d5a249a13d08983f55";
        studentController.deleteStudent(studentId);
        Mockito.verify(studentRepository).deleteById(studentId);
    }
}
