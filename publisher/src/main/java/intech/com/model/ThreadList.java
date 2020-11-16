package intech.com.model;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {

    private final List<Thread> threadList;

    private volatile boolean stopTreads = false;

    public ThreadList(final int countTread, final ThreadFunction function) {
        this.threadList = createTreadList(countTread, function);
    }

    private List<Thread> createTreadList(final int countThread, final ThreadFunction function) {
        final List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < countThread; i++) {
            Thread thread = new Thread(getRunnable(function));
            threadList.add(thread);
        }
        return threadList;
    }

    private Runnable getRunnable(final ThreadFunction function) {
        return  () -> {
            do {
                if (!stopTreads)
                    function.run();
                else
                    return;
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            } while (!stopTreads);
        };
    }

    public List<Thread> getThreadList() {
        return threadList;
    }

    public boolean isStopTreads() {
        return stopTreads;
    }

    public void setStopTreads(boolean stopTreads) {
        this.stopTreads = stopTreads;
    }

}
