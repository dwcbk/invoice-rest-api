package com.dwcbk.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

/**
 * Invoice Bean with settings for JSON parsing and Hibernate.
 */
@Entity
@Table(name = "invoices")
@JsonPropertyOrder({ "id", "invoice_number", "po_number", "due_date", "amount_cents", "created_at"})
public class Invoice {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@JsonProperty("invoice_number")
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@JsonProperty("po_number")
	@Column(name = "po_number")
	private String poNumber;

	private int amount_cents;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date due_date;

	@JsonProperty("created_at")
	@Column(name = "created_at")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="America/New_York")
	private Date createdAt;

	@PrePersist
	private void setCreatedDate() {
		this.createdAt = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public int getAmount_cents() {
		return amount_cents;
	}

	public void setAmount_cents(int amount_cents) {
		this.amount_cents = amount_cents;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Invoice{" +
				"id=" + id +
				", invoice_number='" + invoiceNumber + '\'' +
				", poNumber='" + poNumber + '\'' +
				", amount_cents=" + amount_cents +
				", created_at=" + createdAt +
				", due_date=" + due_date +
				'}';
	}
}
