package com.sg.bankBuddy.bankBuddy_core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Set<Account> accounts;

}
