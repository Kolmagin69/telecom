package intech.com.model;

import java.sql.Timestamp;
import java.util.Random;

public class Message {

    private int id;

    private long msisdn;

    private Action action;

    private Timestamp timestamp;

    public Message(int id, Timestamp timestamp) {
        this.id = id;
        this.msisdn = new Random().nextInt();
        this.action = Action.getRandomAction();
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(int msisdn) {
        this.msisdn = msisdn;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
