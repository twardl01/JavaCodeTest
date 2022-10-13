package com.tomaswardle.williamsleacodetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tomaswardle.williamsleacodetest.record.model.Record;
import com.tomaswardle.williamsleacodetest.record.repository.IRecordRepository;

//mock implementation of database, used to test.
public class TestRecordRepo implements IRecordRepository {

    private ArrayList<Record> records = new ArrayList<>();

    @Override
    public <S extends Record> S save(S entity) {
        records.add(entity);
        return entity;
    }

    @Override
    public <S extends Record> Iterable<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Record> findById(Integer id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterable<Record> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Record> findAllById(Iterable<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count() {
        return records.size();
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Record entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll(Iterable<? extends Record> entities) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

    public List<Record> getRecords() {
        return null;
    }

}
