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
        // not needed in implementation
        return null;
    }

    @Override
    public Optional<Record> findById(Integer id) {
        // not needed in implementation
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer id) {
        // not needed in implementation
        return false;
    }

    @Override
    public Iterable<Record> findAll() {
        return records;
    }

    @Override
    public Iterable<Record> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public long count() {
        return records.size();
    }

    @Override
    public void deleteById(Integer id) {
        // not needed in implementation
    }

    @Override
    public void delete(Record entity) {
        // not needed in implementation
        
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        // not needed in implementation
        
    }

    @Override
    public void deleteAll(Iterable<? extends Record> entities) {
        // not needed in implementation
        
    }

    @Override
    public void deleteAll() {
        // not needed in implementation
        
    }
}
