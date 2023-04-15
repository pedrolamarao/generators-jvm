package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class LineBenchmark
{
    @Param({"1024"})
    int length;

    @Param({"1024"})
    int count;

    char[] chars;

    @Benchmark
    public long base () throws IOException
    {
        final var reader = new BufferedReader( new CharArrayReader(chars) );
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
        final var supplier = LineSuppliers.from(chars);
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
        final var supplier = LineSuppliers.from( new CharArrayReader(chars) );
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
        final var generator = LineGenerators.abstractFrom(chars);
        int counter = 0;
        while (true) {
            final var line = generator.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long abstractGeneratorFromReader ()
    {
        final var generator = LineGenerators.abstractFrom( new CharArrayReader(chars) );
        int counter = 0;
        while (true) {
            final var line = generator.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGeneratorFromCharArray ()
    {
        final var generator = LineGenerators.runnableFrom(chars);
        int counter = 0;
        while (true) {
            final var line = generator.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGeneratorFromReader ()
    {
        final var generator = LineGenerators.runnableFrom( new CharArrayReader(chars) );
        int counter = 0;
        while (true) {
            final var line = generator.get();
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
        chars = builder.toString().toCharArray();
    }
}
