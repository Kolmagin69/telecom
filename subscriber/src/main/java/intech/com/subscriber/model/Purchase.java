package intech.com.subscriber.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase extends ActionEntity{

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", msisdn=" + msisdn +
                ", timestamp=" + timestamp +
                '}';
    }
}
