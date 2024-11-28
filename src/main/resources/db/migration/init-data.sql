INSERT INTO client (fist_name, last_name, email, address)
VALUES ('John', 'Doe', 'john.doe@example.com', '1234 Elm Street');

INSERT INTO account (account_id , type, currency, balance, client_id)
VALUES ('c1234567-89ab-cdef-0123-456789abcdef','SAVINGS', 'USD', 1000.00, 1);

INSERT INTO transaction (account_id, type, status, amount, description, time_stamp, balance)
VALUES
    ('c1234567-89ab-cdef-0123-456789abcdef', 'DEPOSIT', 'VALID', 500.00, 'Initial Deposit', CURRENT_TIMESTAMP, 500.00),
    ('c1234567-89ab-cdef-0123-456789abcdef', 'DEPOSIT', 'VALID', 700.00, 'Initial Deposit', CURRENT_TIMESTAMP, 1200.00),
    ('c1234567-89ab-cdef-0123-456789abcdef', 'WITHDRAWAL', 'VALID', 200.00, 'ATM Withdrawal', CURRENT_TIMESTAMP, 1000.00),
    ('c1234567-89ab-cdef-0123-456789abcdef', 'DEPOSIT', 'REJECTED', 3000.00, 'Failed Deposit', CURRENT_TIMESTAMP, 1000.00),
    ('c1234567-89ab-cdef-0123-456789abcdef', 'WITHDRAWAL', 'REJECTED', 100.00, 'Insufficient Funds', CURRENT_TIMESTAMP, 1000.00);
