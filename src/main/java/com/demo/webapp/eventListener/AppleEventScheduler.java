package com.demo.webapp.eventListener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class AppleEventScheduler extends AbstractScheduler {

    private int yellowAppleQueueSize = 0;
    private int redAppleQueueSize = 0;
    private static final Random random = new Random();

    public void addToAppleQueueSize(String color, int i) {
        if (color.equals("yellow")) {
            yellowAppleQueueSize += i;
        } else {
            redAppleQueueSize += i;
        }
    }

    @Getter
    public static class AppleEvent extends ApplicationEvent {
        private final String color;

        public AppleEvent(Object source, String color) {
            super(source);
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    @Override
    public boolean loopCheckBeforeRequest(String color)  {
        if (color.equals("yellow")) {
            return yellowAppleQueueSize > 0;
        } else {
            return redAppleQueueSize > 0;
        }
    }

    @Override
    public boolean loopCheckAfterRequest(String color)  {
        if (color.equals("yellow")) {
            return yellowAppleQueueSize > 0;
        } else {
            return redAppleQueueSize > 0;
        }
    }

//    @Override
    @EventListener
    public void handleEvent(AppleEvent event) {
        System.out.println("AppleEvent trigger function for color = " + event.getColor());
        while (true) {
            if (!loopCheckBeforeRequest(event.getColor())) {
                System.out.println("[Check Before] No pending request for apple color = " + event.getColor() + ". Break the loop.");
                break;
            }
            // do something
            int pick = random.nextInt(5) + 1;
            int res = 0;
            if (event.getColor().equals("yellow")) {
                yellowAppleQueueSize-=pick;
                res = yellowAppleQueueSize;
            } else {
                redAppleQueueSize-=pick;
                res = redAppleQueueSize;
            }
            System.out.println("Pick " + pick + " apples this time. Left request number = " + res);

            if (!loopCheckAfterRequest(event.getColor())) {
                System.out.println("[Check After] No pending request for apple color = " + event.getColor() + ". Break the loop.");
                break;
            }
        }
    }

}
