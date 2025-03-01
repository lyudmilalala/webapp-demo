package com.demo.webapp.eventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private AppleEventScheduler appleEventScheduler;
    @Autowired
    private CakeEventScheduler cakeEventScheduler;

    @RequestMapping(value="/pickApple", method= RequestMethod.POST)
    public ResponseEntity<String> pickApple(@RequestParam("color") String color, @RequestParam("size") int size) {
        appleEventScheduler.addToAppleQueueSize(color, size);
        applicationEventPublisher.publishEvent(new AppleEventScheduler.AppleEvent(this, color));
        return ResponseEntity.ok(new String("Add " + size + " " + color + " apple request to queue"));
    }

    @RequestMapping(value="/makeCake", method= RequestMethod.POST)
    public ResponseEntity<String> makeCake(@RequestParam("size") int size) {
        cakeEventScheduler.addToCakeQueueSize(size);
        applicationEventPublisher.publishEvent(new CakeEventScheduler.CakeEvent(this));
        return ResponseEntity.ok(new String("Add "+ size + " cake request to queue"));
    }

}
