package com.tomaswardle.williamsleacodetest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.context.annotation.RequestScope;
import com.tomaswardle.williamsleacodetest.record.model.Record;

@RequestScope
public class RecordBuilder {
    private String companyName;
    private String companyNum;
    private String eventType;
    private LocalDate eventDate;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Map<String,String> maps = null;

    //constructor
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

        //if company num is null, continuation of last line
        if (parseCompanyNumber == null || parseCompanyNumber.isEmpty()) {
            //ignores empty lines
            if (parseCompanyName != null) {
                this.companyName += parseCompanyName;
            }
        } else {

            String parsedType = StringMethods.getSubstring(line,49,53);
            String parsedDate = StringMethods.getSubstring(line,54,64);

            if (!invalidCompanyName(companyName)) {
                newR = new Record(companyName,companyNum,eventType,eventDate);
            }

            if (invalidCompanyNum(parseCompanyNumber) || invalidEventType(parsedType) || invalidEventDate(parsedDate)) {
                return null;
            }

            this.companyName = parseCompanyName;
            this.companyNum = parseCompanyNumber;
            this.eventType = maps.get(parsedType);
            
            if (parsedDate == null) {
                this.eventDate = null;
            } else {
                if (parsedDate.isEmpty()) {
                    this.eventDate = null;
                } else {
                    this.eventDate = LocalDate.parse(parsedDate,formatter);
                }
            }
            
        }

        return newR;
    }

    //detects invalid names
    private boolean invalidCompanyName(String name) {
        return name == null || name.isEmpty();
    }

    //as the num is a string, detects cases where the "num" string contains letters/symbols.
    private boolean invalidCompanyNum(String num) {
        if (num == null) {
            return true;
        }
        
        Pattern nonNum = Pattern.compile("[^\\d]");
        Matcher nonNumMatcher = nonNum.matcher(num);

        return nonNumMatcher.find();
    }

    //detects cases where a non-existent event type is specified
    private boolean invalidEventType(String event) {
        if (event == null) {
            return false;
        }

        return !event.isEmpty() && maps.get(event) == null;
    }

    //detects dates that are specified but imparsible
    private boolean invalidEventDate(String date) {
        if (date == null) {
            return false;
        }

        if (date.isEmpty()) {
            this.eventDate = null;
            return false;
        }
        
        try {
            this.eventDate = LocalDate.parse(date,formatter);
        } catch(DateTimeParseException pe) {
            return true;
        }

        return false;
    }

    //fetches record in object's properties
    public Record fetchRecord() {
        return new Record(companyName,companyNum,eventType,eventDate);
    }
}
