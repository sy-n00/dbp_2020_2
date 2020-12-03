# 과제영상


# 새로 배운 내용 및 정리
- MongoDB
<br>2009년 발표된, 크로스 플랫폼 도큐먼트 지향 데이터베이스 시스템 
<br>데이터를 테이블형태로 저장하는 것이 아니라 {key:value} 형태의 document형식으로 저장. 
<br>(JSON-JavaScript Object Notation 형태와 유사함)

- MongoDB 구조
<br>키-값 쌍 -> 도큐먼트
<br>여러 개의 도큐먼트 모음 -> 컬렉션
<br>컬렉션의 모음 -> 데이터베이스

- 터미널에서 데이터베이스 사용 명령어
<br>use DB이름
<br>해당 디비가 없으면 이러한 이름을 사용할 것이라는 것을 예고(완전히 생성되지는 않고 도큐먼트를 하나 생성해야 디비가 만들어지는것임)

### MongoDB에서 데이터 조작법
##### 조회
- DB이름.컬렉션이름.find()
<br>전체 데이터 조회

- DB이름.컬렉션이름.find({x:1})
<br>특정 데이터 조회 (x==1인 데이터만 조회)

- DB이름.컬렉션이름.find({"x.0":1})
<br>특정 데이터 조회-key값에 value가 여러개인 경우 (키 x의 첫번째 데이터가 1인 데이터만 조회)

- DB이름.컬렉션이름.find({x:1}, {y:false})
<br>특정 key값을 제외한 데이터 조회 (x==1인 데이터를 키가 y인 값만 빼고 조회-일부속성만 조회하는것과 비슷)

- var cursor = DB이름.컬렉션이름.find() 
<br>cursor라는 변수에 데이터의 첫번째 항목을 가리키는 커서를 담음

- cursor.next()
<br>다음 값으로 커서 이동, 다음 값이 없으면 오류문구 출력

- cursor.hasNext()
<br>다음 값이 존재하는지 확인

##### 수정
- DB이름.컬렉션이름.replaceOne({x:2}, {x:10, y:11})
<br>해당 조건의 도큐먼트 하나를 수정 (x==2인 것 하나를 x=10, y=11로 수정)

- DB이름.컬렉션이름.replaceOne({x:2}, {x:10, y:11}, {upsert:true})
<br>해당 조건의 도큐먼트 하나를 수정. 없으면 도큐먼트 추가

- DB이름.컬렉션이름.updateMany({x:4}, {$set: {y:15}}) 
<br>여러 도큐먼트 수정 (x==4인 것 전부 y=15로 수정)

- DB이름.컬렉션이름.updateMany({x:4}, {$set:{"y.$[e]":17}}, {arrayFilters:[{e:7}]}) 
<br>여러 도큐먼트 수정-value값 여러개인 것 중 arrayFilter에 해당하는 조건을 가진 value만 수정
<br>(x==4인 것 중 y의 값에서 7인 것을 17로 수정)

##### 삭제
- DB이름.컬렉션이름.deleteOne({x:1})
<br>해당 조건의 도큐먼트 하나를 삭제 (x==1인 도큐먼트 삭제)

- DB이름.컬렉션이름.deleteMany({})
<br>도큐먼트 전체 삭제

- DB이름.컬렉션이름.drop()
<br>컬렉션 삭제

- DB이름.dropDatabase()
<br>현재 사용중인 데이터베이스 삭제

# 문제 발생 및 해결 내용


# 참고 내용
- [MongoDB] 강좌 1편: 소개, 설치 및 데이터 모델링
<br>https://velopert.com/436
- [MongoDB] 강좌 2편: Database/Collection/Document 생성·제거
<br>https://velopert.com/457
- [MongoDB] 강좌 3편 Document Query(조회) – find() 메소드
<br>https://velopert.com/479
- [MongoDB] 강좌 5편 Document 수정 – update() 메소드
<br>https://velopert.com/545


# 회고
- 터미널에서 데이터를 조작할 때 명령어가 길면 가독성이 떨어지는데 mongoDB의 compass를 사용하니까 데이터의 가독성도 훨씬 좋아지고 데이터의 삽입, 삭제, 수정도 매우 간단해져서 편리했다.
- 괄호가 대괄호, 중괄호 여러개가 들어가서 잘못 입력하기 쉬운것같다. 특히 하나의 key값에 여러개의 value값이 있는 데이터를 처리할 때 꼭 닫는 괄호 하나씩 빠트려서 한번 더 확인하게 된다.
