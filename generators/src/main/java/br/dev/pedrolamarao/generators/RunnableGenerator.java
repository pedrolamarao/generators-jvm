package br.dev.pedrolamarao.generators;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public class RunnableGenerator<T> implements Generator<T>
{
    private static final ContinuationScope scope = new ContinuationScope("RunnableGenerator");

    private static class ContinuationImpl<T> extends Continuation
    {
        private T next;

        public ContinuationImpl (Runnable target)
        {
            super(scope, target);
        }
    }

    private final ContinuationImpl<T> continuation;

    public RunnableGenerator (Runnable runnable)
    {
        this.continuation = new ContinuationImpl<T>(runnable);
    }

    public final T next ()
    {
        continuation.run();
        return continuation.next;
    }

    public static <T> void yield (T value)
    {
        ((ContinuationImpl) Continuation.getCurrentContinuation(scope)).next = value;
        Continuation.yield(scope);
    }
}
