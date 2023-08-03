package org.acme;

import java.util.function.Supplier;

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

	public <T> Mono<T> withRollback(Supplier<Mono<T>> mono) {
		return this.rxtx.execute(tx -> {
			tx.setRollbackOnly();
			return mono.get();
		}).next();
	}
}
