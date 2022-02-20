package model;

public class MqttSubscription {
    private String topic;
    private Qos qos;

    public MqttSubscription(String topic, Qos qos) {
        this.qos = qos;
        this.topic = topic;
    }

    public boolean sameTopic(MqttSubscription mqttSubscription) {
        return mqttSubscription.sameTopic(this.topic);

    }

    private boolean sameTopic(String topic) {
        return topic.equals(this.topic);
    }
}
