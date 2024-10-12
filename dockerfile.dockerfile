FROM openjdk:11
COPY . /app
WORKDIR /app
RUN javac HelloWorld.java
CMD ["java", "HelloWorld"]
