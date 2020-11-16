FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 631041490@qq.com

RUN mkdir -p /young/tftp

WORKDIR /young/tftp

EXPOSE 8106

ADD ./target/young-service-tftp.jar ./tftp.jar

ENTRYPOINT [ \
    "java", \
    "-XX:MetaspaceSize=256m", \
    "-XX:MaxMetaspaceSize=512m", \
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
    "tftp.jar" \
]

CMD ["--spring.profiles.active=prod"]
