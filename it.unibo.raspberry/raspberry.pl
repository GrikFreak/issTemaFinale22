%====================================================================================
% raspberry description   
%====================================================================================
context(ctxwasteservice, "192.168.1.131",  "TCP", "8025").
context(ctxraspberrypi, "localhost",  "TCP", "8030").
 qactor( wasteservice, ctxwasteservice, "external").
  qactor( sonarsimulator, ctxraspberrypi, "sonarSimulator").
  qactor( sonardatasource, ctxraspberrypi, "sonarHCSR04Support2021").
  qactor( datacleaner, ctxraspberrypi, "dataCleaner").
  qactor( distancefilter, ctxraspberrypi, "distanceFilter").
  qactor( sonarqak22, ctxraspberrypi, "it.unibo.sonarqak22.Sonarqak22").
  qactor( sonarmastermock, ctxraspberrypi, "it.unibo.sonarmastermock.Sonarmastermock").
  qactor( led, ctxraspberrypi, "it.unibo.led.Led").
