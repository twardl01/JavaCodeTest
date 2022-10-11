package com.tomaswardle.williamsleacodetest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordExtractor {
    
    private HashMap<String, String> eventTypes;
    private ArrayList<Record> records;
    private Record object1;
    private Record object2;

    public Record[] getRecords() {
        return (Record[]) records.toArray();
    }

    public void setEventTypes(HashMap<String, String> newDict) {
        eventTypes = newDict;
    }
    
    public RecordExtractor(HashMap<String, String> events) {
        this.eventTypes = events;
    }

    public void processLine(String line) {
        if (line.substring(47,55).trim().equals("")) {
            object2.companyName += line.substring(10,47).trim();
        } else {
            object1.companyName = line.substring(10,47);
            object1.companyNum = Integer.parseInt(line.substring(47,55));
            object1.eventType = line.substring(59,63);
            object1.eventDate = LocalDate.parse(line.substring(64,74));
        }

        if (line.substring(47,55).trim().equals("")) {
            object2.companyName += line.substring(10,47);
        } else {
            object2.companyName = line.substring(10,47);
            object2.companyNum = Integer.parseInt(line.substring(47,55));
            object2.eventType = line.substring(0,1);
            object2.eventDate = LocalDate.parse(line.substring(64,74));
        }
    }
}
