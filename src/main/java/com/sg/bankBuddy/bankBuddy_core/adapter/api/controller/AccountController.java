package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateAccountDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.CreateAccountDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.CreateAccountUseCase;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    public AccountController(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateAccountDto> createAccount(@RequestBody CreateAccountDto accountDto) {
        Account  account = CreateAccountDtoMapper.toDomain(accountDto);
        Account createdAccount = createAccountUseCase.createAccount(account);
        return new ResponseEntity<>(CreateAccountDtoMapper.toDto(createdAccount), HttpStatus.CREATED);
    }

}
