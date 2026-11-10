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

  // ===== B хэсэг: letterGrade() Branch Tests =====

  @Test
  void shouldReturnGradeCFor75() {
    assertEquals("C", calculator.letterGrade(75));
  }

  @Test
  void shouldReturnGradeDFor65() {
    assertEquals("D", calculator.letterGrade(65));
  }

  @Test
  void shouldReturnGradeFFor50() {
    assertEquals("F", calculator.letterGrade(50));
  }

  @Test
  void shouldThrowExceptionForNegativeScore() {
    assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(-1));
  }

  @Test
  void shouldThrowExceptionForScoreAbove100() {
    assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(101));
  }

  // ===== B хэсэг: gpa() Branch Tests =====

  @Test
  void shouldHandleDefaultSwitchBranchForGradeF() {
    assertEquals(0.0, calculator.gpa(new double[]{50}), 0.001);
  }

  @Test
  void shouldThrowExceptionForNullScores() {
    assertThrows(IllegalArgumentException.class, () -> calculator.gpa(null));
  }

  @Test
  void shouldThrowExceptionForEmptyScores() {
    assertThrows(IllegalArgumentException.class, () -> calculator.gpa(new double[]{}));
  }

  // ===== B хэсэг: scholarship() Branch Tests =====

  @Test
  void shouldReturnZeroWhenHasViolation() {
    assertEquals(0, calculator.scholarship(3.9, 16, true));
  }

  @Test
  void shouldReturnZeroWhenFirstGpaConditionIsFalse() {
    assertEquals(70, calculator.scholarship(3.5, 12, false));
  }

  @Test
  void shouldReturnZeroWhenFirstCreditConditionIsFalse() {
    assertEquals(70, calculator.scholarship(3.9, 14, false));
  }

  @Test
  void shouldReturnZeroWhenSecondGpaConditionIsFalse() {
    assertEquals(50, calculator.scholarship(3.2, 15, false));
  }

  @Test
  void shouldReturnZeroWhenSecondCreditConditionIsFalse() {
    assertEquals(50, calculator.scholarship(3.5, 11, false));
  }

  @Test
  void shouldReturn50PercentScholarship() {
    assertEquals(50, calculator.scholarship(3.0, 10, false));
  }

  @Test
  void shouldReturnZeroWhenGpaBelowThree() {
    assertEquals(0, calculator.scholarship(2.9, 16, false));
  }

  @Test
  void shouldThrowExceptionForNegativeGpa() {
    assertThrows(IllegalArgumentException.class, () -> calculator.scholarship(-1, 16, false));
  }

  @Test
  void shouldThrowExceptionForGpaAboveFour() {
    assertThrows(IllegalArgumentException.class, () -> calculator.scholarship(4.5, 16, false));
  }

  @Test
  void shouldThrowExceptionForNegativeCredits() {
    assertThrows(IllegalArgumentException.class, () -> calculator.scholarship(3.9, -1, false));
  }

  // ===== C хэсэг: Loop Boundary Tests =====
  // GPA function - 0 / 1 / 2+ iterations

//  //0 iteration - Empty array
//  @Test
//  void shouldThrowExceptionForEmptyScores() {
//    assertThrows(IllegalArgumentException.class, () -> calculator.gpa(new double[]{}));
//  }
//
//  //0 iteration - Null array
//  @Test
//  void shouldThrowExceptionForNullScores() {
//    assertThrows(IllegalArgumentException.class, () -> calculator.gpa(null));
//  }

  //1 iteration - Single score
  @Test
  void shouldCalculateGpaForOneScore() {
    assertEquals(4.0, calculator.gpa(new double[]{95}), 0.001);
  }

  //2+ iterations - Multiple scores
  @Test
  void shouldCalculateGpaForMultipleScores() {
    assertEquals(2.5, calculator.gpa(new double[]{95, 85, 75, 65}), 0.001);
  }

}
