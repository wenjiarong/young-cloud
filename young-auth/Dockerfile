FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER 631041490@qq.com

#验证码字体包
RUN set -xe \
&& apk --no-cache add ttf-dejavu fontconfig

RUN mkdir -p /young/auth

WORKDIR /young/auth

EXPOSE 8100

ADD ./target/young-auth.jar ./auth.jar

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
    "auth.jar" \
]

CMD ["--spring.profiles.active=prod"]
