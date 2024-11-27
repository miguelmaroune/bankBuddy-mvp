package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.TransactionReportUseCase;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.AccountNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionReportService implements TransactionReportUseCase {
    private final AccountLedgerService accountLedgerService;

    public TransactionReportService(AccountLedgerService accountLedgerService) {
        this.accountLedgerService = accountLedgerService;
    }

    @Override
    public byte[] generateTransactionReport(UUID accountId, LocalDateTime fromDateTime, String type, Sort timeStamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        List<Transaction> transactionList = accountLedgerService.findByAccountId(accountId, fromDateTime, type, timeStamp);
        Account account = transactionList.stream()
                .findFirst()
                .map(Transaction::getAccount)
                .orElseThrow(() -> new AccountNotFoundException(BankBudyErrorCodes.ACCOUNT_NOT_FOUND));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);

        Document document = new Document(pdfDocument);
        document.add(new Paragraph("Account Transactions Report")
                .setFontSize(18).setLineThrough().simulateBold());
        document.add(new Paragraph("Account Type: " + account.getType()));
        document.add(new Paragraph("Account balance: "+ account.getBalance()));
        document.add(new Paragraph("Currency: " + account.getCurrency()));
        document.add(new Paragraph("Account ID: " + account.getId()));
        document.add(new Paragraph(" "));

        Table table = new Table(4);

        table.addHeaderCell(new Cell().add(new Paragraph("Date")));
        table.addHeaderCell(new Cell().add(new Paragraph("Transaction Type")));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("Balance")));

        for (Transaction transaction : transactionList) {
            table.addCell(transaction.getTimeStamp().format(formatter));
            table.addCell(transaction.getType().toString());
            table.addCell(transaction.getAmount().toString());
            table.addCell(transaction.getBalance().toString());
        }

        document.add(table);

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
