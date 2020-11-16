package intech.com.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Integer id;

    @Column(name = "msisdn")
    private final Integer msisdn;

    @Column(name = "action")
    private final Action action;

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timestamp;

    public Message(Integer id, Integer msisdn, Action action, Timestamp timestamp) {
        this.id = id;
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
}
