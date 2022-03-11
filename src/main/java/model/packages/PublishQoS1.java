package model.packages;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class PublishQoS1 extends Publish {

    private static final short MSB = 8;

    private final byte msb;
    private final byte lsb;
    private String payload;
    private String topicName;
    private boolean retainFlag;
    private boolean dupFlag;



    public PublishQoS1(byte idMsb, byte idLsb, String topic_name, String payload, boolean dup, boolean retain) {
        super(payload,topic_name,1);
        this.msb = idMsb;
        this.lsb = idLsb;
        this.payload = payload;
        this.topicName = topic_name;
        this.retainFlag = retain;
        this.dupFlag = dup;

    }

    private int makeId() {
        return (this.msb << 0x08) | this.lsb;
    }

    @Override
    public int getId() {
        return this.makeId();
    }

    @Override
    public boolean isRetained() {
        return this.retainFlag;
    }

    @Override
    public boolean isDuplicated() {
        return this.dupFlag;
    }

    @Override
    public Byte flagByte() {
        byte publishByte = 0x30;
        if (this.dupFlag){
            publishByte |= (byte) 0x08;
        }

        publishByte |= (byte) 0x01 << 1;

        if (this.retainFlag){
            publishByte |= (byte) 0x01;
        }
        return publishByte;
    }

    @Override
    public Collection<Byte> variableHeaderBytes() {
        ArrayList<Byte> variableHeaderBytes = new ArrayList<>();

        byte topicNameMsb  = (byte) ((this.topicName.length() & 0x0000ff00) >> 8);
        byte topicNameLsb  = (byte) (this.topicName.length() &  0x000000ff);
        variableHeaderBytes.add(topicNameMsb);
        variableHeaderBytes.add(topicNameLsb);

        for (byte i: this.topicName.getBytes(StandardCharsets.UTF_8)){
            variableHeaderBytes.add(i);
        }
        variableHeaderBytes.add(this.msb);
        variableHeaderBytes.add(this.lsb);
        return variableHeaderBytes;
    }

    @Override
    public Collection<Byte> payloadBytes() {
        ArrayList<Byte> payloadBytes = new ArrayList<>();
        for (byte b : this.payload.getBytes(StandardCharsets.UTF_8)){
            payloadBytes.add(b);
        }
        return payloadBytes;
    }

    @Override
    public ArrayList<Byte> toBytes() {
        ArrayList<Byte> publishBytes = new ArrayList<>();
        publishBytes.add(this.flagByte());
        publishBytes.addAll(this.lengthAlgorithm());
        publishBytes.addAll(this.variableHeaderBytes());
        publishBytes.addAll(this.payloadBytes());

        return publishBytes;
    }
}
