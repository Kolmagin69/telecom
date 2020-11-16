package intech.com.subscriber.model;

import java.sql.Timestamp;

public class Message {

    private Integer id;

    private Long msisdn;

    private Action action;

    private Timestamp timestamp;

    public Message(){};

    public Message(Integer id, Long msisdn, Action action, Timestamp timestamp) {
        this.id = id;
        this.msisdn = msisdn;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public Action getAction() {
        return action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msisdn=" + msisdn +
                ", action=" + action +
                ", timestamp=" + timestamp +
                '}';
    }
}
