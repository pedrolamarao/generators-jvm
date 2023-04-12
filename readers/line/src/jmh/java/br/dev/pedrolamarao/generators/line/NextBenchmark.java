package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class NextBenchmark
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
    public long parseChars ()
    {
        final int[] counter = { 0 };
        LineParser.parse( data.toCharArray(), line -> ++counter[0] );
        if (counter[0] != count) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long parseCharsWithMarkReset ()
    {
        final int[] counter = { 0 };
        LineParser.parseWithMarkReset( data.toCharArray(), line -> ++counter[0] );
        if (counter[0] != count) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long parseReader () throws IOException
    {
        final int[] counter = { 0 };
        LineParser.parse( new StringReader(data), line -> ++counter[0] );
        if (counter[0] != count) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long parseReaderWithMarkReset () throws IOException
    {
        final int[] counter = { 0 };
        LineParser.parseWithMarkReset( new StringReader(data), line -> ++counter[0] );
        if (counter[0] != count) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long abstractRead ()
    {
        final var reader = new LineAbstractReader( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.read();
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
            final var line = reader.read();
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
