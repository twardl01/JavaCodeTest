package com.tomaswardle.williamsleacodetest;

import java.util.HashMap;

public class EventTypeExtractor {
    
    private HashMap<String, String> eventDict;
    private String eventCode;
    private String eventDesc;

    //if new data includes code, add currently stored data to hashmap + set eventCode and eventDesc
    //if no code, assume line writes over
    public void processLine(String line) {
        if (line.charAt(20) == '(') {
            if (!eventCode.isEmpty() && !eventDesc.isEmpty()) {
                eventDict.put(line,line);
            }
            eventCode = line.substring(20, 25);
            eventDesc = line.substring(28).stripTrailing();
        } else {
            eventDesc += line.substring(28).stripTrailing();
        }
    }

    public HashMap<String, String> getDict() {
        return eventDict;
    }
}
