package model.packages;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class PublishQoS0 extends Publish implements MqttPackage {
        private  final String topicName;
        private final String payload;
        private final boolean retainFlag;
        private final boolean dup;

        private final int MSB = 0x0000ff00;
        private final int LSB = 0x000000ff;

        public PublishQoS0(String topicName, String payload, boolean retainFlag, boolean dup) {
            super(payload,topicName,0);
            this.topicName = topicName;
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

            if (this.retainFlag){
                publishByte |= (byte) 0x01;
            }

            return publishByte;
        }

        @Override
        public Collection<Byte> variableHeaderBytes() {
            ArrayList<Byte> variableHeaderBytes = new ArrayList<>();
            byte topicNameMsb  = (byte) ((this.topicName.length() & MSB) >> 8);
            byte topicNameLsb  = (byte) (this.topicName.length() &  LSB);
            variableHeaderBytes.add(topicNameMsb);
            variableHeaderBytes.add(topicNameLsb);

            for (byte i: this.topicName.getBytes(StandardCharsets.UTF_8)){
                variableHeaderBytes.add(i);
            }
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
            ArrayList<Byte> bytes = new ArrayList<>();
            bytes.add(this.flagByte());
            bytes.addAll(this.lengthAlgorithm());
            bytes.addAll(this.variableHeaderBytes());
            bytes.addAll(this.payloadBytes());


            return bytes;
        }
}
