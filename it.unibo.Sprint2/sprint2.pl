%====================================================================================
% sprint2 description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxraspberrypi, "192.168.1.131",  "TCP", "8030").
context(ctxwasteservice, "localhost",  "TCP", "8025").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( led, ctxraspberrypi, "external").
  qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
