package com.tomaswardle.williamsleacodetest;

import java.util.ArrayList;
import java.util.List;

//mock implementation of database, used to test.
public class ArrayRecordTable implements IRecordTable {

    private ArrayList<Record> records = new ArrayList<>();

    //simulates the addition of a record to a database
    @Override
    public void add(Record r) {
        System.out.println(r.companyName + " " + r.companyNum + " " + r.eventType + " " + r.eventDate);
        records.add(r);
    }

    //fetches the records inside the database
    public List<Record> getRecords() {
        return records;
    }
    

}
