FROM gradle AS build

WORKDIR /code
COPY . /code/

EXPOSE 4000
RUN gradle init
CMD ["gradle","run"] 