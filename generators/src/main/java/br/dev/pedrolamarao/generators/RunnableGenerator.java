package br.dev.pedrolamarao.generators;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

import java.util.function.Supplier;

public class RunnableGenerator<T> implements Supplier<T>
{
    private static final ContinuationScope scope = new ContinuationScope("RunnableGenerator");

    private static class ContinuationImpl<T> extends Continuation
    {
        private T next;

        ContinuationImpl (Runnable target)
        {
            super(scope, target);
        }
    }

    private final ContinuationImpl<T> continuation;

    public RunnableGenerator (Runnable runnable)
    {
        this.continuation = new ContinuationImpl<T>(runnable);
    }

    @Override
    public final T get ()
    {
        continuation.run();
        return continuation.next;
    }

    public static <T> void yield (T value)
    {
        ((ContinuationImpl<T>) Continuation.getCurrentContinuation(scope)).next = value;
        Continuation.yield(scope);
    }
}
