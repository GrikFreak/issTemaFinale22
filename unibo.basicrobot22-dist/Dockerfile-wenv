FROM openjdk:11
EXPOSE 8020

COPY unibo.basicrobot22-1.0.zip /
RUN unzip unibo.basicrobot22-1.0.zip
WORKDIR /unibo.basicrobot22-1.0/bin/
# i file necessari .json .pl .sh sono gia dentro bin

CMD [ "bash", "./unibo.basicrobot22" ]