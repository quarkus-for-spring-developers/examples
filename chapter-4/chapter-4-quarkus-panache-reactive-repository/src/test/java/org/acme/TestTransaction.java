package org.acme;

import java.util.function.Supplier;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

/**
 * For performing transactional rollback within a reactive pipeline
 */
public class TestTransaction {
	public static <T> Uni<T> withRollback(Supplier<Uni<T>> uni) {
		return Panache.getSession()
			.flatMap(session -> session.withTransaction(tx -> {
				tx.markForRollback();
				return uni.get();
			}));
	}
}
