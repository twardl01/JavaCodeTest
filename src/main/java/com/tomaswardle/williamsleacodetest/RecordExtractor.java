package com.tomaswardle.williamsleacodetest;

import java.util.Map;

import com.tomaswardle.williamsleacodetest.record.model.Record;
import com.tomaswardle.williamsleacodetest.record.repository.IRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RecordExtractor  {

    IRecordRepository recordRepository;

    private RecordBuilder builder1;
    private RecordBuilder builder2;

    
    //constructor
    public RecordExtractor(IRecordRepository repo) {
        this.recordRepository = repo;
    }  

    //
    public void start(Map<String, String> events) {
        this.builder1 = new RecordBuilder(events);
        this.builder2 = new RecordBuilder(events);
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

        this.recordRepository.save(r);
        return 1;
    }

    //closes recordextractor, enters records in builder's properties and returns the number of records written.
    public int close() {
        int count = processRecord(builder1.fetchRecord());
        count += processRecord(builder2.fetchRecord());
        return count;
    }
}
