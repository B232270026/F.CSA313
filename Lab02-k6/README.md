# Lab 02 - Гүйцэтгэлийн хэмжүүрийг k6-аар хэмжих

---

## 1. Оюутны мэдээлэл

- **Оюутан:** Б.Энхжин
- **Оюутны код:** B232270026
- **Хичээл:** Программ хангамжийн чанарын баталгаа ба туршилт
- **Лаборатори:** Lab 02

---

## 2. Лабораторийн зорилго

Энэ лабораторийн зорилго нь эдгээр хэмжүүрийг k6 хэрэгслээр БОДИТООР хэмжиж, ачаалал өсөхөд гүйцэтгэл хэрхэн өөрчлөгддөгийг өөрийн нүдээр харахад оршино.


Туршилтад зөвшөөрөгдсөн public test target болох: https://test.k6.io  ашиглана.

Энэ лабораторийн хүрээнд baseline тест, 5/30/100 VU-ийн тусдаа load test, stages test болон threshold-ийн PASS, FAIL тестүүдийг хийсэн.

---

## 3. Test Environment

- **Operating System:** Linux Debian
- **CPU:** 12th Gen Intel Core i7-1255U
- **RAM:** 16GB
- **Performance testing tool:** Grafana k6 v2.2.0
- **Test target:** `https://test.k6.io`
- **Baseline duration:** 30 seconds
- **Load comparison duration:** 1 minute
- **Virtual Users:** 5, 30, 100 VU
    
---

## 4. Ашигласан технологи

| Технологи    | Ашиглалт                               |
| ------------ | -------------------------------------- |
| Grafana k6   | Performance / Load testing             |
| JavaScript   | k6 test script бичих                   |
| Linux Debian | Туршилтын орчин                        |
| Git / GitHub | Source code болон test result хадгалах |
| test.k6.io   | Зөвшөөрөгдсөн public test target       |

---

## 5. Test Target
Энэ лабораторийн туршилтад дараах public test target-ийг ашигласан.
---

## 6. Test Scenarios

### 6.1 Baseline Test
| Metric                |         Result |
| --------------------- | -------------: |
| Virtual Users         |           5 VU |
| Duration              |            30s |
| Average response time |      173.93 ms |
| Minimum               |       57.01 ms |
| Median                |      173.60 ms |
| Maximum               |      445.04 ms |
| p(90)                 |      271.01 ms |
| **(p95)**             |  **291.27 ms** |
| Throughput            | 7.055451 req/s |
| Error rate            |          0.00% |
| Completed iterations  |            110 |


### 6.2 5 VU Test
| Metric                |          Value |
| --------------------- | -------------: |
| VU                    |              5 |
| Duration              |             1m |
| Average response time |      178.17 ms |
| p(90)                 |      299.86 ms |
| p(95)                 |      321.04 ms |
| Maximum               |      470.89 ms |
| Throughput            | 7.210176 req/s |
| Error rate            |          0.00% |
| Completed iterations  |            220 |
p(95)

### 6.3 30 VU Test
| Metric                |           Value |
| --------------------- | --------------: |
| VU                    |              30 |
| Duration              |              1m |
| Average response time |       160.99 ms |
| p(90)                 |       272.01 ms |
| p(95)                 |       278.77 ms |
| Maximum               |       892.28 ms |
| Throughput            | 43.946765 req/s |
| Error rate            |           0.00% |
| Completed iterations  |            1347 |


### 6.4 100 VU Test
| Metric                |            Value |
| --------------------- | ---------------: |
| VU                    |              100 |
| Duration              |               1m |
| Average response time |        149.44 ms |
| p(90)                 |        231.91 ms |
| p(95)                 |        243.10 ms |
| Maximum               |           1.49 s |
| Throughput            | 150.148379 req/s |
| Error rate            |            0.00% |
| Completed iterations  |             4593 |


### 6.5 Stages Test
| Metric                |            Value |
| --------------------- | ---------------: |
| Total duration        |           2m 30s |
| Maximum VU            |              100 |
| Average response time |        146.07 ms |
| Minimum               |         52.72 ms |
| Median                |        217.65 ms |
| Maximum               |           1.58 s |
| p(90)                 |        230.29 ms |
| **p(95)**             |    **233.74 ms** |
| Throughput            |  46.866448 req/s |
| Error rate            |            0.00% |
| Completed iterations  |             3517 |
| Checks                | 3517/3517 (100%) |

---

## 7. Performance Test Results

|  VU |       p90 |       p95 |       Throughput | Error Rate |
| --: | --------: | --------: | ---------------: | ---------: |
|   5 | 299.86 ms | 321.04 ms |   7.210176 req/s |      0.00% |
|  30 | 272.01 ms | 278.77 ms |  43.946765 req/s |      0.00% |
| 100 | 231.91 ms | 243.10 ms | 150.148379 req/s |      0.00% |

---

## 8. Threshold Test

### 8.1 PASS

### 8.2 Intentional FAIL

---

## 9. Screenshots / Evidence

---

## 10. Дүгнэлт
