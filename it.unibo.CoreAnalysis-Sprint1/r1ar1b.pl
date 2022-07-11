%====================================================================================
% r1ar1b description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8025").
 qactor( wastetruckmock, ctxwasteservice, "it.unibo.wastetruckmock.Wastetruckmock").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
