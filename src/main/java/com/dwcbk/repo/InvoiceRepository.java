package com.dwcbk.repo;

import com.dwcbk.beans.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Handles loading Invoices from the DB in several different ways.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	Optional<Invoice> findById(int id);
	List<Invoice> findByPoNumberOrderByCreatedAtDesc(String poNumber);
	List<Invoice> findByInvoiceNumberOrderByCreatedAtDesc(String invoiceNumber);
	List<Invoice> findByInvoiceNumberAndPoNumberOrderByCreatedAtDesc(String invoiceNumber, String poNumber);
}
