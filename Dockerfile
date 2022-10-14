FROM gradle AS build

WORKDIR /code
COPY . /code/

RUN gradle --version
# EXPOSE 4000
# RUN gradle init
# CMD ["gradle","run"] 