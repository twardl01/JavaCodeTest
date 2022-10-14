package com.tomaswardle.williamsleacodetest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tomaswardle.williamsleacodetest.record.model.Record;

@SpringBootTest
public class RecordExtractorTests {
    
    //tests to see if the records are properly extracted from the file
	@Test
	public void recordsExtracted() {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		map.put("(D4)","THE COMPANY'S CONFIRMATION STATEMENT.");

		TestRecordRepo mockTable = new TestRecordRepo();
		RecordExtractor recExtractor = new RecordExtractor(mockTable);
		recExtractor.start(map);
		
		String line = "          ASH RAIL LTD                         11467106    (C2) 01/05/2020  ASH TREE FIELDS MANAGEMENT COMPANY   09062234    (D4) 04/05/2020";
		recExtractor.processLine(line);
		recExtractor.close();

		ArrayList<Record> expectedRecords = new ArrayList<Record>(); 
		expectedRecords.add(new Record("ASH RAIL LTD","11467106","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.",LocalDate.of(2020, 05, 01)));
		expectedRecords.add(new Record("ASH TREE FIELDS MANAGEMENT COMPANY","09062234","THE COMPANY'S CONFIRMATION STATEMENT.", LocalDate.of(2020,05,04)));
		
		List<Record> actualRecords = new ArrayList<Record>();
		mockTable.findAll().forEach(e -> actualRecords.add(e));
		assertEquals(expectedRecords.size(),actualRecords.size());

		for(int i = 0; i < actualRecords.size(); i++) {
			compareRecord(expectedRecords.get(i),actualRecords.get(i));
		}
	}

	public void compareRecord(Record r1, Record r2) {
		assertEquals(r1.companyName,r2.companyName);
		assertEquals(r1.companyNum,r2.companyNum);
		assertEquals(r1.eventType,r2.eventType);
		assertEquals(r1.eventDate,r2.eventDate);
	}
}
