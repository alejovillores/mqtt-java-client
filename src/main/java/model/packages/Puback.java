package model.packages;

import java.util.ArrayList;

public class Puback implements MqttPackage {
    private int id;

    public Puback(int id) {
        this.id = id;
    }

    @Override
    public ArrayList<Byte> toBytes() {
        byte idMsb  = (byte) ((this.id & 0x0000ff00) >> 8);
        byte idLsb  = (byte) (this.id &  0x000000ff);

        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte)0x40);
        bytes.add((byte)0x02);
        bytes.add(idMsb);
        bytes.add(idLsb);

        return bytes;
    }

    public boolean fromPackage(int id) {
        return (this.id == id);
    }
}
