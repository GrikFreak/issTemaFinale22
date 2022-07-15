%====================================================================================
% wasteservicecorerobot description   
%====================================================================================
context(ctxledqak22, "127.0.0.1",  "TCP", "8030").
context(ctxsonarqak22, "127.0.0.1",  "TCP", "8035").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8025").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( led, ctxledqak22, "external").
  qactor( sonar, ctxsonarqak22, "external").
  qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
