package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

/**
 * For performing transactional rollback within a reactive pipeline
 */
public class TestTransaction {
	public static <T> Uni<T> withRollback(Uni<T> uni) {
		return Panache.getSession().withTransaction(tx -> {
			tx.markForRollback();
			return uni;
		});
	}
}
