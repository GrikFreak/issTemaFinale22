package it.unibo.Sprint3web;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.comm22.utils.ColorsOut;

public class LedCoapObserver implements CoapHandler{

    @Override
    public void onLoad(CoapResponse response) {
        ColorsOut.outappl("LedCoapObserver changed!" + response.getResponseText(), ColorsOut.GREEN);
        //send info over the websocket
        WebSocketConfiguration.wshandler.sendToAll("" + response.getResponseText());
        //simpMessagingTemplate.convertAndSend(WebSocketConfig.topicForTearoomstatemanager, new ResourceRep("" + HtmlUtils.htmlEscape(response.getResponseText())));
    }

    @Override
    public void onError() {
        ColorsOut.outerr("LedCoapObserver observe error!");
    }
}