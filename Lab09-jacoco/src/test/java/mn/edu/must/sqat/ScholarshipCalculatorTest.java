package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

  private final ScholarshipCalculator calculator = new ScholarshipCalculator();

  // ===== A хэсэг: Happy Path Tests =====

  @Test
  void shouldReturnGradeAFor95() {
    assertEquals("A", calculator.letterGrade(95));
  }

  @Test
  void shouldReturnGradeBFor85() {
    assertEquals("B", calculator.letterGrade(85));
  }

  @Test
  void shouldCalculateGpaForTwoScores() {
    assertEquals(3.5, calculator.gpa(new double[]{95, 85}), 0.001);
  }

  @Test
  void shouldReturn100PercentScholarship() {
    assertEquals(100, calculator.scholarship(3.9, 16, false));
  }

  @Test
  void shouldReturn70PercentScholarship() {
    assertEquals(70, calculator.scholarship(3.5, 12, false));
  }
}
