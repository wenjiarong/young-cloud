FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 15602400262@163.com

RUN mkdir -p /young/jpush

WORKDIR /young/jpush

EXPOSE 8800

ADD ./target/young-jpush.jar ./jpush.jar

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
    "jpush.jar" \
]