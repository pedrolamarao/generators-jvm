package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class NextBenchmark
{
    static final int lines = 10240;

    String data;

    @Benchmark
    public long bufferedReader () throws IOException
    {
        final var reader = new BufferedReader( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.readLine();
            if (line == null) break;
            ++counter;
        }
        if (counter != lines) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long parser () throws IOException
    {
        final int[] counter = { 0 };
        LineParser.parse( new StringReader(data), line -> ++counter[0] );
        if (counter[0] != lines) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long abstractGenerator ()
    {
        final var reader = new LineAbstractReader( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.read();
            if (line == null) break;
            ++counter;
        }
        if (counter != lines) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Benchmark
    public long runnableGenerator ()
    {
        final var reader = new LineRunnableReader( new StringReader(data) );
        int counter = 0;
        while (true) {
            final var line = reader.read();
            if (line == null) break;
            ++counter;
        }
        if (counter != lines) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    @Setup
    public void generate ()
    {
        final var builder = new StringBuilder();
        IntStream.range(0,lines).forEach(_1 -> builder.append("2022-02-02,foo ,bar, 1.0, Description of this record\n"));
        data = builder.toString();
    }
}
