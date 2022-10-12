package com.tomaswardle.williamsleacodetest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class RecordBuilder {
    private String companyName;
    private String companyNum;
    private String eventType;
    private LocalDate eventDate;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Map<String,String> maps = null;

    public RecordBuilder(Map<String, String> events) {
        maps = events;
    }

    //adds/updates data stored in object
    public Record addData(String line) {
        //if company num not present, line is a continuation of another company's name.
        //event type and event date can be null
        Record newR = null;
        String parseCompanyName = StringMethods.getSubstring(line,0,37);
        String parseCompanyNumber = StringMethods.getSubstring(line,37,45);

        if (parseCompanyNumber == null || parseCompanyNumber.isEmpty()) {
            this.companyName += parseCompanyName;
        } else {
            if (companyName != null) {
                newR = new Record(companyName,companyNum,eventType,eventDate);
            }

            this.companyName = parseCompanyName;
            this.companyNum = parseCompanyNumber;
            
            String parsedType = StringMethods.getSubstring(line,49,53);
            this.eventType = maps.get(parsedType);
            
            //handles no eventDate
            String parsedDate = StringMethods.getSubstring(line,54,64);
            if (parsedDate == null || parsedDate.isEmpty()) {
                this.eventDate = null;
            } else {
                this.eventDate = LocalDate.parse(parsedDate,formatter);
            }
        }

        return newR;
    }

    public Record fetchRecord() {
        return new Record(companyName,companyNum,eventType,eventDate);
    }
}
