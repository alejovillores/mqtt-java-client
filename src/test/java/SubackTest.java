import model.MqttSubscription;
import model.packages.Suback;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;



public class SubackTest {

    @Test
    public void test_01_suback_shares_subs_qos(){
        MqttSubscription mqttSubscription  = new MqttSubscription("topic1",1);

        Suback suback = new Suback(10);
        mqttSubscription.addQosToSuback(suback);

        assertEquals(1,suback.qos());
    }

    @Test
    public void test_02_suback_shares_three_subs_qos(){
        MqttSubscription mqttSubscription  = new MqttSubscription("topic1",1);
        MqttSubscription mqttSubscription2  = new MqttSubscription("topic2",0);
        MqttSubscription mqttSubscription3  = new MqttSubscription("topic3",1);

        Suback suback = new Suback(10);
        mqttSubscription.addQosToSuback(suback);
        mqttSubscription2.addQosToSuback(suback);
        mqttSubscription3.addQosToSuback(suback);

        assertEquals(3,suback.qos());
    }

    @Test
    public void test_03_suback_to_bytes(){
        MqttSubscription mqttSubscription  = new MqttSubscription("topic1",1);
        MqttSubscription mqttSubscription2  = new MqttSubscription("topic1",0);
        MqttSubscription mqttSubscription3  = new MqttSubscription("topic1",1);

        Suback suback = new Suback(0x0a);
        mqttSubscription.addQosToSuback(suback);
        mqttSubscription2.addQosToSuback(suback);
        mqttSubscription3.addQosToSuback(suback);

        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList(0x90, 0x05, 0x00, 0x0a, 0x01, 0x00, 0x01)){
            expectedBytes.add((byte) i);
        }

        assertEquals(expectedBytes,suback.toBytes());
    }
}
