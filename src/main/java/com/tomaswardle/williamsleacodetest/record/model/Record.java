package com.tomaswardle.williamsleacodetest.record.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table
public class Record {

    @Id
    @GeneratedValue
    @Column
    public Long id;

    @Column
    public String companyName;

    @Column
    public String companyNum;

    @Column
    public String eventType;

    @Column
    public LocalDate eventDate;

    public Record(String name, String num, String event, LocalDate date) {
        this.companyName = name;
        this.companyNum = num;
        this.eventType = event;
        this.eventDate = date;
    }

    public boolean isValid() {
        return !(this.companyName == null || this.companyNum == null);
    }
}
