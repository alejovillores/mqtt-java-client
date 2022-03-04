package model.packages;

import java.util.ArrayList;

public class PublishQoS0 implements Publish,MqttPackage {
    private final int qos;
    private  final String topicName;
    private final String payload;
    private final boolean retainFlag;
    private final boolean dup;

    public PublishQoS0(String topicName, int qos, String payload, boolean retainFlag, boolean dup) {
        this.topicName = topicName;
        this.qos = qos;
        this.payload = payload;
        this.retainFlag = retainFlag;
        this.dup = dup;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public boolean isRetained() {
        return this.retainFlag;
    }

    @Override
    public boolean isDuplicated() {
        return this.dup;
    }

    @Override
    public ArrayList<Byte> toBytes() {
        return null;
    }
}
