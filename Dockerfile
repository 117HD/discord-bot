FROM openjdk:12-jdk-alpine

WORKDIR /code
COPY . /code/

EXPOSE 4000

RUN gradle init
CMD ["gradle","run"] 