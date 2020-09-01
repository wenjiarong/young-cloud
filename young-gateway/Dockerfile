FROM adoptopenjdk/openjdk8-openj9:alpine-slim

MAINTAINER smallchill@163.com

#验证码字体包
RUN set -xe \
&& apk --no-cache add ttf-dejavu fontconfig

RUN mkdir -p /young/gateway

WORKDIR /young/gateway

EXPOSE 8100

ADD ./target/young-gateway.jar ./gateway.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "gateway.jar"]

CMD ["--spring.profiles.active=prod"]
