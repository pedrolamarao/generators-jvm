package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class NextBenchmark
{
    static final int lines = 10240;

    @State(Scope.Benchmark)
    public static class BufferedReaderState
    {
        int counter;

        BufferedReader reader;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            reader = new BufferedReader(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long bufferedReader (BufferedReaderState it) throws Exception
    {
        while (true) {
            final var line = it.reader.readLine();
            if (line == null) break;
            ++it.counter;
        }
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    @State(Scope.Benchmark)
    public static class ParserState
    {
        final char[] buffer = new char[8192];

        int counter;

        Reader reader;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            reader = new StringReader( data() );
        }
    }

    @Benchmark
    public long parser (ParserState it) throws IOException
    {
        LineParser.parse(it.reader,it.buffer, line -> ++it.counter);
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    @State(Scope.Benchmark)
    public static class AbstractGeneratorState
    {
        int counter;

        LineAbstractReader reader;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            reader = new LineAbstractReader(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long abstractGenerator (AbstractGeneratorState it)
    {
        while (true) {
            final var line = it.reader.read();
            if (line == null) break;
            ++it.counter;
        }
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    @State(Scope.Benchmark)
    public static class RunnableGeneratorState
    {
        int counter;

        LineRunnableReader reader;

        @Setup(Level.Invocation)
        public void setup ()
        {
            counter = 0;
            reader = new LineRunnableReader(new StringReader(data()),8192);
        }
    }

    @Benchmark
    public long runnableGenerator (RunnableGeneratorState it)
    {
        while (true) {
            final var line = it.reader.read();
            if (line == null) break;
            ++it.counter;
        }
        if (it.counter != lines) throw new RuntimeException("unexpected counter: " + it.counter);
        return it.counter;
    }

    public static String data ()
    {
        final var builder = new StringBuilder();
        IntStream.range(0,lines).forEach(_1 -> builder.append("2022-02-02,foo ,bar, 1.0, Description of this record\n"));
        return builder.toString();
    }
}
