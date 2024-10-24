package com.hhplus.commerce.globals;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class TransactionEventLogger {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void logTransactionComplete(Object transactionEvent) {
        log.info("Transaction Complete: {}", transactionEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void logTransactionRollback(Object transactionEvent) {
        log.info("Transaction Rollback: {}", transactionEvent);
    }
}
