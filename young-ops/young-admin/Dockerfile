FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 631041490@qq.com

RUN mkdir -p /young/admin

WORKDIR /young/admin

EXPOSE 8500

ADD ./target/young-admin.jar ./admin.jar

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
    "admin.jar" \
]