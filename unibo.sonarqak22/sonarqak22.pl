%====================================================================================
% sonarqak22 description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "unibo/sonarqak22").
context(ctxwasteservice, "10.5.5.1",  "TCP", "8025").
context(ctxsonarqak22, "localhost",  "TCP", "8085").
 qactor( wastetruckmock, ctxwasteservice, "external").
  qactor( sonarqak22, ctxsonarqak22, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmock, ctxsonarqak22, "it.unibo.sonarmock.Sonarmock").
