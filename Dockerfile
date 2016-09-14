FROM openjdk:8u102-jdk

RUN apt-get install wget \
    && wget https://github.com/jwilder/dockerize/releases/download/v0.2.0/dockerize-linux-amd64-v0.2.0.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.2.0.tar.gz

ARG IMAGE_NAME
ARG IMG_VER
ARG ENV

ENV VERSION=${IMG_VER:-v1.0.0} NAME=${IMAGE_NAME:-derpName} TARGET=${ENV}

ADD $NAME-$VERSION.tgz /opt

COPY rhizome.yaml /opt
COPY rhizome.yaml.prod /opt

RUN cd /opt/$NAME-$VERSION/lib \
  && mv /opt/rhizome.yaml$TARGET ./rhizome.yaml \
  && jar vfu $NAME-$VERSION.jar rhizome.yaml \
  && rm /opt/rhizome.yaml*

CMD dockerize -wait tcp://cassandra:9042 -timeout 300s; /opt/$NAME-$VERSION/bin/$NAME cassandra spark

EXPOSE 5701
