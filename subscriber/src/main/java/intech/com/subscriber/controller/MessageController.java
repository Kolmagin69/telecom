package intech.com.subscriber.controller;

import intech.com.subscriber.model.*;
import intech.com.subscriber.repository.PurchaseRepository;
import intech.com.subscriber.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public ActionEntity checkActionAndSave(final Message mes) {
        if (mes == null || mes.getAction() == null)
            throw new IllegalArgumentException(
                    "Getting message not present or field action into message is null");
        logger.info("Get " + mes.toString());
        return mes.getAction() == Action.PURCHASE ?
                purchaseRepository.save(getActionFromMessage(mes, new Purchase())) :
                subscriptionRepository.save(getActionFromMessage(mes, new Subscription()));
    }

    private <T extends ActionEntity> T getActionFromMessage(final Message message, final T action) {
        action.setMsisdn(message.getMsisdn());
        action.setTimestamp(message.getTimestamp());
        return action;
    }


}
