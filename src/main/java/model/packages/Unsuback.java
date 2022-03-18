package model.packages;

import java.util.ArrayList;

public class Unsuback implements MqttPackage {
    private int id;

    public Unsuback(int id) {
        this.id = id;
    }

    @Override
    public ArrayList<Byte> toBytes() {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte)0xB0);
        bytes.add((byte)0x02);
        bytes.add((byte)(this.id >> 8));
        bytes.add((byte)(this.id & 0x00ff));

        return bytes;
    }
}
