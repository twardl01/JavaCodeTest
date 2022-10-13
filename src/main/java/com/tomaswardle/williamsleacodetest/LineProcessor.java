package com.tomaswardle.williamsleacodetest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LineProcessor {

    private boolean eventTypeInput;

    @Autowired
    private RecordExtractor recordExtractor;

    @Autowired
    private EventTypeExtractor eventExtractor;

    private int recordCount;

    //constructor
    public LineProcessor() {
        this.eventTypeInput = true;
        this.recordCount = 0;
    }

    //processes lines coming in
    //events are written in until the DOCUMENTS ISSUED string is detected.
    //after which, records are added to the database.
    public void processLine(String line) {
        if (line.contains("* DOCUMENTS ISSUED *")) {
            Map<String,String> events = eventExtractor.getMap();
            eventTypeInput = false;
            recordExtractor.start(events);
            return;
        }

        if (eventTypeInput) {
            eventExtractor.processLine(line);
        } else {
            this.recordCount += recordExtractor.processLine(line);
        }
    }

    //"closes" the lineprocessor, returns the number of records written to the database
    public int close() {
        this.recordCount += recordExtractor.close();
        return this.recordCount;
    }
}
