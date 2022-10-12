package com.tomaswardle.williamsleacodetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WilliamsleacodetestApplicationTests {

	@Test
	void recordUpdates() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		RecordBuilder r = new RecordBuilder(m);
		r.addData("ASH RAIL LTD                         11467106    (C2) 01/05/2020");
		Record rec = r.fetchRecord();

		assertEquals("ASH RAIL LTD", rec.companyName);
		assertEquals("11467106", rec.companyNum);
		assertEquals("NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.", rec.eventType);
		assertEquals(LocalDate.of(2020,05,01), rec.eventDate);
	}

	@Test
	void recordUpdateWithoutEvent() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		RecordBuilder r = new RecordBuilder(m);
		r.addData("ASH RAIL LTD                         11467106                   ");
		Record rec = r.fetchRecord();

		assertEquals("ASH RAIL LTD", rec.companyName);
		assertEquals("11467106", rec.companyNum);
		assertEquals(null, rec.eventType);
		assertEquals(null, rec.eventDate);
	}

	@Test
	void recordUpdateWithoutEventType() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		RecordBuilder r = new RecordBuilder(m);
		r.addData("ASH RAIL LTD                         11467106    (C2) 01/05/2020");
		r.addData("ASH RAIL LTD                         11467106         01/05/2020");
		Record rec = r.fetchRecord();

		assertEquals("ASH RAIL LTD", rec.companyName);
		assertEquals("11467106", rec.companyNum);
		assertEquals(null, rec.eventType);
		assertEquals(LocalDate.of(2020,05,01), rec.eventDate);
	}

	@Test
	void recordHandlesStraddling() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		RecordBuilder r = new RecordBuilder(m);
		r.addData("ASH RAIL                             11467106    (C2) 01/05/2020");
		r.addData(" LTD                                                            ");
		Record rec = r.fetchRecord();

		assertEquals("ASH RAIL LTD", rec.companyName);
		assertEquals("11467106", rec.companyNum);
		assertEquals("NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.", rec.eventType);
		assertEquals(LocalDate.of(2020,05,01), rec.eventDate);
	}

	//tests to see if the events are properly extracted
	@Test
	void eventsExtracted() {
		EventTypeExtractor e = new EventTypeExtractor();
		HashMap<String,String> testcase = new HashMap<String,String>();

		testcase.put("(A)","CERTIFICATES OF INCORPORATION");
		testcase.put("(B1)","THE COMPANY'S MEMORANDUM AND ARTICLES.");
		testcase.put("(B2)","ANY AMENDMENT TO COMPANY'S ARTICLES (INCLUDING EVERY RESOLUTION OR AGREEMENT REQUIRED TO BE EMBODIED IN OR ANNEXED TO COPIES OF THE COMPANY'S ARTICLES ISSUED BY THE COMPANY).");
		testcase.put("(G11)","ANY STATEMENT OF COMPLIANCE DELIVERED UNDER SECTION 762 (STATEMENT THAT COMPANY MEETS CONDITIONS FOR ISSUE OF TRADING CERTIFICATE).");

		//tests contains all possible lines within the document
		String[] tests = 
		{
			"                    (A)    CERTIFICATES OF INCORPORATION",
			"                    (B1)   THE COMPANY'S MEMORANDUM AND ARTICLES.",
			"                    (B2)   ANY AMENDMENT TO COMPANY'S ARTICLES (INCLUDING EVERY RESOLUTION OR AGREEMENT REQUIRED TO BE EMBODIED IN OR",
			"                           ANNEXED TO COPIES OF THE COMPANY'S ARTICLES ISSUED BY THE COMPANY).",
			"                    (G11)  ANY STATEMENT OF COMPLIANCE DELIVERED UNDER SECTION 762 (STATEMENT THAT COMPANY MEETS CONDITIONS FOR ISSUE OF",
			"                           TRADING CERTIFICATE).",
			"                                                                                                  LOUISE SMYTH",
			"                                                                                                  REGISTRAR OF COMPANIES"
		};
		
		for (String test : tests) {
			e.processLine(test);
		}

		assertEquals(testcase,e.getMap());
	}

	//tests to see if the records are properly extracted from the file
	@Test
	void recordsExtracted() {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		map.put("(D4)","THE COMPANY'S CONFIRMATION STATEMENT.");

		ArrayRecordTable mockTable = new ArrayRecordTable();
		RecordExtractor recExtractor = new RecordExtractor(map, mockTable);
		
		String line = "          ASH RAIL LTD                         11467106    (C2) 01/05/2020  ASH TREE FIELDS MANAGEMENT COMPANY   09062234    (D4) 04/05/2020";
		recExtractor.processLine(line);
		recExtractor.close();

		ArrayList<Record> expectedRecords = new ArrayList<Record>(); 
		expectedRecords.add(new Record("ASH RAIL LTD","11467106","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.",LocalDate.of(2020, 05, 01)));
		expectedRecords.add(new Record("ASH TREE FIELDS MANAGEMENT COMPANY","09062234","THE COMPANY'S CONFIRMATION STATEMENT.", LocalDate.of(2020,05,04)));
		
		ArrayList<Record> actualRecords = mockTable.getRecords();
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
