# Lab 04 - JUnit 5 Unit Testing

---

## 1. Оюутны мэдээлэл

* **Оюутан:** Б.Энхжин
* **Оюутны код:** B232270026
* **Хичээл:** Программ хангамжийн чанарын баталгаа ба туршилт
* **Лаборатори:** Lab 04 - JUnit 5

---

## 2. Лабораторийн зорилго

Энэ лабораторийн ажлын зорилго нь орчин үеийн Java төслийн стандарт болсон JUnit 5 хүрээгээр
нэгжийн (unit) тест бичиж сурахад оршино. Лекц 4-т үзсэн тестийн суурь ойлголтууд — тест тохиолдол,
oracle (хүлээгдэж буй зөв үр дүн), assertion — энд шууд практик хэрэгжилтээ олно. Мөн хязгаарын утгуудыг шалгах, буруу оролтын үед exception үүсэж байгаа эсэхийг шалгах, parameterized test болон mutation testing ашиглан тестийн чанарыг үнэлэх зорилготой.

---

## 3. Ашигласан орчин

### Java хувилбар

Команд:

```bash
java -version
```

Гаралт:

```text
openjdk version "21.0.12.1" 2026-08-18
OpenJDK Runtime Environment (build 21.0.12.1+1-1-deb13u1-Debian)
OpenJDK 64-Bit Server VM (build 21.0.12.1+1-1-deb13u1-Debian, mixed mode, sharing)
```

### Maven хувилбар

Команд:

```bash
mvn -version
```

Гаралт:

```text
Apache Maven 3.9.9
Maven home: /usr/share/maven
Java version: 21.0.12.1, vendor: Debian, runtime: /usr/lib/jvm/java-21-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.12.107+deb13-amd64", arch: "amd64", family: "unix"

```

### Ашигласан технологи

* Java 21
* Maven
* JUnit 5 / JUnit Jupiter 5.10.2
* Maven Surefire Plugin 3.2.5

---

## 4. Хэрэгжүүлсэн ажил

Энэ лабораторийн хүрээнд `GradeCalculator` класс үүсгэж, `letterGrade()` болон `totalScore()` гэсэн хоёр үндсэн методыг хэрэгжүүлсэн.

### `letterGrade()`

`letterGrade()` метод нь онооноос хамааран үсгэн үнэлгээг дараах байдлаар буцаана.

|     Оноо | Үсгэн дүн |
| -------: | :-------: |
|   90–100 |     A     |
| 80–89.99 |     B     |
| 70–79.99 |     C     |
| 60–69.99 |     D     |
|  0–59.99 |     F     |

0-100-ийн хязгаараас гадуур утга өгсөн тохиолдолд `IllegalArgumentException` алдаа шиднэ.

### `totalScore()`

`totalScore()` метод нь дараах оноонуудын нийлбэрээр нийт оноог тооцно.

* Attendance — 10 оноо
* Lab — 40 оноо
* Quiz 1 — 10 оноо
* Quiz 2 — 10 оноо
* Exam — 30 оноо

Нийт хамгийн их оноо нь 100 байна. Аль нэг оноо сөрөг эсвэл тухайн үзүүлэлтийн дээд хэмжээнээс их байвал `IllegalArgumentException` шиднэ.

---

## 5. Unit Test

`GradeCalculatorTest` классад нийт **16 тестийн метод** хэрэгжүүлсэн.

Тестүүдэд дараах зүйлсийг шалгасан:

* Энгийн онооны утгууд
* Хязгаарын утгууд
* 0 болон 100 гэсэн онцгой утгууд
* `letterGrade()`-ийн буруу оролт
* `totalScore()`-ийн буруу оролт
* `assertThrows`
* `@DisplayName`
* `@ParameterizedTest`
* `@CsvSource`

### Хязгаарын утгын тестүүд

Дараах boundary утгуудыг тусгайлан шалгасан:

```text
90
89.99
60
59.99
0
100
```

Мөн `letterGrade()` болон `totalScore()` методын буруу оролтыг `assertThrows()` ашиглан шалгасан.

---

## 6. Parameterized Test

Нийт 2 `@ParameterizedTest` хэрэгжүүлсэн.

### 6.1 `letterGrade` Parameterized Test

`@CsvSource` ашиглан олон төрлийн онооны утгыг нэг тестийн методаар шалгасан.

```text
95    → A
90    → A
89.99 → B
80    → B
70    → C
60    → D
59.99 → F
0     → F
```

### 6.2 `totalScore` Parameterized Test

`@CsvSource` ашиглан нийт онооны хэд хэдэн хувилбарыг шалгасан.

```text
10, 40, 10, 10, 30 → 100
10, 30, 10, 10, 30 → 90
8, 35, 9, 8, 25    → 85
5, 20, 8, 7, 25    → 65
```

---

## 7. Тестийн үр дүн

Тестийг дараах командаар ажиллуулсан.

```bash
mkdir -p results
mvn test 2>&1 | tee results/mvn-test.txt
```

### Тестийн методын тоо

**Тестийн методын тоо: 16**

### Maven-ийн `Tests run` тоо

`results/mvn-test.txt` файлын хамгийн сүүлийн үр дүн:

```text
Tests run: 28,
Failures: 0,
Errors: 0,
Skipped: 0

BUILD SUCCESS
```

`@ParameterizedTest`-ийн `@CsvSource` доторх мөр бүр тусдаа test case хэлбэрээр ажилладаг тул **тестийн методын тоо болон Maven-ийн `Tests run` тоо ялгаатай байж болно**.

---

## 8. Mutation Testing

Mutation testing хийхдээ `GradeCalculator` классын `letterGrade()` методын дараах нөхцөлийг зориудаар өөрчилсөн.

Анхны нөхцөл:

```java
score >= 90
```

Mutation хийсний дараа:

```java
score > 90
```

Энэ өөрчлөлтийн дараа 90 оноо `A` биш болж, дараагийн нөхцөлд шилжих тул 90 онооны boundary test унах ёстой.

Mutation тестийг дараах командаар ажиллуулсан.

```bash
mvn test 2>&1 | tee results/mvn-test-mutant.txt
```

### Mutation-ийн үр дүн

```text
Tests run: 28,
Failures: 2,
Errors: 0,
Skipped: 0

BUILD FAILURE
```

Mutation-ийн үед **`90 оноо яг A дүн байх ёстой`** гэсэн тест унасан.

Энэ тест унасан нь `90` оноо A дүнгийн доод хязгаар болохыг зөв шалгаж байгааг харуулсан.

Mutation тестийн дараа кодыг буцааж:

```java
score >= 90
```

болгон засварласан.

Дараа нь:

```bash
mvn test
```

командыг дахин ажиллуулж, бүх тест амжилттай болсон.

```text
Failures: 0
Errors: 0
BUILD SUCCESS
```

---

## 9. Хамгийн сонирхолтой тест ба илрүүлсэн алдаа

Энэ лабораторийн хамгийн сонирхолтой тест нь **90 онооны boundary test** байсан. Учир нь 90 оноо нь `A` дүнгийн хамгийн бага оноо бөгөөд `score >= 90` нөхцөлөөр шалгагдах шаардлагатай. Mutation testing хийх үед энэ нөхцөлийг `score > 90` болгон зориудаар өөрчилсөн. Үүний дараа **`90 оноо яг A дүн байх ёстой`** гэсэн тест унасан. Энэ нь boundary value testing нь нөхцөлийн жижиг өөрчлөлтөөс үүссэн алдааг илрүүлж чадсаныг харуулсан. Мөн `89.99` оноог B, `60` оноог D, `59.99` оноог F гэж шалгаснаар дүнгийн хязгааруудыг хоёр талаас нь баталгаажуулсан. `assertThrows` ашигласан тестүүдээр буруу болон хэтэрсэн онооны үед `IllegalArgumentException` зөв үүсэж байгаа эсэхийг шалгасан. `@ParameterizedTest` болон `@CsvSource` ашигласнаар олон төрлийн оролтыг давтагдсан тестийн код багатайгаар шалгах боломжтой болсон.

---

## 10. Git Commit

Ажлын явцыг хэд хэдэн commit болгон хадгалсан.

```bash
git log --oneline
```

Commit-ууд нь төслийн үүсгэлт, Maven/JUnit тохиргоо, үндсэн код болон тестүүдийг хэрэгжүүлсэн үе шатуудыг тус тус харуулна.


---

## 11. Дүгнэлт

Энэ лабораторийн ажлаар JUnit 5 ашиглан Java програмын unit test бичиж, Maven ашиглан тестүүдийг ажиллуулж сурсан. `GradeCalculator` классын үндсэн үйлдлүүдийг шалгахын зэрэгцээ энгийн утга, boundary value болон буруу оролтын тестүүдийг хэрэгжүүлсэн. Мөн `assertThrows` ашиглан exception handling-ийг шалгасан. `@ParameterizedTest` болон `@CsvSource` ашиглан олон test case-ийг нэг тестийн методаар шалгасан. Mutation testing хийж `score >= 90` нөхцөлийг `score > 90` болгон өөрчлөхөд 90 онооны boundary test алдааг илрүүлж байгааг баталгаажуулсан. Mutation-ийг буцаан засварласны дараа бүх тест амжилттай ажиллаж, `BUILD SUCCESS` үр дүн гарсан.

---

## 12. Шалгах хуудас

* [x] Оюутны нэр, код README-д бичсэн
* [x] `java -version` оруулсан
* [x] `mvn -version` оруулсан
* [x] `GradeCalculator` хэрэгжүүлсэн
* [x] 8-аас дээш тестийн методтой
* [x] `@DisplayName` ашигласан
* [x] Boundary value test хийсэн
* [x] `assertThrows` ашигласан
* [x] `@ParameterizedTest` — `letterGrade`
* [x] `@ParameterizedTest` — `totalScore`
* [x] `results/mvn-test.txt` үүсгэсэн
* [x] `results/mvn-test-mutant.txt` үүсгэсэн
* [x] Mutation-ийн үед тест унасан
* [x] Mutation-ийг буцааж зассан
* [x] Эцсийн тест `BUILD SUCCESS`
* [x] 3+ commit хийсэн

