%====================================================================================
% wasteservicecore description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8025").
 qactor( wastetruck, ctxwasteservice, "it.unibo.wastetruck.Wastetruck").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
