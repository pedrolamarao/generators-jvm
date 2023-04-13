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
    public long iterateCharArray ()
    {
        int counter = 0;
        final var supplier = LineSuppliers.from(data.toCharArray());
        while (true) {
            final var line = supplier.get();
            if (line == null) break;
            ++counter;
        }
        if (counter != count) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long iterateReader ()
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
    public long abstractRead ()
    {
        final var reader = new LineAbstractReader( new StringReader(data) );
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
    public long runnableRead ()
    {
        final var reader = new LineRunnableReader( new StringReader(data) );
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
