### 📕 SearchBookApplication

**카카오 API를 통해 **도서를 검색**하고, 검색한 도서를 **데이터 베이스에 저장**하고 , **조회**할 수 있는 애플리케이션입니다!



## 프로그램 실행 및 흐름

* 실행

1. H2 database를 resoucre/META-INF/persistence.xml에 설정대로 설정을 진행합니다. (자신의 데이터베이스에 알맞게 수정해도 됨.)

2. SearchBookApplication.java를 통해 main 함수를 실행시킵니다.

   

* 흐름

1. 검색하고 싶은 도서를 입력합니다.

2. 검색과 관련한 도서가 출력됩니다.

   ![검색 도서 출력](https://github.com/FastCampusKDTBackend/KDT_Y_BE_Java_Assignment2/blob/SimJaeCheol/%EA%B3%BC%EC%A0%9C2/%EB%8F%84%EC%84%9C%20%EA%B2%80%EC%83%89%20%EA%B2%B0%EA%B3%BC.png)

3. 검색 결과로 나온 도서를 설정한 데이터베이스에 저장하고 싶으면 Y, 하고 싶지 않으면 N을 누릅니다.

4. 데이터베이스에 저장되있던 도서 검색 결과가 상위 10개 출력이 됩니다.

   ![검색 도서 출력](https://github.com/FastCampusKDTBackend/KDT_Y_BE_Java_Assignment2/blob/SimJaeCheol/%EA%B3%BC%EC%A0%9C2/%ED%85%8C%EC%9D%B4%EB%B8%94%20%EB%A6%AC%EC%8A%A4%ED%8A%B8.png)



## 디렉토리 구성

* searchbook

  **SearchBookApplication.java** : Application을 실행하기 위한 main 메소드가 있는 클래스이다. 

  사용자의 입력이 이루어진다.

  * dto

    **BookDto.java**: 계층 간 데이터 교환을 하기 위해 사용하는 Book 관련 객체  

  * entity

    **Book.java**: DAO 객체가 데이터베이스로 부터 ORM되는 객체이자, Book 테이블에 맵핑되는 객체

  * repository

    **BookRepository.java**: DAO 객체이자, Book Table에 Access하는 객체

  * service

    **BookService.java**: 핵심적인 기능을 제공하는 객체 (도서 검색, 최근 검색 도서 출력 등)



## 의존성

- Java 17, maven
- 데이터베이스 라이브러리 : org.hibernate 
- HTTP 통신 라이브러리 : httpclient
- DBMS : com.h2database
- Json 관련 라이브러리 : org.Json
