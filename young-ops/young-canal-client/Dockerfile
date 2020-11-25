FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 15602400262@163.com

RUN mkdir -p /young/canal-client

WORKDIR /young/canal-client

EXPOSE 8400

ADD ./target/young-canal-client.jar ./canal-client.jar

ENTRYPOINT [ \
    "java", \
    "-XX:MetaspaceSize=128m", \
    "-XX:MaxMetaspaceSize=256m", \
    "-Xms512m", \
    "-Xmx512m", \
    "-Xmn256m", \
    "-Xss512k", \
    "-XX:SurvivorRatio=8", \
    "-XX:+UseConcMarkSweepGC", \
    "-Duser.timezone=GMT+08", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Xshareclasses", \
    "-Xquickstart", \
    "-jar", \
    "canal-client.jar" \
]
