import model.Client;
import model.MqttSubscription;
import org.junit.jupiter.api.Test;

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
        MqttSubscription mqttSubscription = new MqttSubscription("wheather/ba",1);
        client.subscribeTo(mqttSubscription);
        assertFalse(client.empty());
    }

    @Test
    public void test_03_client_has_removed_subscription(){
        Client client = new Client("localhost","7878","client_1");
        MqttSubscription mqttSubscription = new MqttSubscription("wheather/ba",1);
        client.subscribeTo(mqttSubscription);
        client.unsubscribeTo(mqttSubscription);
        assertTrue(client.empty());
    }

    @Test
    public void test_04_removes_all_subs_after_disconnect(){
        Client client = new Client("localhost","7878","client_1");
        client.setClean_session(true);
        client.setWill_retain(false);
        client.setWill_topic("error-topic");
        client.setWill_message("An error occurred");

        MqttSubscription mqttSubscription = new MqttSubscription("wheather/ba/junin",1);
        MqttSubscription mqttSubscription_2 = new MqttSubscription("wheather/ba/chacabuco",0);
        MqttSubscription mqttSubscription_3 = new MqttSubscription("wheather/ba/+/lluvia",0);

        client.sendDisconnect();
        assertTrue(client.empty());
    }

    @Test
    public void test_05_client_make_connection_returns_0_if_succes(){
        Client client = new Client("localhost","7878","client_1");
        client.setClean_session(true);
        client.setWill_retain(false);
        client.setWill_topic("error-topic");
        client.setWill_message("An error occurred");
        client.setUsername("alejo");
        client.setPassword("1234villores");

        int r  = client.connect();
        assertEquals(r,0);
    }
}
