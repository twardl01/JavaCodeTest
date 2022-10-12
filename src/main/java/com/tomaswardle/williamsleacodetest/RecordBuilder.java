package com.tomaswardle.williamsleacodetest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class RecordBuilder {
    private String companyName;
    private int companyNum;
    private String eventType;
    private LocalDate eventDate;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private HashMap<String,String> maps = null;

    public RecordBuilder(HashMap<String,String> map) {
        maps = map;
    }

    //adds/updates data stored in object
    public Record addData(String line) {
        //if company num not present, line is a continuation of another company's name.
        //event type and event date can be null
        Record newR = null;
        String parseCompanyNumber = line.substring(37,45).trim();
        String parseCompanyName = line.substring(0,37).stripTrailing();

        if (parseCompanyNumber.isEmpty()) {
            this.companyName += parseCompanyName;
        } else {
            newR = new Record(companyName,companyNum,eventType,eventDate);
            this.companyName = parseCompanyName;
            this.companyNum = Integer.parseInt(parseCompanyNumber);
            
            String parsedType = line.substring(49,53).trim();
            if (!parsedType.isEmpty()) {
                this.eventType = parsedType;
            } else {
                this.eventType = null;
            }
            
            String parsedDate = line.substring(54,64).trim();
            //handles no eventDate
            if (!parsedDate.isEmpty()) {
                this.eventDate = LocalDate.parse(parsedDate,formatter);
            } else {
                this.eventDate = null;
            }
        }

        return newR;
    }

    public Record fetchRecord() {
        return new Record(companyName,companyNum,eventType,eventDate);
    }
}
