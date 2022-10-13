package com.tomaswardle.williamsleacodetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.Test;
import com.tomaswardle.williamsleacodetest.record.model.Record;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecordBuilderTests {
    @Test
	void recordUpdates() {
		RecordBuilder r = recordAdditionSetup();

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

	@Test
	void recordHandlesInvalidNumber() {
		RecordBuilder r = recordAdditionSetup();

		r.addData("ASH RAIL LTD                         114b7106    (C2) 01/05/2020");
		Record rec = r.fetchRecord();
		assertTrue(!rec.isValid());
	}

	@Test
	void recordHandlesInvalidEventType() {
		RecordBuilder r = recordAdditionSetup();

		r.addData("ASH RAIL LTD                         11467106    (C) 01/05/2020");
		Record rec = r.fetchRecord();
		assertTrue(!rec.isValid());
	}

	@Test
	void recordHandlesInvalidEventDate() {
		RecordBuilder r = recordAdditionSetup();

		r.addData("ASH RAIL LTD                         11467106    (C2) 01/g5/2020");
		Record rec = r.fetchRecord();
		assertTrue(!rec.isValid());
	}

    public RecordBuilder recordAdditionSetup() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("(C2)","NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.");
		return new RecordBuilder(m);
	}
}
