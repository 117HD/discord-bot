FROM python:3.10-slim

WORKDIR /code
COPY . /code/

EXPOSE 4000

RUN gradle init
CMD ["gradle","run"] 