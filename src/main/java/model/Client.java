package model;



import java.util.LinkedList;

public class Client {

    // Atributes
    private String ip;
    private String port;
    private String username;
    private String password;
    private String client_id;
    private String will_topic;
    private String will_message;
    private boolean clean_session;
    private boolean will_retain;

    private LinkedList<MqttSubscription> subscriptions;
    // Methods

    public Client(String ip, String port, String client_id){
        this.ip = ip;
        this.port = port;
        this.client_id = client_id;
        this.subscriptions = new LinkedList<MqttSubscription>();

    }

    public void setWill_topic(String will_topic) {
        this.will_topic = will_topic;
    }

    public void setWill_message(String will_message) {
        this.will_message = will_message;
    }

    public void setClean_session(boolean clean_session) {
        this.clean_session = clean_session;
    }

    public void setWill_retain(boolean will_retain) {
        this.will_retain = will_retain;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean empty() {
        return !(this.subscriptions.size() > 0);
    }

    public void subscribeTo(MqttSubscription mqttSubscription) {
        this.subscriptions.add(mqttSubscription);
    }

    public void unsubscribeTo(MqttSubscription mqttSubscription) {
        this.subscriptions.removeIf(s -> s.sameTopic(mqttSubscription));
    }
}
