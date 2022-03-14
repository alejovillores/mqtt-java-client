package model.packages;

import model.packages.MqttPackage;

import java.util.ArrayList;
import java.util.Collection;

public class Suback implements MqttPackage {
    private int id;
    private ArrayList<Integer> qos;

    public Suback(int id) {
        this.id = id;
        this.qos = new ArrayList<>();
    }

    @Override
    public ArrayList<Byte> toBytes() {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte)0x90);
        bytes.addAll(this.lengthAlgorithms());
        bytes.addAll(this.idBytes());
        for (int qos: this.qos) {
            bytes.add((byte) qos);
        }

        return bytes;

    }

    private ArrayList<Byte> idBytes() {
        byte idMsb  = (byte) ((this.id & 0x0000ff00) >> 8);
        byte idLsb  = (byte) (this.id &  0x000000ff);
        ArrayList<Byte> idArrayList = new ArrayList<>();
        idArrayList.add(idMsb);
        idArrayList.add(idLsb);

        return idArrayList;
    }

    private ArrayList<Byte> lengthAlgorithms(){
        int  x = (2 + this.qos.size());

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

    public int qos() {
        return this.qos.size();
    }

    public void addQos(int qos) {
        this.qos.add(qos);
    }
}
