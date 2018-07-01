delete from invoices;
insert into invoices(id,invoice_number,po_number,due_date,amount_cents) values(1, 'I1001','P1001','2018-7-15', 2500);
insert into invoices(id,invoice_number,po_number,due_date,amount_cents,created_at) values(2, 'I1002','P1002',DATE_ADD(CURDATE(), INTERVAL 10 DAY), 9995, DATE_ADD(CURDATE(), INTERVAL -10 DAY));
insert into invoices(id,invoice_number,po_number,due_date,amount_cents,created_at) values(3, 'I1003','P1002',DATE_ADD(CURDATE(), INTERVAL 10 DAY), 9995, DATE_ADD(CURDATE(), INTERVAL -5 DAY));
insert into invoices(id,invoice_number,po_number,due_date,amount_cents) values(4, 'I1004','P1002',DATE_ADD(CURDATE(), INTERVAL 10 DAY), 9995);
insert into invoices(id,invoice_number,po_number,due_date,amount_cents) values(5, 'I1005','P1002',DATE_ADD(CURDATE(), INTERVAL 10 DAY), 9995);
insert into invoices(id,invoice_number,po_number,due_date,amount_cents) values(6, 'I1006','P1005',DATE_ADD(CURDATE(), INTERVAL 10 DAY), 50000);