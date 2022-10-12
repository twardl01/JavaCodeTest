package com.tomaswardle.williamsleacodetest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class WilliamsleacodetestApplicationTests {
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	void emptyFileRead() {
		
	}

	@Test
	void oneLineFileRead() {

	}

	@Test
	void multiLineFileRead() {

	}

	@Test
	void recordUpdates() {
		RecordBuilder r = new RecordBuilder();
		r.addData("ASH RAIL LTD                         11467106    (C2) 01/05/2020");
		Record m = r.fetchRecord();

		assertEquals("ASH RAIL LTD", m.companyName);
		assertEquals(11467106, m.companyNum);
		assertEquals("(C2)", m.eventType);
		assertEquals(LocalDate.of(2020,05,01), m.eventDate);
	}

	@Test
	void recordUpdateWithoutEvent() {
		RecordBuilder r = new RecordBuilder();
		r.addData("ASH RAIL LTD                         11467106                   ");
		Record m = r.fetchRecord();

		assertEquals("ASH RAIL LTD", m.companyName);
		assertEquals(11467106, m.companyNum);
		assertEquals(null, m.eventType);
		assertEquals(null, m.eventDate);
	}

	@Test
	void recordUpdateWithoutEventType() {
		RecordBuilder r = new RecordBuilder();
		r.addData("ASH RAIL LTD                         11467106    (C2) 01/05/2020");
		r.addData("ASH RAIL LTD                         11467106         01/05/2020");
		Record m = r.fetchRecord();

		assertEquals("ASH RAIL LTD", m.companyName);
		assertEquals(11467106, m.companyNum);
		assertEquals(null, m.eventType);
		assertEquals(LocalDate.of(2020,05,01), m.eventDate);
	}

	@Test
	void recordHandlesStraddling() {
		RecordBuilder r = new RecordBuilder();
		r.addData("ASH RAIL                             11467106    (C2) 01/05/2020");
		r.addData(" LTD                                                            ");
		Record m = r.fetchRecord();


		assertEquals("ASH RAIL LTD", m.companyName);
		assertEquals(11467106, m.companyNum);
		assertEquals("(C2)", m.eventType);
		assertEquals(LocalDate.of(2020,05,01), m.eventDate);
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

		assertEquals(testcase,e.getHashmap());
	}

	//tests to see if the records are properly extracted from the file
	@Test
	void recordsExtracted() {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("(C2)","");
		RecordExtractor r = new RecordExtractor(map);
		
		String line = "          ASH RAIL LTD                         11467106    (C2) 01/05/2020  ASH TREE FIELDS MANAGEMENT COMPANY   09062234    (D4) 04/05/2020";

		r.processLine(line);
		Record[] expectedRecords = {new Record("ASH RAIL LTD",11467106,"",LocalDate.of(2020, 05, 01)),new Record("ASH TREE FIELDS MANAGEMENT COMPANY",(int) 09062234,"(D4)", LocalDate.of(2020,05,04))};
		assertEquals(expectedRecords,r.getRecords());
	}

}
