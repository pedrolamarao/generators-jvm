package br.dev.pedrolamarao.generators;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Iterator;
import java.util.stream.IntStream;

@State(Scope.Thread)
public class IntegerGeneratorBenchmark
{
    final AbstractGenerator<Integer> abstractGenerator = new AbstractGenerator<>()
    {
        @Override protected void run ()
        {
            int seed = 0;
            while (true) jield(seed++);
        }
    };

    final ConsumerGenerator<Integer> consumerGenerator = new ConsumerGenerator<>(jield ->
    {
        int seed = 0;
        while (true) jield.accept(seed);
    });

    final RunnableGenerator<Integer> runnableGenerator = new RunnableGenerator<>(() ->
    {
        int seed = 0;
        while (true) RunnableGenerator.yield(seed);
    });

    final Iterator<Integer> stream = IntStream.iterate(0, it -> it + 1).iterator();

    @Benchmark
    public Object abstractGenerator ()
    {
        return abstractGenerator.get();
    }

    @Benchmark
    public Object consumerGenerator ()
    {
        return consumerGenerator.get();
    }

    @Benchmark
    public Object runnableGenerator ()
    {
        return runnableGenerator.get();
    }

    @Benchmark
    public Object stream ()
    {
        return stream.next();
    }
}
