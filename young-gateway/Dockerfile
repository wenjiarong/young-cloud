FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 631041490@qq.com

RUN mkdir -p /young/gateway

WORKDIR /young/gateway

EXPOSE 9000

ADD ./target/young-gateway.jar ./gateway.jar

ENTRYPOINT [ \
    "java", \
    "-XX:MetaspaceSize=512m", \
    "-XX:MaxMetaspaceSize=512m", \
    "-Xms1024m", \
    "-Xmx1024m", \
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

CMD ["--spring.profiles.active=prod"]
