package intech.com.subscriber.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "subscriptions")
public class Subscription extends ActionEntity {

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", msisdn=" + msisdn +
                ", timestamp=" + timestamp +
                '}';
    }
}
