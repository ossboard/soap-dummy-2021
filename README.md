# soap-dummy-2021

## 설명
> soap 더미 API 
> 외부 연동 개발할때 가상 테스트

## 스팩
- java 1.8.x 이상
- gradle 6.3.x 이상
- spring-boot 2.2.x
- use `logback` logger

### 빌드
```
$ ./gradlew build -x test
$ ./gradlew bootRun -x test --args='-Dspring.profiles.active=developer'
``` 

### 실행
``` 
$ java -jar soap-2021.1.war

```
