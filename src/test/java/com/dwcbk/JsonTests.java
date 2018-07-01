package com.dwcbk;

import com.dwcbk.beans.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

public class JsonTests {

	@Test
	public void testMapper() {
		Invoice i1 = new Invoice();
		i1.setId(1);
		i1.setInvoiceNumber("I1000");
		i1.setPoNumber("P1000");
		i1.setDue_date(new Date());
		i1.setCreatedAt(new Date());

		ObjectMapper mapper = new ObjectMapper();
		try {
			String output = mapper.writeValueAsString(i1);
			System.out.println("Invoice:\n" + output);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
