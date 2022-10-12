package com.tomaswardle.williamsleacodetest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordExtractor {
    
    private HashMap<String, String> eventTypes;
    private ArrayList<RecordBuilder> records;
    private RecordBuilder builder1;
    private RecordBuilder builder2;

    public RecordBuilder[] getRecords() {
        return (RecordBuilder[]) records.toArray();
    }

    public void setEventTypes(HashMap<String, String> newDict) {
        eventTypes = newDict;
    }
    
    public RecordExtractor(HashMap<String, String> events) {
        this.eventTypes = events;
    }

    public void processLine(String line) {
        //if company num present on new line, add old record to arraylist and intialise next obj 
        processRecord(builder1.addData(line.substring(10,74)));
        processRecord(builder2.addData(line.substring(76,140)));
    }

    private boolean processRecord(Record r) {
        //
        if (r == null) {
            return false;
        }

        //TODO store new record

        return true;
    }
}
