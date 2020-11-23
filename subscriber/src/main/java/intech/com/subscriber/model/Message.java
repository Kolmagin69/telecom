package intech.com.subscriber.model;

public class Message {

    private Integer id;

    private Integer msisdn;

    private Action action;

    private Long timestamp;

    public Message(){};

    public Message(Integer id, Integer msisdn, Action action, Long timestamp) {
        this.id = id;
        this.msisdn = msisdn;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMsisdn() {
        return msisdn;
    }

    public Action getAction() {
        return action;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMsisdn(Integer msisdn) {
        this.msisdn = msisdn;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setTimestamp(Long timestamp) {
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
