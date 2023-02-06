FROM openjdk:11
EXPOSE 8025

COPY unibo.wasteservice-1.0.zip /
RUN unzip unibo.wasteservice-1.0.zip
WORKDIR /unibo.wasteservice-1.0/bin/
# i file necessari .pl sono gia dentro bin

CMD [ "bash", "./unibo.wasteservice" ]