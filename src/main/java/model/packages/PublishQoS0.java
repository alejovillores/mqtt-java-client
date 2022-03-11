package model.packages;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class PublishQoS0 implements Publish,MqttPackage {
        private final int qos;
        private  final String topicName;
        private final String payload;
        private final boolean retainFlag;
        private final boolean dup;
        private int payloadLength;

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
        public Byte flagByte() {
            byte publishByte = 0x30;
            if (this.dup){
                publishByte |= (byte) 0x08;
            }

            publishByte |= (byte) this.qos << 1;

            if (this.retainFlag){
                publishByte |= (byte) 0x01;
            }

            return publishByte;
        }

        @Override
        public Collection<Byte> lengthAlgorithm() {

            int  x = (2 + this.payload.length());

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

        @Override
        public Collection<Byte> variableHeaderBytes() {
            ArrayList<Byte> variableHeaderBytes = new ArrayList<>();
            byte topicNameMsb  = (byte) ((this.topicName.length() & 0x0000ff00) >> 8);
            byte topicNameLsb  = (byte) (this.topicName.length() &  0x000000ff);
            variableHeaderBytes.add(topicNameMsb);
            variableHeaderBytes.add(topicNameLsb);

            for (byte i: this.payload.getBytes(StandardCharsets.UTF_8)){
                variableHeaderBytes.add(i);
            }
            return variableHeaderBytes;
        }

        @Override
        public Collection<Byte> payloadBytes() {
            ArrayList<Byte> payloadBytes = new ArrayList<>();
            byte payloadMsb = (byte) ((this.payload.length() & 0x0000ff00) >> 8);
            byte payloadLsb = (byte) (this.payload.length() &  0x000000ff);
            payloadBytes.add(payloadMsb);
            payloadBytes.add(payloadLsb);
            for (byte b : this.payload.getBytes(StandardCharsets.UTF_8)){
                payloadBytes.add(b);
            }
            return payloadBytes;
        }

        @Override
        public ArrayList<Byte> toBytes() {
            ArrayList<Byte> bytes = new ArrayList<>();
            bytes.add(this.flagByte());
            bytes.addAll(this.lengthAlgorithm());
            bytes.addAll(this.variableHeaderBytes());
            bytes.addAll(this.payloadBytes());


            return bytes;
        }
}
