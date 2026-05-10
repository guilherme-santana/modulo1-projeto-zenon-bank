create TABLE transactions (
                              id BIGINT auto_increment primary key,
                              step INT not null,
                              type VARCHAR(20) not null,
                              amount DECIMAL(20,2) not null,
                              name_origin VARCHAR(50) not null,
                              old_balance_orgin DECIMAL(20,2) not null,
                              new_balance_origin DECIMAL(20,2) not null,
                              name_recipient VARCHAR(20) not null,
                              old_balance_recipient DECIMAL(20,2) not null,
                              new_balance_recipient DECIMAL(20,2) not null,
                              is_fraud TINYINT(1) DEFAULT 0,
                              is_flagged_fraud TINYINT(1) DEFAULT 0
);