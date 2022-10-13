package com.tomaswardle.williamsleacodetest;

import java.util.Map;

public class RecordExtractor  {

    private RecordBuilder builder1;
    private RecordBuilder builder2;
    private IRecordTable table;
    
    //constructor
    public RecordExtractor(Map<String, String> events, IRecordTable table) {
        this.builder1 = new RecordBuilder(events);
        this.builder2 = new RecordBuilder(events);
        this.table = table;
    }

    //returns num of finished records
    public int processLine(String line) {
        //if company num present on new line, add old record to arraylist and intialise next obj 
        int count = processRecord(builder1.addData(StringMethods.getSubstring(line,10,74)));
        count += processRecord(builder2.addData(StringMethods.getSubstring(line,76)));

        return count;
    }

    //adds record to database, returns 1 if success, 0 if not.
    private int processRecord(Record r) {
        if (r == null) {
            return 0;  
        }

        this.table.add(r);
        return 1;
    }

    //closes recordextractor, enters records in builder's properties and returns the number of records written.
    public int close() {
        int count = processRecord(builder1.fetchRecord());
        count += processRecord(builder2.fetchRecord());
        return count;
    }
}
