[04:50:02.693] > ssh: connect to host 127.0.1.1 port 22: Connection refused
> ]0;C:\WINDOWS\System32\cmd.exe
[04:50:02.693] Got some output, clearing connection timeout
[04:50:02.714] > 프로세스에서 없는 파이프에 쓰려고 했습니다.
이런 오류 발생. 분명히 포트 22, 80, 443열려있는거 확인했는데...
>>>> 가상머신 다시 새로 설치하여 해결

새폴더를 추가하려고 하니까 퍼미션 오류발생
>>> 권한이 내가 만든 계정인 mid가 아니라 root계정에 있었기 때문에 이러한 문제 발생
>>> 원하는 디렉토리의 상위 디렉토리에서 sudo chown -R mid.mid /var/www/라고 명령어 입력하여 권한 바꿔줌.

데이터를 다운받으려고 SOURCE C:/temp/sakila-db/sakila-schema.sql; 명령을 치니까 
ERROR: Failed to open file 'C:/temp/sakila-db/sakila-schema.sql', error: 2
이러한 명령 발생.
