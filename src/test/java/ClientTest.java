import model.Client;
import model.MqttSubscription;
import org.junit.jupiter.api.Test;

import static model.Qos.Qos1;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void test_01_initial_client_has_zero_subscriptions(){
        Client client = new Client("localhost","7878","client_1");
        assertTrue(client.empty());
    }

    @Test
    public void test_02_client_has_new_subscription(){
        Client client = new Client("localhost","7878","client_1");
        MqttSubscription mqttSubscription = new MqttSubscription("wheather/ba",Qos1);
        client.subscribeTo(mqttSubscription);
        assertFalse(client.empty());
    }

    @Test
    public void test_03_client_has_removed_subscription(){
        Client client = new Client("localhost","7878","client_1");
        MqttSubscription mqttSubscription = new MqttSubscription("wheather/ba",Qos1);
        client.subscribeTo(mqttSubscription);
        client.unsubscribeTo(mqttSubscription);
        assertTrue(client.empty());
    }
}
