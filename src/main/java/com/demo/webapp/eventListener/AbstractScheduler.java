package com.demo.webapp.eventListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

public class AbstractScheduler {
    public boolean loopCheckBeforeRequest(String params){return true;}

    public boolean loopCheckAfterRequest(String params){return true;}
//
//    @EventListener
//    public void handleEvent(ApplicationEvent event) {
//        while (true) {
//            if (!loopCheckBeforeRequest()) {
//                break;
//            }
//            // do something
//            if (!loopCheckAfterRequest()) {
//                break;
//            }
//        }
//    }

}
