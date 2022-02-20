package model.packages;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ConnectPackage implements MqttPackage {
    final private int package_id = 0x10;
    private String client_id;
    private String username;
    private String password;
    private boolean clean_session;
    private boolean will_retain;
    private String will_message;
    private String will_topic;
    private int qos;
    private int payload;
    private int keep_alive;



    public ConnectPackage(String client_id, boolean clean_session, boolean will_retain) {
        super();
        this.clean_session = clean_session;
        this.client_id = client_id;
        this.will_retain = will_retain;
        this.qos = 0;
        this.payload = 0;
    }

    public void setAuth(String username, String password) {
        this.username = username;
        this.password = password;
        this.payload += username.length() + password.length();
    }

    public void setWillData(String will_topic, String will_message) {
        this.will_message = will_message;
        this.will_topic = will_topic;
        this.payload += will_topic.length() + will_message.length();
    }
    public void setKeep_alive(int keep_alive) {
        this.keep_alive = keep_alive;
    }

    @Override
    public ArrayList<Integer> toBytes() {
        ArrayList<Integer> bytes = new ArrayList<Integer>(this.package_id);
        bytes.addAll(this.remainingLength());
        bytes.addAll(this.mqtt_bytes());
        bytes.add(0x04);
        bytes.add(this.flags());
        bytes.add(this.keep_alive);
        bytes.addAll(this.payload_bytes());

        return bytes;
    }

    private int flags() {

        int flag = 0x00;
        if (this.username != null ){
            flag |= 1 << 7;
        }
        if (this.password != null ) {
            flag |= 1 << 6;
        }
        if (this.will_retain) {
            flag |= 1 << 5;
        }
        flag |= this.qos << 3;
        if (this.will_message != null ) {
            flag |= 1 << 2;
        }
        if (this.clean_session) {
            flag |= 1 << 1;
        }

        return flag;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    private ArrayList<Integer> remainingLength() {
        int  x = 10 + this.payload;

        ArrayList<Integer> bytes = new ArrayList<Integer>();
        while (x > 0) {
            int encoded_byte = (x % 128);
            x /= 128;

            if (x > 0) {
                encoded_byte |= 128;
            }
            bytes.add(encoded_byte);
        }
        return bytes;
    }

    private ArrayList<Integer> mqtt_bytes(){
        ArrayList<Integer> mqtt_protocol =  new ArrayList<Integer>();
        mqtt_protocol.add(0x00);
        mqtt_protocol.add(0x04);
        mqtt_protocol.add(0x4d);
        mqtt_protocol.add(0x51);
        mqtt_protocol.add(0x54);
        mqtt_protocol.add(0x54);

        return mqtt_protocol;
    }

    private ArrayList<Integer> payload_bytes() {
        ArrayList<Integer> payload = new ArrayList<Integer>();
        payload.add(Integer.decode(this.client_id));
        if (this.username != null ){
            payload.add(Integer.decode(this.username));
        }
        if (this.password != null ) {
            payload.add(Integer.decode(this.password));
        }
        if (this.will_topic != null) {
            payload.add(Integer.decode(this.will_topic));
        }

        if (this.will_message != null ) {
            payload.add(Integer.decode(this.will_message));
        }

        return payload;
    }

}
