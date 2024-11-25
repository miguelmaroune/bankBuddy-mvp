package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateAccountDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.DepositRequestDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.TransactionDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.CreateAccountDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.TransactionDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.CreateAccountUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.DepositTransactionUseCase;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final DepositTransactionUseCase depositTransactionUseCase;
    public AccountController(CreateAccountUseCase createAccountUseCase, DepositTransactionUseCase depositTransactionUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.depositTransactionUseCase = depositTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateAccountDto> createAccount(@RequestBody CreateAccountDto accountDto) {
        Account  account = CreateAccountDtoMapper.toDomain(accountDto);
        Account createdAccount = createAccountUseCase.createAccount(account);
        return new ResponseEntity<>(CreateAccountDtoMapper.toDto(createdAccount), HttpStatus.CREATED);
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<TransactionDto> withdraw(@PathVariable UUID accountId, @Valid @RequestBody DepositRequestDto depositRequestDto) {
        Transaction transaction = depositTransactionUseCase.deposit(accountId, depositRequestDto.getAmount());
        return new ResponseEntity<>(TransactionDtoMapper.toDto(transaction), HttpStatus.CREATED);
    }
}
