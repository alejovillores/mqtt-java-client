import model.MqttSubscription;
import model.packages.SubscribePackage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SubscribeTest {
    @Test
    public void test_01_subscribe_topic_qos1(){
        SubscribePackage subscribePackage = new SubscribePackage((short) 0);
        MqttSubscription mqttSubscription = new MqttSubscription("feat/alejo",1);
        subscribePackage.addSubscription(mqttSubscription);

        assertEquals(mqttSubscription,subscribePackage.getSub(0));
    }


    @Test
    public void test_02_subscribe_one_topics_to_bytes(){
        ArrayList<Byte> subsBytes = new ArrayList<>();
        for (int l_i: Arrays.asList(0x82, 0x0F, 0x00, 0x01, 0x00, 0x0A, 0x4f, 0x50, 0x45, 0x4e, 0x4c, 0x41, 0x42, 0x50,
                0x52, 0x4f, 0x00) ){
            subsBytes.add((byte) l_i);
        }
        SubscribePackage subscribePackage = new SubscribePackage((short) 0x01);
        MqttSubscription mqttSubscription1 = new MqttSubscription("OPENLABPRO",0);
        subscribePackage.addSubscription(mqttSubscription1);
        ArrayList<Byte> bytes = subscribePackage.toBytes();

        assertEquals(subsBytes,bytes);
    }


    @Test
    public void test_03_subscribe_two_topics_to_bytes(){
        ArrayList<Byte> subsBytes = new ArrayList<>();
        for (int l_i: Arrays.asList(0x82, 0x0e, 0x00, 0x0a, 0x00, 0x03, 0x61, 0x2f, 0x62, 0x01, 0x00, 0x03, 0x63, 0x2f,
                0x64, 0x02) ){
            subsBytes.add((byte) l_i);
        }
        SubscribePackage subscribePackage = new SubscribePackage((short) 0x0a);
        MqttSubscription mqttSubscription1 = new MqttSubscription("a/b",1);
        MqttSubscription mqttSubscription2 = new MqttSubscription("c/d",2);
        subscribePackage.addSubscription(mqttSubscription1);
        subscribePackage.addSubscription(mqttSubscription2);

        ArrayList<Byte> bytes = subscribePackage.toBytes();

        assertEquals(subsBytes,bytes);
    }
}
