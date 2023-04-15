package br.dev.pedrolamarao.generators;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConsumerGenerator<T> implements Supplier<T>
{
    private static final ContinuationScope scope = new ContinuationScope("ConsumerGenerator");

    private final Continuation continuation;

    private T next;

    public ConsumerGenerator (Consumer<Consumer<T>> procedure)
    {
        this.continuation = new Continuation(scope,() -> procedure.accept(this::yield));
    }

    @Override
    public final T get ()
    {
        continuation.run();
        return next;
    }

    private void yield (T value)
    {
        next = value;
        Continuation.yield(scope);
    }
}
