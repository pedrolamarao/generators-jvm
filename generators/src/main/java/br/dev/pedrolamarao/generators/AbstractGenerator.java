package br.dev.pedrolamarao.generators;

public abstract class AbstractGenerator<T> implements Generator<T>
{
    private final jdk.internal.vm.Continuation continuation;

    private final jdk.internal.vm.ContinuationScope scope = new jdk.internal.vm.ContinuationScope("AbstractGenerator");

    private volatile T next;

    public AbstractGenerator ()
    {
        this.continuation = new jdk.internal.vm.Continuation(scope,this::run);
    }

    public T next ()
    {
        continuation.run();
        return next;
    }

    protected abstract void run ();

    protected void yield (T next)
    {
        this.next = next;
        continuation.yield(scope);
    }
}
