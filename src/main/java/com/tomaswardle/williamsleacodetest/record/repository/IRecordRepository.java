package com.tomaswardle.williamsleacodetest.record.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tomaswardle.williamsleacodetest.record.model.Record;

@Repository
public interface IRecordRepository extends CrudRepository<Record, Integer> {  
    
}
