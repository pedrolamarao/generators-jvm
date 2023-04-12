package br.dev.pedrolamarao.generators.line;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.*;
import java.nio.CharBuffer;
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
        LineParser.parse( new NoMarkResetReader( new StringReader(data) ), line -> ++counter[0] );
        if (counter[0] != lines) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long parserMarkReset () throws IOException
    {
        final int[] counter = { 0 };
        LineParser.parse( new StringReader(data), line -> ++counter[0] );
        if (counter[0] != lines) throw new RuntimeException("unexpected counter: " + counter[0]);
        return counter[0];
    }

    @Benchmark
    public long abstractGenerator ()
    {
        final var reader = new LineAbstractReader( new NoMarkResetReader( new StringReader(data) ) );
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
    public long abstractGeneratorMarkReset ()
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

    @Benchmark
    public long runnableGeneratorMarkReset ()
    {
        final var reader = new LineRunnableReader( new NoMarkResetReader( new StringReader(data) ) );
        int counter = 0;
        while (true) {
            final var line = reader.read();
            if (line == null) break;
            ++counter;
        }
        if (counter != lines) throw new RuntimeException("unexpected counter: " + counter);
        return counter;
    }

    //

    static class NoMarkResetReader extends Reader
    {
        private final Reader reader;

        NoMarkResetReader (Reader reader)
        {
            this.reader = reader;
        }

        @Override
        public void close () throws IOException
        {
            reader.close();
        }

        @Override
        public void mark (int readAheadLimit) throws IOException
        {
            throw new IOException();
        }

        @Override
        public boolean markSupported ()
        {
            return false;
        }

        @Override
        public int read () throws IOException
        {
            return reader.read();
        }

        @Override
        public int read (char[] cbuf) throws IOException
        {
            return reader.read(cbuf);
        }

        @Override
        public int read (char[] cbuf, int off, int len) throws IOException
        {
            return reader.read(cbuf,off,len);
        }

        @Override
        public int read (CharBuffer target) throws IOException
        {
            return reader.read(target);
        }

        @Override
        public boolean ready () throws IOException
        {
            return reader.ready();
        }

        @Override
        public void reset () throws IOException
        {
            throw new IOException();
        }

        @Override
        public long skip (long n) throws IOException
        {
            return reader.skip(n);
        }

        @Override
        public long transferTo (Writer out) throws IOException
        {
            return reader.transferTo(out);
        }
    }

    @Setup
    public void generate ()
    {
        final var builder = new StringBuilder();
        IntStream.range(0,lines).forEach(_1 -> builder.append("2022-02-02,foo ,bar, 1.0, Description of this record\n"));
        data = builder.toString();
    }
}
