package com.sg.bankBuddy.bankBuddy_core.service;
import com.sg.bankBuddy.bankBuddy_core.application.service.AccountLedgerService;
import com.sg.bankBuddy.bankBuddy_core.application.service.TransactionReportService;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.AccountType;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.Currency;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.AccountNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionReportServiceTest {

    @Mock
    private AccountLedgerService accountLedgerService;

    private TransactionReportService transactionReportService;

    private UUID accountId;
    private Account mockAccount;
    private Transaction mockTransaction;
    private LocalDateTime fromDateTime;
    private Sort timeStamp;

    @BeforeEach
    public void setUp() {
        accountId = UUID.randomUUID();
        mockAccount = new Account();
        mockAccount.setId(accountId);
        mockAccount.setBalance(BigDecimal.valueOf(1000));
        mockAccount.setType(AccountType.CURRENT);
        mockAccount.setCurrency(Currency.EUR);

        mockTransaction = Transaction.builder()
                .account(mockAccount)
                .amount(BigDecimal.valueOf(100))
                .balance(BigDecimal.valueOf(1100))
                .type(TransactionType.DEPOSIT)
                .timeStamp(LocalDateTime.now())
                .build();

        fromDateTime = LocalDateTime.now().minusDays(7);
        timeStamp = Sort.by(Sort.Order.desc("timeStamp"));

        transactionReportService = new TransactionReportService(accountLedgerService);
    }

    @Test
    public void testGenerateTransactionReport_Success() {
        when(accountLedgerService.findByAccountId(accountId, fromDateTime, "DEPOSIT", timeStamp))
                .thenReturn(List.of(mockTransaction));

        byte[] report = transactionReportService.generateTransactionReport(accountId, fromDateTime, "DEPOSIT", timeStamp);

        verify(accountLedgerService).findByAccountId(accountId, fromDateTime, "DEPOSIT", timeStamp);
        assert report.length > 0;
    }

    @Test
    public void testGenerateTransactionReport_AccountNotFound() {
        when(accountLedgerService.findByAccountId(accountId, fromDateTime, "DEPOSIT", timeStamp))
                .thenReturn(List.of());

        try {
            transactionReportService.generateTransactionReport(accountId, fromDateTime, "DEPOSIT", timeStamp);
        } catch (AccountNotFoundException e) {
            assert e.getMessage().equals("The account could not be found.");
        }
    }
    @Test
    public void testGenerateTransactionReport_InvalidAccountId() {
        UUID invalidAccountId = UUID.randomUUID();
        when(accountLedgerService.findByAccountId(invalidAccountId, fromDateTime, "DEPOSIT", timeStamp))
                .thenThrow(new AccountNotFoundException(BankBudyErrorCodes.ACCOUNT_NOT_FOUND));

        try {
            transactionReportService.generateTransactionReport(invalidAccountId, fromDateTime, "DEPOSIT", timeStamp);
        } catch (AccountNotFoundException e) {
            assert e.getMessage().equals("The account could not be found.");
        }
    }
}
