package com.dwcbk.controller;

import com.dwcbk.beans.Invoice;
import com.dwcbk.exception.BadRequestException;
import com.dwcbk.exception.ResourceNotFoundException;
import com.dwcbk.repo.InvoiceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Handles all invoice REST endpoints, including: <br/>
 * - /v1/invoices/{id} <br/>
 * - /v1/invoices <br/>
 * 	 - Handles searching for invvoices, see {@link #searchInvoices(String, String, int, int)} <br/>
 * 	 - Handles adding new invoices both as POST requests, one method handles sending JSON text in the request body,
 * 	 	see {@link #addInvoiceViaJson(Invoice)}. The other method handles adding via a urlencoed web form,
 * 	 	see {@link #addInvoiceViaForm(String, String, String, int)}
 */
@RestController
public class InvoiceController {

	@Autowired
	InvoiceRepository invoiceRepository;

	/**
	 * Search for invoice by id. Not part of project requirements, but included for convenience.
	 * @param id invoice id
	 * @return
	 */
	@RequestMapping(value = "/v1/invoice/{id}", method = GET)
	public Invoice getInvoice(@PathVariable("id") int id) {
		Optional<Invoice> invoice = invoiceRepository.findById(id);
		return invoice.orElseThrow(ResourceNotFoundException::new);
	}


	@RequestMapping(value = "/v1/invoice", method = POST, headers="Content-Type=application/json")
	public Invoice addInvoiceViaJson(@RequestBody Invoice invoice) {
		invoice = invoiceRepository.save(invoice);
		return invoice;
	}

	private static final DateFormat DUE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/v1/invoice", method = POST, headers="Content-Type=application/x-www-form-urlencoded")
	public Invoice addInvoiceViaForm(@RequestParam(value = "invoice_number", required = false) String invoice_number,
							  @RequestParam(value = "po_number", required = false) String po_number,
							  @RequestParam(value = "due_date", required = false) String due_date,
							  @RequestParam(value = "amount_cents", required = false) int amount_cents) {

		Date dueDate = getDueDate(due_date);
		Invoice invoice = new Invoice();
		invoice.setInvoiceNumber(invoice_number);
		invoice.setPoNumber(po_number);
		invoice.setDue_date(dueDate);
		invoice.setAmount_cents(amount_cents);

		invoice = invoiceRepository.save(invoice);
		return invoice;
	}

	private Date getDueDate(String val) {
		try {
			return DUE_DATE_FORMAT.parse(val);
		} catch (ParseException e) {
			throw new RuntimeException("Error parsing due date val: " + val, e);
		}
	}

	/**
	 * Search​ ​of​ ​invoice(s)​ ​by​ invoice_number ​or​ ​po_number​ ​criteria,​ ​supporting​ ​pagination​ ​by limit​ ​and​ ​offset.​ ​
	 * This​ ​will​ ​return​ ​the​ ​list​ ​of​ ​invoices​ ​matching​ ​the​ ​criteria​ ​sorted​ ​by created_at​ ​from​ ​newest​ ​to​ ​oldest.
	 * @return
	 */
	@RequestMapping(value = "/v1/invoice", method = GET)
	public List<Invoice> searchInvoices(@RequestParam(value = "invoice_number", required = false) String invoice_number,
										@RequestParam(value = "po_number", required = false) String po_number,
										@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
										@RequestParam(value = "offset", required = false, defaultValue = "0") int offset
	) {
		List<Invoice> results = null;
		if (StringUtils.isEmpty(invoice_number) && StringUtils.isEmpty(po_number)) {
			throw new BadRequestException();
		}
		// call different repo method depending on if po num, invoice num, or both args have values
		if (StringUtils.isNotEmpty(invoice_number) && StringUtils.isNotEmpty(po_number)) {
			results = invoiceRepository.findByInvoiceNumberAndPoNumberOrderByCreatedAtDesc(invoice_number, po_number);
		}
		else if (StringUtils.isNotEmpty(invoice_number)) {
			results = invoiceRepository.findByInvoiceNumberOrderByCreatedAtDesc(invoice_number);
		}
		else {
			results = invoiceRepository.findByPoNumberOrderByCreatedAtDesc(po_number);
		}
		// if no results found, return 404 response
		if (results.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		results = filter(results, limit, offset);
		// if no results found after filtering by limit/offset, also return 404
		if (results.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return results;
	}

	private List<Invoice> filter(List<Invoice> invoices, int limit, int offset) {
		return invoices.stream().skip(offset).limit(limit).collect(Collectors.toList());
	}
}
