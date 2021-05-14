package org.acme.service;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

/**
 * An in-memory channel using {@link Sinks.Many} as the channel implementation.
 *
 * @param <T> The type of object in the channel
 */
public class InMemoryChannel<T> {
	private final Sinks.Many<T> sink;

	/**
	 * Constructor for an in-memory channel allowing consumers to determine the {@link Sinks.Many} implementation
	 * @param sink The {@link Sinks.Many} implementation
	 */
	public InMemoryChannel(Sinks.Many<T> sink) {
		this.sink = sink;
	}

	/**
	 * Default constructor using {@code Sinks.many().multicast().onBackpressureBuffer(1, false)} as the {@link Sinks.Many} implementation.
	 */
	public InMemoryChannel() {
		this(Sinks.many().multicast().onBackpressureBuffer(1, false));
	}

	/**
	 * Gets a {@link Publisher} for the channel for consumers to subscribe to
	 * @return A {@link Publisher} for the channel for consumers to subscribe to
	 */
	public Publisher<T> getPublisher() {
		return this.sink.asFlux();
	}

	/**
	 * Try emitting a non-null element, generating an {@link org.reactivestreams.Subscriber#onNext(Object) onNext} signal.
	 * The result of the attempt is represented as an {@link EmitResult}, which possibly indicates error cases.
	 * <p>
	 * Might throw an unchecked exception as a last resort (eg. in case of a fatal error downstream which cannot
	 * be propagated to any asynchronous handler, a bubbling exception, ...).
	 *
	 * @param value The value to emit, not {@code null}
	 * @return An {@link EmitResult}, which should be checked to distinguish different possible failures
	 * @see org.reactivestreams.Subscriber#onNext(Object)
	 */
	public EmitResult emitValue(T value) {
		return this.sink.tryEmitNext(value);
	}

	/**
	 * Try to terminate the sequence successfully, generating an {@link org.reactivestreams.Subscriber#onComplete() onComplete}
	 * signal. The result of the attempt is represented as an {@link EmitResult}, which possibly indicates error cases.
	 *
	 * @return An {@link EmitResult}, which should be checked to distinguish different possible failures
	 * @see org.reactivestreams.Subscriber#onComplete()
	 */
	public EmitResult emitComplete() {
		return this.sink.tryEmitComplete();
	}

	/**
	 * Try to fail the sequence, generating an {@link org.reactivestreams.Subscriber#onError(Throwable) onError}
	 * signal. The result of the attempt is represented as an {@link EmitResult}, which possibly indicates error cases.
	 *
	 * @param error The exception to signal, not {@code null}
	 * @return An {@link EmitResult}, which should be checked to distinguish different possible failures
	 * @see org.reactivestreams.Subscriber#onError(Throwable)
	 */
	public EmitResult emitError(Throwable error) {
		return this.sink.tryEmitError(error);
	}
}
