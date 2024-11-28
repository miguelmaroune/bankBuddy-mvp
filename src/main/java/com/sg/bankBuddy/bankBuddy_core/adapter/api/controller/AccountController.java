package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.AccountLedgerDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateAccountDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.TransactionDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.TransactionRequestDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.AccountLedgerDtoMaper;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.CreateAccountDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.TransactionDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.*;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final DepositTransactionUseCase depositTransactionUseCase;
    private final WithdrawalTransactionUseCase withdrawalTransactionUseCase;
    private final AccountLedgerUseCase accountLedgerUseCase;
    private final TransactionReportUseCase transactionReportUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase, DepositTransactionUseCase depositTransactionUseCase, WithdrawalTransactionUseCase withdrawalTransactionUseCase, AccountLedgerUseCase accountLedgerUseCase, TransactionReportUseCase transactionReportUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.depositTransactionUseCase = depositTransactionUseCase;
        this.withdrawalTransactionUseCase = withdrawalTransactionUseCase;
        this.accountLedgerUseCase = accountLedgerUseCase;
        this.transactionReportUseCase = transactionReportUseCase;
    }

    @Operation(summary = "Create a new account", description = "Creates a new bank account based on provided details.")
    @ApiResponse(responseCode = "201", description = "Account successfully created.")
    @PostMapping
    public ResponseEntity<CreateAccountDto> createAccount(@RequestBody CreateAccountDto accountDto) {
        Account account = CreateAccountDtoMapper.toDomain(accountDto);
        Account createdAccount = createAccountUseCase.createAccount(account);
        return new ResponseEntity<>(CreateAccountDtoMapper.toDto(createdAccount), HttpStatus.CREATED);
    }

    @Operation(summary = "Deposit money into an account", description = "Deposits a specified amount into an account.")
    @ApiResponse(responseCode = "201", description = "Deposit successfully made.")
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<TransactionDto> deposit(@PathVariable UUID accountId, @Valid @RequestBody TransactionRequestDto depositRequestDto) {
        Transaction transaction = depositTransactionUseCase.deposit(accountId, depositRequestDto.getAmount());
        return new ResponseEntity<>(TransactionDtoMapper.toDto(transaction), HttpStatus.CREATED);
    }

    @Operation(summary = "Withdraw money from an account", description = "Withdraws a specified amount from an account.")
    @ApiResponse(responseCode = "201", description = "Withdrawal successfully made.")
    @PostMapping("/{accountId}/withdrawal")
    public ResponseEntity<TransactionDto> withdrawal(@PathVariable UUID accountId, @Valid @RequestBody TransactionRequestDto depositRequestDto) {
        Transaction transaction = withdrawalTransactionUseCase.withdrawal(accountId, depositRequestDto.getAmount());
        return new ResponseEntity<>(TransactionDtoMapper.toDto(transaction), HttpStatus.CREATED);
    }

    @Operation(summary = "Get account ledger", description = "Fetches the transaction history for a given account.")
    @ApiResponse(responseCode = "200", description = "Transaction ledger retrieved successfully.")
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<AccountLedgerDto>> getAccountLedger(@PathVariable UUID accountId,
                                                                   @RequestParam(name = "type") @Pattern(regexp = "VALID|PENDING|REJECTED", message = "Invalid transaction type. Allowed values are: VALID, PENDING, REJECTED") String type,
                                                                   @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") @PastOrPresent(message = "The 'from' date cannot be in the future") LocalDate from) {

        if (from == null) {
            from = LocalDate.now().minusYears(1);
        }

        LocalDateTime fromDateTime = from.atStartOfDay();

        List<Transaction> transactions = accountLedgerUseCase.findByAccountId(accountId, fromDateTime, type, Sort.by(Sort.Direction.DESC, "timeStamp"));

        List<AccountLedgerDto> transactionDtos = transactions.stream().map(AccountLedgerDtoMaper::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(transactionDtos, HttpStatus.OK);
    }

    @Operation(summary = "Generate a PDF report for transactions", description = "Generates a downloadable PDF report of transactions for a given account.")
    @ApiResponse(responseCode = "200", description = "PDF report successfully generated.")
    @GetMapping("/{accountId}/transactions/report")
    public ResponseEntity<byte[]> generatePdf(@PathVariable UUID accountId,
                                              @RequestParam(name = "type")
                                              @Pattern(regexp = "VALID|PENDING|REJECTED", message = "Invalid transaction type. Allowed values are: VALID, PENDING, REJECTED") String type,
                                              @RequestParam(name = "from", required = false)
                                              @DateTimeFormat(pattern = "dd/MM/yyyy")
                                              @PastOrPresent(message = "The 'from' date cannot be in the future") LocalDate from) {

        if (from == null) {
            from = LocalDate.now().minusYears(1);
        }

        LocalDateTime fromDateTime = from.atStartOfDay();

        byte[] pdfBytes = transactionReportUseCase.generateTransactionReport(accountId, fromDateTime, type, Sort.by(Sort.Direction.DESC, "timeStamp"));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_report.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
