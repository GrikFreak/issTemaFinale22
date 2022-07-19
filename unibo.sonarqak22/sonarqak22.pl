%====================================================================================
% sonarqak22 description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "unibo.sonarqak22").
context(ctxwasteservice, "127.0.0.1",  "TCP", "8025").
context(ctxsonarqak22, "localhost",  "TCP", "8035").
 qactor( wasteservice, ctxwasteservice, "external").
  qactor( sonarqak22, ctxsonarqak22, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmock, ctxsonarqak22, "it.unibo.sonarmock.Sonarmock").
