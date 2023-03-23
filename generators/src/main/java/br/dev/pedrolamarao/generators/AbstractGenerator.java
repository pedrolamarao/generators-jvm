package br.dev.pedrolamarao.generators;

public abstract class AbstractGenerator<T> implements Generator<T>
{
    private final jdk.internal.vm.Continuation continuation;

    private static final jdk.internal.vm.ContinuationScope scope = new jdk.internal.vm.ContinuationScope("AbstractGenerator");

    protected T next;

    public AbstractGenerator ()
    {
        this.continuation = new jdk.internal.vm.Continuation(scope,this::run);
    }

    public final T next ()
    {
        continuation.run();
        return next;
    }

    protected abstract void run ();

    protected final void yield ()
    {
        continuation.yield(scope);
    }
}
