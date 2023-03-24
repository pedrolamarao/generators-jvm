package br.dev.pedrolamarao.generators;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public abstract class AbstractGenerator<T> implements Generator<T>
{
    private static final ContinuationScope scope = new ContinuationScope("AbstractGenerator");

    private final Continuation continuation;

    protected T next;

    public AbstractGenerator ()
    {
        this.continuation = new Continuation(scope,this::run);
    }

    public final T next ()
    {
        continuation.run();
        return next;
    }

    protected abstract void run ();

    protected final void yield ()
    {
        Continuation.yield(scope);
    }
}
