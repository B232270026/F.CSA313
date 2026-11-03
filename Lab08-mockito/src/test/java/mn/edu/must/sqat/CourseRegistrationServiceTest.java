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

  // A2: Байхгүй оюутны бүртгэл
  @Test
  void register_studentNotFound_returnsErrorAndDoesNotSaveOrSendEmail() {

    // Arrange: Оюутан олдохгүй
    when(repository.findStudent("S999"))
        .thenReturn(null);

    // Act: Бүртгүүлэх үйлдэл
    String result = service.register("S999", "CS201");

    // Assert: Зөв алдааны үр дүн буцаасан эсэх
    assertEquals("ERROR_NO_STUDENT", result);

    // Verify: Оюутны мэдээллийг хайсан эсэх
    verify(repository).findStudent("S999");

    // Verify: Оюутан олдоогүй тул дараагийн шалгалтууд хийгдээгүй
    verify(repository, never()).courseExists(anyString());
    verify(repository, never()).prerequisites(anyString());

    // Verify: Бүртгэл хадгалаагүй
    verify(repository, never())
        .saveRegistration(anyString(), anyString());

    // Verify: Имэйл илгээгээгүй
    verify(emailSender, never())
        .send(anyString(), anyString(), anyString());

    // Verify: EmailSender-тэй өөр ямар ч харилцан үйлдэл хийгээгүй
    verifyNoInteractions(emailSender);
  }

  // A3
  @Test
  void register_inactiveStudent_returnsErrorAndDoesNotSave() {

    // Arrange: Идэвхгүй оюутан
    Student student = new Student(
        "S002",
        "inactive@example.com",
        false,
        List.of("CS101")
    );

    when(repository.findStudent("S002"))
        .thenReturn(student);

    // Act: Бүртгүүлэх үйлдэл
    String result = service.register("S002", "CS201");

    // Assert: Идэвхгүй оюутны алдааны код
    assertEquals("ERROR_INACTIVE_STUDENT", result);

    // Verify: Оюутны мэдээллийг хайсан
    verify(repository).findStudent("S002");

    // Verify: Хичээл байгаа эсэхийг шалгаагүй
    verify(repository, never()).courseExists(anyString());

    // Verify: Урьдчилсан нөхцөл шалгаагүй
    verify(repository, never()).prerequisites(anyString());

    // Verify: Бүртгэл хадгалаагүй
    verify(repository, never())
        .saveRegistration(anyString(), anyString());

    // Verify: EmailSender-тэй огт харилцаагүй
    verifyNoInteractions(emailSender);
  }

  // A4
  @Test
  void register_missingPrerequisites_returnsErrorAndDoesNotSave() {

    // Arrange: Идэвхтэй оюутан боловч шаардлагатай хичээл дутуу
    Student student = new Student(
        "S003",
        "student@example.com",
        true,
        List.of("CS101")
    );

    when(repository.findStudent("S003"))
        .thenReturn(student);

    when(repository.courseExists("CS201"))
        .thenReturn(true);

    // CS201 хичээлд CS101 болон CS102 шаардлагатай
    when(repository.prerequisites("CS201"))
        .thenReturn(List.of("CS101", "CS102"));

    // Act: Бүртгүүлэх үйлдэл
    String result = service.register("S003", "CS201");

    // Assert: Урьдчилсан нөхцөл дутуу алдааны код
    assertEquals("ERROR_PREREQUISITES", result);

    // Verify: Оюутныг хайсан
    verify(repository).findStudent("S003");

    // Verify: Хичээл байгаа эсэхийг шалгасан
    verify(repository).courseExists("CS201");

    // Verify: Урьдчилсан нөхцөлийг шалгасан
    verify(repository).prerequisites("CS201");

    // Verify: Бүртгэл хадгалаагүй
    verify(repository, never())
        .saveRegistration(anyString(), anyString());

    // Verify: EmailSender-тэй огт харилцаагүй
    verifyNoInteractions(emailSender);
  }

  // A5
  @Test
  void register_emailSenderThrowsException_registrationStillSucceeds() {

    // Arrange: Идэвхтэй оюутан
    Student student = new Student(
        "S005",
        "student@example.com",
        true,
        List.of("CS101")
    );

    when(repository.findStudent("S005"))
        .thenReturn(student);

    when(repository.courseExists("CS201"))
        .thenReturn(true);

    when(repository.prerequisites("CS201"))
        .thenReturn(List.of("CS101"));

    // EmailSender exception шиднэ
    doThrow(new RuntimeException("Email sending failed"))
        .when(emailSender)
        .send(anyString(), anyString(), anyString());

    // Act: Бүртгүүлэх үйлдэл
    String result = service.register("S005", "CS201");

    // Assert: Email exception гарсан ч бүртгэл амжилттай
    assertEquals("OK", result);

    // Verify: Оюутныг хайсан
    verify(repository).findStudent("S005");

    // Verify: Хичээл байгаа эсэхийг шалгасан
    verify(repository).courseExists("CS201");

    // Verify: Урьдчилсан нөхцөлийг шалгасан
    verify(repository).prerequisites("CS201");

    // Verify: Бүртгэл хадгалсан
    verify(repository).saveRegistration("S005", "CS201");

    // Verify: Имэйл илгээх оролдлого хийсэн
    verify(emailSender).send(
        eq("student@example.com"),
        eq("Бүртгэл баталгаажлаа"),
        contains("CS201")
    );
  }

}