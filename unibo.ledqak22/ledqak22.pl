%====================================================================================
% ledqak22 description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "unibo/ledqak22").
context(ctxledqak22, "localhost",  "TCP", "8030").
 qactor( led, ctxledqak22, "it.unibo.led.Led").
