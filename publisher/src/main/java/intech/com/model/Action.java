package intech.com.model;

import java.util.Random;

public enum Action {

    PURCHASE,SUBSCRIPTION;

    public static Action fromString(final String action) {
        return action.equals(PURCHASE.toString()) ? PURCHASE : SUBSCRIPTION;
    }

    public static Action getRandomAction() {
        final int i = new Random().nextInt();
        return i % 2 == 0 ? PURCHASE : SUBSCRIPTION;
    }
}
