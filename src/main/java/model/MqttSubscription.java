package model;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MqttSubscription {
    private String topic;
    private int qos;
    final int QOS_LENGTH = 3;

    public MqttSubscription(String topic, int qos) {
        this.qos = qos;
        this.topic = topic;
    }

    public boolean sameTopic(MqttSubscription mqttSubscription) {
        return mqttSubscription.sameTopic(this.topic);

    }

    private boolean sameTopic(String topic) {
        return topic.equals(this.topic);
    }

    public int completeLength() {
        return (this.topic.length() + QOS_LENGTH);
    }

    public ArrayList<Byte> packetIDBytes() {
        ArrayList<Byte> bytes = new ArrayList<>();

        short len = (short) this.topic.length();
        bytes.add((byte)(len >> 8));
        bytes.add((byte)(len & 0x00ff));
        for (byte b : this.topic.getBytes(StandardCharsets.UTF_8)){
            bytes.add(b);
        }

        return bytes;
    }

    public Byte qosBytes() {
        return (byte) this.qos;
    }
}
