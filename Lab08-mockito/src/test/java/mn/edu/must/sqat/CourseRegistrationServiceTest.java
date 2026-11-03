package mn.edu.must.sqat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseRegistrationServiceTest {

  @Mock
  StudentRepository repository;

  @Mock
  EmailSender emailSender;

  CourseRegistrationService service;

  @BeforeEach
  void setUp() {
    service = new CourseRegistrationService(repository, emailSender);
  }

  // A1: Идэвхтэй оюутан амжилттай бүртгүүлэх
  @Test
  void register_activeStudent_returnsOkAndSavesAndSendsEmail() {
    Student student = new Student(
        "S001",
        "student@example.com",
        true,
        List.of("CS101")
    );

    when(repository.findStudent("S001")).thenReturn(student);
    when(repository.courseExists("CS201")).thenReturn(true);
    when(repository.prerequisites("CS201")).thenReturn(List.of("CS101"));

    String result = service.register("S001", "CS201");

    assertEquals("OK", result);

    // Repository-ийн үйлдлүүдийг шалгах
    verify(repository).findStudent("S001");
    verify(repository).courseExists("CS201");
    verify(repository).prerequisites("CS201");
    verify(repository).saveRegistration("S001", "CS201");

    // Email илгээсэн эсэхийг шалгах
    verify(emailSender).send(
        eq("student@example.com"),
        eq("Бүртгэл баталгаажлаа"),
        contains("CS201")
    );
  }
}