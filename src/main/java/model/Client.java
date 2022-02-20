package model;



import model.packages.ConnectPackage;
import model.packages.MqttPackage;

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

    private int qos;

    private LinkedList<MqttSubscription> subscriptions;

    // Methods
    public Client(String ip, String port, String client_id){
        this.ip = ip;
        this.port = port;
        this.client_id = client_id;
        this.subscriptions = new LinkedList<MqttSubscription>();
        this.qos = 0;

    }

    public void setQos(int qos) {
        this.qos = qos;
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

    public void sendDisconnect() {
        // simulates disconnect
        if (this.clean_session){
            // save subscriptions.
            int a = 2+2;
            this.subscriptions.clear();
        }

        // save data
    }

    public int connect() {
        MqttPackage connectPackage = this.makeConnectPackage();
        /* do socket connection */
        return 0;
    }

    private ConnectPackage makeConnectPackage() {
        ConnectPackage connectPackage = new ConnectPackage(this.client_id,this.clean_session,this.will_retain);
        if ((this.username != null) && (this.password != null)){
            connectPackage.setAuth(this.username,this.password);
        }

        if ((this.will_message != null) && (this.will_topic != null)){
            connectPackage.setWillData(this.will_topic,this.will_message);
        }
        return connectPackage;
    }
}
