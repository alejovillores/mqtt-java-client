import model.MqttSubscription;
import model.packages.Unsubscribe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UnsubscribeTest {

    @Test
    public void test_01_unsubscribe_has_one_or_more_topics(){
        Unsubscribe unsubscribe = new Unsubscribe(1);
        MqttSubscription subscription = new MqttSubscription("boca",0);
        unsubscribe.addSubscription(subscription);

        assertFalse(unsubscribe.isEmpty());
    }

    @Test
    public void test_02_unsubscription_starts_empty(){
        Unsubscribe unsubscribe = new Unsubscribe(1);

        assertTrue(unsubscribe.isEmpty());
    }

    @Test
    public void test_03_unsubscribe_has_two_topics(){
        Unsubscribe unsubscribe = new Unsubscribe(1);
        MqttSubscription subscription = new MqttSubscription("boca",0);
        MqttSubscription subscription2 = new MqttSubscription("river",0);
        unsubscribe.addSubscription(subscription);
        unsubscribe.addSubscription(subscription2);

        assertEquals(2,unsubscribe.subs());
    }

    @Test
    public void test_04_unsubscribe_with_one_topic_to_bytes(){
        Unsubscribe unsubscribe = new Unsubscribe(0x0a);
        MqttSubscription subscription = new MqttSubscription("a/b",0);
        unsubscribe.addSubscription(subscription);

        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList(0xa2, 0x07, 0x00, 0x0a, 0x00, 0x03, 0x61, 0x2f, 0x62)){
            expectedBytes.add((byte) i);
        }

        assertEquals(expectedBytes,unsubscribe.toBytes());
    }

    @Test
    public void test_05_unsubscribe_with_twp_topic_to_bytes(){
        Unsubscribe unsubscribe = new Unsubscribe(0x0a);
        MqttSubscription subscription = new MqttSubscription("a/b",0);
        MqttSubscription subscription2 = new MqttSubscription("c/d",0);
        unsubscribe.addSubscription(subscription);
        unsubscribe.addSubscription(subscription2);

        ArrayList<Byte> expectedBytes = new ArrayList<>();
        for (int i : Arrays.asList(0xa2, 0x0c, 0x00, 0x0a, 0x00, 0x03, 0x61, 0x2f, 0x62, 0x00, 0x03, 0x63, 0x2f, 0x64)){
            expectedBytes.add((byte) i);
        }

        assertEquals(expectedBytes,unsubscribe.toBytes());
    }
}
