package unibo.testqakexample;


import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.comm22.utils.ColorsOut;


public class WasteServiceObserver implements CoapHandler{
    protected String history = "";
    @Override
    public synchronized void onLoad(CoapResponse response) {
        history += response.getResponseText();
        ColorsOut.outappl("WasteServiceObserver history=" + history, ColorsOut.MAGENTA);
    }

    @Override
    public void onError() {
        ColorsOut.outerr("WasteServiceCoapObserver observe error!");
    }

    public synchronized String getHistory() {
        return history;
    }

}


