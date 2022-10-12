package com.tomaswardle.williamsleacodetest;

import java.util.ArrayList;

public class ArrayRecordTable implements IRecordTable {

    private ArrayList<Record> records = new ArrayList<Record>();

    @Override
    public void add(Record r) {
        records.add(r);
    }

    public ArrayList<Record> getRecords() {
        return records;
    }
    

}
