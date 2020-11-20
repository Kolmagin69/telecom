package intech.com.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer msisdn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Column(nullable = false)
    private Long timestamp;

    public Message() {
    }

    public Message(final Integer msisdn, final Action action, final Long timestamp) {
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

    public Long getTimestamp() {
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
