package br.dev.pedrolamarao.generators.csv;

import br.dev.pedrolamarao.generators.AbstractGenerator;
import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Supplier;

public class CsvGenerators
{
    public static Supplier<String[]> abstractFrom (char[] chars, char separator)
    {
        return new CharArrayAbstractGenerator(chars,0,chars.length,separator);
    }

    public static Supplier<String[]> abstractFrom (Reader reader, char separator)
    {
        return new ReaderAbstractGenerator(reader,8192,separator);
    }

    public static Supplier<String[]> abstractFrom (Reader reader, int capacity, char separator)
    {
        return new ReaderAbstractGenerator(reader,capacity,separator);
    }

    public static Supplier<String[]> runnableFrom (char[] chars, char separator)
    {
        return new RunnableLineGenerator(chars,0,chars.length,separator);
    }

    public static Supplier<String[]> runnableFrom (Reader reader, char separator)
    {
        return new RunnableLineGenerator(reader,8192,separator);
    }

    public static Supplier<String[]> runnableFrom (Reader reader, int capacity, char separator)
    {
        return new RunnableLineGenerator(reader,capacity,separator);
    }

    private static final class CharArrayAbstractGenerator extends AbstractGenerator<String[]>
    {
        private final char[] chars;

        private final int initial;

        private final int limit;

        private final String separator;

        public CharArrayAbstractGenerator (char[] chars, int offset, int length, char separator)
        {
            this.chars = chars;
            this.initial = offset;
            this.limit = offset + length;
            this.separator = String.valueOf(separator);
        }

        @Override
        protected void run ()
        {
            int position = initial;

            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    this.yield( new String(chars, position, i - position).split(separator,-1) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                this.yield( new String(chars, position, limit - position).split(separator,-1) );
            }

            this.yield(null);
        }
    }

    private static final class ReaderAbstractGenerator extends AbstractGenerator<String[]>
    {
        private final int capacity;

        private final Reader reader;

        private final String separator;

        public ReaderAbstractGenerator (Reader reader, int capacity, char separator)
        {
            this.capacity = capacity;
            this.reader = reader;
            this.separator = String.valueOf(separator);
        }

        @Override
        protected void run ()
        {
            final var buffer = new StringBuilder();
            final var chars = new char[capacity];

            try
            {
                while (true)
                {
                    final int limit = reader.read(chars);
                    if (limit == -1) break;
                    int position = 0;

                    for (int i = position; i != limit; ++i) {
                        final char c = chars[i];
                        if (c == '\r' || c == '\n') {
                            final String line;
                            if (buffer.isEmpty()) {
                                line = new String(chars,position,i-position);
                            }
                            else {
                                buffer.append(chars,position,i-position);
                                line = buffer.toString();
                                buffer.setLength(0);
                            }
                            position = i + 1;
                            this.yield(line.split(separator,-1));
                        }
                    }

                    buffer.append(chars,position,limit-position);
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                this.yield( buffer.toString().split(separator,-1) );
            }

            this.yield(null);
        }
    }

    private static final class RunnableLineGenerator extends RunnableGenerator<String[]>
    {
        public RunnableLineGenerator (char[] chars, int offset, int length, char separator)
        {
            super(() -> run(chars,offset,length,String.valueOf(separator)));
        }

        public RunnableLineGenerator (Reader reader, int capacity, char separator)
        {
            super(() -> run(reader,capacity,String.valueOf(separator)));
        }

        private static void run (char[] chars, int offset, int length, String separator)
        {
            int position = offset;
            final int limit = offset + length;

            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    RunnableGenerator.yield( new String(chars, position, i - position).split(separator,-1) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                RunnableGenerator.yield( new String(chars, position, limit - position).split(separator,-1) );
            }

            RunnableGenerator.yield(null);
        }

        private static void run (Reader reader, int capacity, String separator)
        {
            final var buffer = new StringBuilder();
            final var chars = new char[capacity];

            try
            {
                while (true)
                {
                    final int limit = reader.read(chars);
                    if (limit == -1) break;
                    int position = 0;

                    for (int i = position; i != limit; ++i) {
                        final char c = chars[i];
                        if (c == '\r' || c == '\n') {
                            final String line;
                            if (buffer.isEmpty()) {
                                line = new String(chars,position,i-position);
                            }
                            else {
                                buffer.append(chars,position,i-position);
                                line = buffer.toString();
                                buffer.setLength(0);
                            }
                            position = i + 1;
                            RunnableGenerator.yield(line.split(separator,-1));
                        }
                    }

                    buffer.append(chars,position,limit-position);
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                RunnableGenerator.yield( buffer.toString().split(separator,-1) );
            }

            RunnableGenerator.yield(null);
        }
    }
}
