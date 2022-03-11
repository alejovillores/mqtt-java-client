package model.packages;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Publish {
    private String payload;
    private String topicName;
    private int qos;

    public Publish(String payload, String topicName,int qos) {
        this.payload = payload;
        this.topicName = topicName;
        this.qos = qos;
    }

    public int getId(){
        return -1;
    };

    public boolean isRetained(){
        return false;
    };

    public boolean isDuplicated(){
        return false;
    };

    public abstract Byte flagByte();

    public Collection<Byte> lengthAlgorithm(){
        int  x = (2 + this.payload.length() + this.topicName.length());
        if (qos > 0) x += 2;

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
    };

    public abstract Collection<Byte> variableHeaderBytes();

    public abstract Collection<Byte> payloadBytes();

    public abstract ArrayList<Byte> toBytes();
}
