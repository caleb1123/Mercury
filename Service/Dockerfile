FROM maven:latest
RUN mkdir /obo \
WORKDIR /obo \
COPY . .
EXPOSE 8088
CMD ["mvn", "spring-boot:run"]
