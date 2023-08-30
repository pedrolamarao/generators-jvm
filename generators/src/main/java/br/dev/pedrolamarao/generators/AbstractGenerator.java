package br.dev.pedrolamarao.generators;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

import java.util.function.Supplier;

public abstract class AbstractGenerator<T> implements Supplier<T>
{
    private static final ContinuationScope scope = new ContinuationScope("AbstractGenerator");

    private final Continuation continuation;

    private T next;

    public AbstractGenerator ()
    {
        this.continuation = new Continuation(scope,this::run);
    }

    @Override
    public final T get ()
    {
        continuation.run();
        return next;
    }

    protected abstract void run ();

    protected final void jield (T value)
    {
        next = value;
        Continuation.yield(scope);
    }
}
