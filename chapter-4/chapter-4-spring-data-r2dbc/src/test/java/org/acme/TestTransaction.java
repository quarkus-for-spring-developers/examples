package org.acme;

import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;

import reactor.core.publisher.Mono;

/**
 * For performing transactional rollback within a reactive pipeline
 */
@Component
public class TestTransaction {
	private final TransactionalOperator rxtx;

	public TestTransaction(TransactionalOperator rxtx) {
		this.rxtx = rxtx;
	}

	public <T> Mono<T> withRollback(Mono<T> publisher) {
		return this.rxtx.execute(tx -> {
			tx.setRollbackOnly();
			return publisher;
		}).next();
	}
}
