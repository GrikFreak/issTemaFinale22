FROM openjdk:11
EXPOSE 8030

COPY unibo.ledqak22-1.0.zip /
RUN unzip unibo.ledqak22-1.0.zip
WORKDIR /unibo.ledqak22-1.0/bin/
# i file necessari .json .pl .sh sono gia dentro bin

CMD [ "bash", "./unibo.ledqak22" ]
#CMD ["bash", "sleep 300000"]
#CMD sleep 3000000
