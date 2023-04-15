package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class LineBenchmark
{
    @Param({"1024"})
    int length;

    @Param({"1024"})
    int count;

    String data;

    @Benchmark
    public long base () throws IOException
    {
        final var reader = new BufferedReader( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.readLine();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long supplierFromCharArray ()
    {
        int counter = 0;
        final var supplier = LineSuppliers.from( data.toCharArray() );
        while (true) {
            final var line = supplier.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long supplierFromReader ()
    {
        int counter = 0;
        final var supplier = LineSuppliers.from( new StringReader(data) );
        while (true) {
            final var line = supplier.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long abstractGeneratorFromCharArray ()
    {
        final var reader = LineGenerators.abstractFrom( data.toCharArray() );
        int counter = 0;
        while (true) {
            final var line = reader.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long abstractGeneratorFromReader ()
    {
        final var reader = LineGenerators.abstractFrom( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGeneratorFromCharArray ()
    {
        final var reader = LineGenerators.runnableFrom( data.toCharArray() );
        int counter = 0;
        while (true) {
            final var line = reader.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGeneratorFromReader ()
    {
        final var reader = LineGenerators.runnableFrom( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Setup
    public void generate ()
    {
        final var builder = new StringBuilder();
        IntStream
            .range(0,count).forEach(_1 -> {
                final char[] chars = new char[length];
                Arrays.fill(chars,'x');
                chars[chars.length-1] = '\n';
                builder.append(chars);
            });
        data = builder.toString();
    }
}
