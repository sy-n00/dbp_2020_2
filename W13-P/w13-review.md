# 과제영상
- employees 테이블에서 컬럼 삭제


# 새로 배운 내용 및 정리
- JSP
HTML 내부에 java 코드를 입력하여 웹 서버에서 동적으로 웹 브라우저를 관리하는 언어

- JSP 구동원리
JSP 실행 -> JSP에서 생성된 서블릿 실행
```
1) 클라이언트가 jsp 실행 요청 -> 서블릿 컨테이너가 jsp 파일에 대응하는 자바 서블릿 찾아서 실행
2) 대응하는 서블릿이 x / jsp파일 변경 -> jsp 엔진을 통해 서블릿 자바 소스 생성.
3) 자바 컴파일러가 서블릿 자바소스 -> 클래스 파일로 컴파일 (jsp 파일 변경될 때마다 반복
4) service()메소드 호출 -> 생성한 html 화면을 웹 브라우저로 보냄
```

- JSP 구성요소
```
1) 템플릿 데이터 : 클라이언트로 출력되는 콘텐츠
2) JSP 전용 태그 : 서블릿 생성 시 특정 자바 코드로 바뀌는 태그
- 지시자 <%@ %>
- 스크립트릿 <% %>
- 표현식 <%= %>
3) JSP 내장 객체 : JSP 기술 사양에 정의된 필수적인 9개 객체
```

# 문제 발생 및 해결 내용
- 오라클 db 연결 테스트(핑 보내는 테스트)에서 연결 실패
>- 터미널에서 .\shutdown.bat실행 후 다시 .\startup.bat실행<br>하지만 해결되지 않음
>- 이클립스 종료 후 재실행<br>하지만 해결되지 않음.
>- 핑 테스트 실패했다고 나왔음에도 finish버튼 클릭<br>'Createing connections to New Oracle.' has encountered a problem. Could not connect to New Oracle이라는 에러 상자와 함께 아래 오류 결과가 표시됨
```
Could not connect to New Oracle.
Error create SQL Model Connection connection to New Oracle.(Error:IO 오류: The Network Adapter could not establish the connection
Error createing JDBC Connection connection to New Oracle.(Error:IO 오류: The Network Adapter could not establish the connection)
IO 오류: The Network Adapter could not establish the connection
```
>- 그냥 OK 버튼 누르고 계속 실습 진행했더니 Data Source Explorer 창의 Database Connections 폴더 아래에 New Oracle이 생김.

- 과제 진행 후 영상을 찍으려고 실행하는 도중 connect는 성공했지만 처리 완료 alert가 나오지 않는 문제 발생
>- 이클립스 종료 후 재실행<br>하지만 해결되지 않음.
>- 다시 tomcat의 server.xml수정 후(수정했다고는 했지만 포트번호를 변경하지는 않음. 계속 8090사용) 재실행<br>아래 오류 발생
```
Several ports (8005, 8090) required by Tomcat v8.5 Server at localhost are already in use. The server may already be running in another process, or a system process may be using the port. To start this server you will need to stop the other process or change the port number(s).
```

# 참고 내용
- Several ports (8005, 8080, 8009) required by Tomcat v7.0 Server at localhost are already in use.<br>
https://myblog.opendocs.co.kr/archives/1702

- 이클립스 에러해결 The superclass "javax.servlet.http.HttpServlet" was not found on the Java Build Path<br>
https://simuing.tistory.com/168

- [톰캣 서버 에러] Could not publish server configuration for Tomcat v9.0 Server at localhost.<br>
https://hyoni-k.tistory.com/56

- [톰캣]Multiple Contexts have a path of "/spring". 에러 해결!
https://bumcrush.tistory.com/132

# 회고
- 오늘도 어김없이 환경설정에서 문제 발생! 이쯤되면 오류 없이 넘어가면 섭섭할 것 같다. 분명 테스트할때는 추가와 삭제가 제대로 되어서 처리 alert 메시지까지 봤는데 과제 영상 찍으려하니까 어떻게 이렇게 갑자기 오류가 발생할 수 있을까..
- 오류를 해결하면 다른 오류가 나온다.. 그리고 다른 오류를 고치면 또다른 오류가 나온다.. 그리고 또다른 오류를 고치면 처음에 해결했던 오류가 다시 나온다.. 끊임없는 오류의 수레바퀴...
