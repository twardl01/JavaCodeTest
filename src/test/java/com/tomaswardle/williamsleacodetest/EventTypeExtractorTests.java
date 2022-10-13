package com.tomaswardle.williamsleacodetest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventTypeExtractorTests {
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
}
