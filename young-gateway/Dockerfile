FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 631041490@qq.com

RUN mkdir -p /young/gateway

WORKDIR /young/gateway

EXPOSE 80

ADD ./target/young-gateway.jar ./gateway.jar

ENTRYPOINT [ \
    "java", \
    "-XX:MetaspaceSize=256m", \
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
    "gateway.jar" \
]