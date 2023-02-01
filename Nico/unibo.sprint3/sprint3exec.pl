%====================================================================================
% sprint3exec description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxledqak22, "127.0.0.1",  "TCP", "8030").
context(ctxwasteservice, "localhost",  "TCP", "8025").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( led, ctxledqak22, "external").
  qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( distancefilter, ctxwasteservice, "it.unibo.distancefilter.Distancefilter").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
