package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GradeCalculatorTest {

    private final GradeCalculator calculator = new GradeCalculator();

    @Test
    @DisplayName("95 оноо A дүн байна")
    void ninetyFiveIsA() {
        // Arrange
        double score = 95.0;

        // Act
        String result = calculator.letterGrade(score);

        // Assert
        assertEquals("A", result);
    }

    @Test
    @DisplayName("85 оноо B дүн байна")
    void eightyFiveIsB() {
        // Arrange
        double score = 85.0;

        // Act
        String result = calculator.letterGrade(score);

        // Assert
        assertEquals("B", result);
    }

    @Test
    @DisplayName("75 оноо C дүн байна")
    void seventyFiveIsC() {
        // Arrange
        double score = 75.0;

        // Act
        String result = calculator.letterGrade(score);

        // Assert
        assertEquals("C", result);
    }

    @Test
    @DisplayName("65 оноо D дүн байна")
    void sixtyFiveIsD() {
        // Arrange
        double score = 65.0;

        // Act
        String result = calculator.letterGrade(score);

        // Assert
        assertEquals("D", result);
    }

    @Test
    @DisplayName("30 оноо F дүн байна")
    void thirtyIsF() {
        // Arrange
        double score = 30.0;

        // Act
        String result = calculator.letterGrade(score);

        // Assert
        assertEquals("F", result);
    }

  @Test
  @DisplayName("90 оноо яг A дүн байх ёстой")
  void ninetyIsExactlyA() {
    // Arrange
    double score = 90.0;

    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals("A", result);
  }

  @Test
  @DisplayName("89.99 оноо B дүн байх ёстой")
  void eightyNinePointNineNineIsB() {
    // Arrange
    double score = 89.99;

    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals("B", result);
  }

  @Test
  @DisplayName("60 оноо яг D дүн байх ёстой")
  void sixtyIsExactlyD() {
    // Arrange
    double score = 60.0;

    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals("D", result);
  }

  @Test
  @DisplayName("59.99 оноо F дүн байх ёстой")
  void fiftyNinePointNineNineIsF() {
    // Arrange
    double score = 59.99;

    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals("F", result);
  }

  @Test
  @DisplayName("0 оноо F дүн байх ёстой")
  void zeroIsF() {
    // Arrange
    double score = 0.0;

    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals("F", result);
  }

  @Test
  @DisplayName("100 оноо A дүн байх ёстой")
  void hundredIsA() {
    // Arrange
    double score = 100.0;

    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals("A", result);
  }

  @Test
  @DisplayName("-1 оноо өгвөл IllegalArgumentException шиднэ")
  void negativeScoreThrowsException() {
    // Arrange
    double score = -1.0;

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.letterGrade(score)
    );
  }

  @Test
  @DisplayName("101 оноо өгвөл IllegalArgumentException шиднэ")
  void scoreAboveHundredThrowsException() {
    // Arrange
    double score = 101.0;

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.letterGrade(score)
    );
  }

  @Test
  @DisplayName("Бүх дээд оноог өгвөл нийт 100 гарна")
  void totalScoreIsHundred() {
    // Arrange
    double att = 10;
    double lab = 40;
    double quiz1 = 10;
    double quiz2 = 10;
    double exam = 30;

    // Act
    double result = calculator.totalScore(att, lab, quiz1, quiz2, exam);

    // Assert
    assertEquals(100.0, result);
  }

  @Test
  @DisplayName("Сөрөг ирцийн оноо өгвөл exception шиднэ")
  void negativeAttendanceThrowsException() {
    // Arrange
    double att = -5;

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.totalScore(att, 40, 10, 10, 30)
    );
  }

  @Test
  @DisplayName("Лабораторийн 40-өөс их оноо өгвөл exception шиднэ")
  void labAboveMaximumThrowsException() {
    // Arrange
    double lab = 41;

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.totalScore(10, lab, 10, 10, 30)
    );
  }

  @ParameterizedTest
  @DisplayName("Онооны хязгааруудаар үсгэн дүн зөв тооцогдоно")
  @CsvSource({
      "95, A",
      "90, A",
      "89.99, B",
      "80, B",
      "70, C",
      "60, D",
      "59.99, F",
      "0, F"
  })
  void letterGradeBoundaries(double score, String expected) {
    // Act
    String result = calculator.letterGrade(score);

    // Assert
    assertEquals(expected, result);
  }

  @ParameterizedTest
  @DisplayName("Нийт оноог зөв тооцно")
  @CsvSource({
      "10, 40, 10, 10, 30, 100",
      "10, 30, 10, 10, 30, 90",
      "8, 35, 9, 8, 25, 85",
      "5, 20, 8, 7, 25, 65"
  })
  void totalScoreCalculations(
      double att,
      double lab,
      double quiz1,
      double quiz2,
      double exam,
      double expected) {

    // Act
    double result =
        calculator.totalScore(att, lab, quiz1, quiz2, exam);

    // Assert
    assertEquals(expected, result);
  }
}