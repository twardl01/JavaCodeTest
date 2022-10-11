package com.tomaswardle.williamsleacodetest;

import java.time.LocalDate;

public class Record {
    public String companyName;
    public int companyNum;
    public String eventType;
    public LocalDate eventDate;

    public Record(String name, int num, String event, LocalDate date) {
        this.companyName = name;
        this.companyNum = num;
        this.eventType = event;
        this.eventDate = date;
    }
}
