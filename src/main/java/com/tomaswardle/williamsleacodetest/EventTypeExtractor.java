package com.tomaswardle.williamsleacodetest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class EventTypeExtractor {
    
    private HashMap<String, String> eventDict = new HashMap<String,String>();
    private String eventCode = "";
    private String eventDesc = "";

    //if new data includes code, add currently stored data to map + set eventCode and eventDesc
    //if no code, assume line writes over
    public void processLine(String line) {
        String parsedDesc = line.substring(27).stripTrailing();
        if(line.charAt(27) == ' ') {
            return;
        }

        //if the line has an eventType, create new definition
        if (line.charAt(20) == '(') {
            if (!eventCode.isEmpty() && !eventDesc.isEmpty()) {
                eventDict.put(eventCode,eventDesc);
            }
            eventCode = line.substring(20, 27).trim();
            eventDesc = parsedDesc;
        } else {
            eventDesc += " " + parsedDesc;
        }
    }
    
    public Map<String, String> getMap() {
        if (!eventCode.isEmpty() && !eventDesc.isEmpty()) {
            eventDict.put(eventCode,eventDesc);
        }
        return eventDict;
    }
}
