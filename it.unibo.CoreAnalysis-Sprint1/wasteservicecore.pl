%====================================================================================
% wasteservicecore description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8025").
 qactor( pathexec, ctxbasicrobot, "external").
  qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
