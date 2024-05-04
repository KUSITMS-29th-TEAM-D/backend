# open jdk 17 버전의 환경을 구성
FROM openjdk:17
#빌드된 파일의 위치를, 이미지 내부의 app.jar로 복사
COPY ./build/libs/jangkku-0.0.1-SNAPSHOT.jar app.jar
# 운영 및 개발에서 사용되는 환경 설정을 분리, 컨테이너를 싱행하면 자동으로 jar 파일을 행하도록 함
ENTRYPOINT ["java", "-jar", "app.jar"]
