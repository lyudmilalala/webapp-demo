package com.demo.webapp.eventListener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CakeEventScheduler extends AbstractScheduler {

    private int cakeQueueSize = 0;
    private static final Random random = new Random();

    public void addToCakeQueueSize(int i) {
        cakeQueueSize += i;
    }

    @Getter
    public static class CakeEvent extends ApplicationEvent {

        public CakeEvent(Object source) {
            super(source);
        }
    }

    @Override
    public boolean loopCheckBeforeRequest(String color)  {
        return cakeQueueSize > 0;
    }

    @Override
    public boolean loopCheckAfterRequest(String color)  {
        return cakeQueueSize > 0;
    }

//    @Override
    @EventListener
    public void handleEvent(CakeEvent event) {
        System.out.println("CakeEvent trigger function");
        while (true) {
            if (!loopCheckBeforeRequest(null)) {
                System.out.println("[Check Before] No pending request for cake. Break the loop.");
                break;
            }
            // do something
            int pick = random.nextInt(5) + 1;
            cakeQueueSize-=pick;
            System.out.println("Make " + pick + " cakes this time. Left request number = " + cakeQueueSize);

            if (!loopCheckAfterRequest(null)) {
                System.out.println("[Check After] No pending request for cake. Break the loop.");
                break;
            }
        }
    }

}
