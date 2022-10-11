package com.tomaswardle.williamsleacodetest;

public class LineProcessor {
    
    private boolean eventTypeInput;
    private RecordExtractor recordExtractor;
    private EventTypeExtractor eventExtractor;

    public LineProcessor() {
        this.eventTypeInput = true;
    }

    public void processLine(String line) {
        if (line.contains("* DOCUMENTS ISSUED *")) {
            eventTypeInput = false;
            recordExtractor = new RecordExtractor(eventExtractor.getDict());
            return;
        }

        if (eventTypeInput) {
            eventExtractor.processLine(line);
        } else {
            recordExtractor.processLine(line);
        }
    }
}
