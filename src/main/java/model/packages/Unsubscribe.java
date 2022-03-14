package model.packages;

import model.MqttSubscription;

import java.util.ArrayList;

public class Unsubscribe implements MqttPackage {

    private int id;
    private ArrayList<MqttSubscription> subscriptions;


    public Unsubscribe(int id) {
        this.id = id;
        this.subscriptions = new ArrayList<>();
    }

    public void removeSubscription(MqttSubscription subscription) {
        if (!this.subscriptions.isEmpty()){
            this.subscriptions.remove(subscription);
        }
    }

    public boolean isEmpty() {
        return this.subscriptions.isEmpty();
    }

    @Override
    public ArrayList<Byte> toBytes() {
        ArrayList<Byte> bytes  = new ArrayList<>();
        bytes.add((byte) 0xa2);
        bytes.addAll(this.lengthAlgorithms());
        bytes.addAll(this.idBytes());

        for (MqttSubscription subscription : this.subscriptions){
            bytes.addAll(subscription.topicBytes());
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

    public void addSubscription(MqttSubscription subscription) {
        this.subscriptions.add(subscription);
    }

    public int subs() {
        return this.subscriptions.size();
    }

    private ArrayList<Byte> lengthAlgorithms(){
        int  x = (2 + this.payload_length());

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

    private int payload_length() {
        int len = 0;
        for (MqttSubscription s: this.subscriptions){
            len += s.completeLength();
        }

        return len;
    }
}
