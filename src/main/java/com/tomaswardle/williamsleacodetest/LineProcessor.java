package com.tomaswardle.williamsleacodetest;

import java.util.Map;

public class LineProcessor {
    
    private boolean eventTypeInput;
    private RecordExtractor recordExtractor;
    private EventTypeExtractor eventExtractor = new EventTypeExtractor();
    private int recordCount;

    public LineProcessor() {
        this.eventTypeInput = true;
        this.recordCount = 0;
    }

    public void processLine(String line) {
        if (line.contains("* DOCUMENTS ISSUED *")) {
            Map<String,String> events = eventExtractor.getMap();
            eventTypeInput = false;
            recordExtractor = new RecordExtractor(events, new ArrayRecordTable());
            return;
        }

        if (eventTypeInput) {
            eventExtractor.processLine(line);
        } else {
            this.recordCount += recordExtractor.processLine(line);
        }
    }

    public int close() {
        this.recordCount += recordExtractor.close();
        return this.recordCount;
    }
}
