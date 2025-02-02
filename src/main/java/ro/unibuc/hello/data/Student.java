package ro.unibuc.hello.data;
import java.util.Arrays;
import org.springframework.data.annotation.Id;

public class Student {
    @Id
    private String id;

    private String name;
    private String email;
    private int age;
    private double[] grades;
    private double averageGrade;

    public Student() {}

    public Student(String name, String email, int age, double[] grades) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.grades = grades;

        if (grades.length > 0) {
            double sum = 0;
            for (double grade : grades) {
                sum += grade;
            }
            averageGrade = sum / grades.length;
        } else {
            averageGrade = 0;
        }
    }

    public double[] getGrades(){
        return grades;
    }

    public void setGrades(double[] grades){
        this.grades = grades;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
       return age;
    }

}