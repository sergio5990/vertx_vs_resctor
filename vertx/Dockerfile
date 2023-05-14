FROM openjdk:17-alpine as build

WORKDIR /app
RUN apk add binutils # for objcopy, needed by jlink
RUN jlink --output jre --compress=2 --no-header-files --no-man-pages --strip-debug --add-modules \
    java.base,jdk.httpserver,java.sql,java.net.http,jdk.crypto.ec,java.naming,java.management,jdk.unsupported,java.management,java.management.rmi,jdk.management.agent

FROM alpine as final
RUN adduser -S user
WORKDIR /app

COPY --from=build /app/jre /app/jre
COPY build/libs/starter-1.0.0-SNAPSHOT-fat.jar /app/starter-1.0.0-SNAPSHOT-fat.jar

# Run under non-privileged user with minimal write permissions
USER user

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=80"
CMD jre/bin/java -Dcom.sun.management.jmxremote=true \
-Dcom.sun.management.jmxremote.port=8051 \
-Dcom.sun.management.jmxremote.local.only=false  \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.rmi.port=8051 \
-Djava.rmi.server.hostname=localhost \
-Dvertx.disableDnsResolver=true \
-jar starter-1.0.0-SNAPSHOT-fat.jar