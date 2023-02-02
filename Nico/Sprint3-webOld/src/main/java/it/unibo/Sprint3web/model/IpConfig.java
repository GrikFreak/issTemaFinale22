package it.unibo.Sprint3web.model;

public class IpConfig {
    private String ip_led;
    private String ip_ws;
    private int port_led;
    private int port_ws;

    public String getIp_led() {
        return ip_led;
    }

    public void setIp_led(String ip_led) {
        this.ip_led = ip_led;
    }

    public String getIp_ws() {
        return ip_ws;
    }

    public void setIp_ws(String ip_ws) {
        this.ip_ws = ip_ws;
    }

    public int getPort_led() {
        return port_led;
    }

    public void setPort_led(int port_led) {
        this.port_led = port_led;
    }

    public int getPort_ws() {
        return port_ws;
    }

    public void setPort_ws(int port_ws) {
        this.port_ws = port_ws;
    }
}