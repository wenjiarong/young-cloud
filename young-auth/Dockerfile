FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER smallchill@163.com

#验证码字体包
RUN set -xe \
&& apk --no-cache add ttf-dejavu fontconfig

RUN mkdir -p /young/auth

WORKDIR /young/auth

EXPOSE 8100

ADD ./target/young-auth.jar ./auth.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "auth.jar"]

CMD ["--spring.profiles.active=prod"]
