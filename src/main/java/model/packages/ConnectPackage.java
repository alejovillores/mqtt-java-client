package model.packages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        this.payload = 2 + client_id.length();
    }

    public void setAuth(String username, String password) {
        this.username = username;
        this.password = password;
        this.payload += 4 +(username.length() + password.length());
    }

    public void setWillData(String will_topic, String will_message) {
        this.will_message = will_message;
        this.will_topic = will_topic;
        this.payload += 4+ (will_topic.length() + will_message.length());
    }
    public void setKeep_alive(int keep_alive) {
        this.keep_alive = keep_alive;
    }

    @Override
    public ArrayList<Byte> toBytes() {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add(this.headerBytes());
        bytes.addAll(this.remainingLength());
        bytes.addAll(this.mqtt_bytes());
        bytes.add((byte)0x04);
        bytes.add(this.flags());
        bytes.addAll(this.keep_alive_bytes());
        bytes.addAll(this.payload_bytes());

        return bytes;
    }

    private List<Byte> keep_alive_bytes() {
        List<Byte> l = new ArrayList<>();
        // int -> 4 bytes -> (8 8 NO) 8 8, solo tengo que usar 2 bytes.
        byte msb = (byte ) ((this.keep_alive & 0x0000ff00) >> 8);
        byte lsb = (byte ) (this.keep_alive & 0x000000ff);

        l.add(msb);
        l.add(lsb);
        return l;
    }

    public byte headerBytes(){
        return (byte)this.package_id;
    }
    public byte flags() {

        byte flag = 0x00;
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

    public ArrayList<Byte> remainingLength() {
        int  x = (10 + this.payload);

        ArrayList<Byte> bytes = new ArrayList<>();
        while (x > 0) {
            int encoded_byte = (x % 128);
            x /= 128;

            if (x > 0) {
                encoded_byte |= 128;
            }
            bytes.add((byte) encoded_byte);
        }
        return bytes;
    }

    public ArrayList<Byte> mqtt_bytes(){
        ArrayList<Byte> mqtt_protocol =  new ArrayList<>();
        mqtt_protocol.add((byte)0x00);
        mqtt_protocol.add((byte)0x04);
        mqtt_protocol.add((byte)0x4d);
        mqtt_protocol.add((byte)0x51);
        mqtt_protocol.add((byte)0x54);
        mqtt_protocol.add((byte)0x54);

        return mqtt_protocol;
    }

    public ArrayList<Byte> payload_bytes() {
        ArrayList<Byte> payload = new ArrayList<>();


        byte[] bytes = this.client_id.getBytes(StandardCharsets.UTF_8);
        short lenByte = (short) this.client_id.length();
        byte msb = (byte) (lenByte >> 8);
        byte lsb = (byte) (lenByte & 0x00ff);

        payload.add(msb);
        payload.add(lsb);
        for (byte b : bytes) {
            payload.add(b);
        }

        if (this.will_topic != null) {
            lenByte = (short) this.will_topic.length();
            msb = (byte) (lenByte >> 8);
            lsb = (byte) (lenByte & 0x00ff);
            payload.add(msb);
            payload.add(lsb);
            bytes = this.will_topic.getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                payload.add( b);
            };
        }

        if (this.will_message != null ) {
            lenByte = (short) this.will_message.length();
            msb = (byte) (lenByte >> 8);
            lsb = (byte) (lenByte & 0x00ff);
            payload.add(msb);
            payload.add(lsb);
            bytes = this.will_message.getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                payload.add(b);
            }
        }

        if (this.username != null ){
            lenByte = (short) this.username.length();
            msb = (byte) (lenByte >> 8);
            lsb = (byte) (lenByte & 0x00ff);
            payload.add(msb);
            payload.add(lsb);
            bytes = this.username.getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                payload.add(b);
            }
        }
        if (this.password != null ) {
            lenByte = (short) this.password.length();
            msb = (byte) (lenByte >> 8);
            lsb = (byte) (lenByte & 0x00ff);
            payload.add(msb);
            payload.add(lsb);
            bytes = this.password.getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                payload.add( b);
            }
        }
        return payload;
    }

}
