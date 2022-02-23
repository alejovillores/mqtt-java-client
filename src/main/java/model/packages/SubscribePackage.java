package model.packages;

import model.MqttSubscription;

import java.util.ArrayList;

public class SubscribePackage implements MqttPackage {

    private short packetID;
    private ArrayList<MqttSubscription> subscriptions;
    private int payloadLength;

    public SubscribePackage(short packetID) {
        this.packetID = packetID;
        this.subscriptions = new ArrayList<>();
        this.payloadLength = 0;
    }

    @Override
    public ArrayList<Byte> toBytes() {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte) 0x82);
        bytes.addAll(this.remainingLength());
        bytes.add((byte)(this.packetID >> 8));
        bytes.add((byte)(this.packetID & 0x00ff));
        bytes.addAll(this.payloadBytes());

        return bytes;
    }

    private ArrayList<Byte> payloadBytes() {
        ArrayList<Byte> payloadBytes = new ArrayList<>();
        for (MqttSubscription sb: this.subscriptions){
            payloadBytes.addAll(sb.packetIDBytes());
            payloadBytes.add(sb.qosBytes());
        }
        return payloadBytes;
    }

    public void addSubscription(MqttSubscription mqttSubscription) {
        this.payloadLength += mqttSubscription.completeLength();
        this.subscriptions.add(mqttSubscription);
    }

    public MqttSubscription getSub(int i) {
        return this.subscriptions.get(i);
    }

    public ArrayList<Byte> remainingLength() {
        int  x = (2 + this.payloadLength);

        ArrayList<Byte> b = new ArrayList<>();
        while (x > 0) {
            int encoded_byte = (x % 128);
            x /= 128;

            if (x > 0) {
                encoded_byte |= 128;
            }
            b.add((byte) encoded_byte);
        }
        return b;
    }
}
