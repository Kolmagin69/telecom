package intech.com.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer msisdn;

    @Enumerated(EnumType.STRING)
    private Action action;

    private Timestamp timestamp;

    public Message() {
    }

    public Message(final Integer msisdn, final Action action, final Timestamp timestamp) {
        this.msisdn = msisdn;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Action getAction() {
        return action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getMsisdn() {
        return msisdn;
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
