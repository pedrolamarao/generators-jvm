package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.AbstractGenerator;
import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Supplier;

public class LineGenerators
{
    public static Supplier<String> abstractFrom (char[] chars)
    {
        return new CharArrayAbstractGenerator(chars,0,chars.length);
    }

    public static Supplier<String> abstractFrom (Reader reader)
    {
        return new ReaderAbstractGenerator(reader);
    }

    public static Supplier<String> runnableFrom (char[] chars)
    {
        return new RunnableLineGenerator(chars,0,chars.length);
    }

    public static Supplier<String> runnableFrom (Reader reader)
    {
        return new RunnableLineGenerator(reader);
    }

    private static final class CharArrayAbstractGenerator extends AbstractGenerator<String>
    {
        private final char[] chars;

        private final int initial;

        private final int limit;

        public CharArrayAbstractGenerator (char[] chars, int offset, int length)
        {
            this.chars = chars;
            this.initial = offset;
            this.limit = offset + length;
        }

        @Override
        protected void run ()
        {
            int position = initial;

            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    this.yield( new String(chars, position, i - position) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                this.yield( new String(chars, position, limit - position) );
            }

            this.yield(null);
        }
    }

    private static final class ReaderAbstractGenerator extends AbstractGenerator<String>
    {
        private final Reader reader;

        public ReaderAbstractGenerator (Reader reader)
        {
            this.reader = reader;
        }

        @Override
        protected void run ()
        {
            final var buffer = new StringBuilder();

            try
            {
                int c = -1;
                while ((c = reader.read()) != -1)
                {
                    if (c == '\r' || c == '\n') {
                        this.yield( buffer.toString() );
                        buffer.setLength(0);
                    }
                    else {
                        buffer.append((char)c);
                    }
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                this.yield( buffer.toString() );
            }

            this.yield(null);
        }
    }

    private static final class RunnableLineGenerator extends RunnableGenerator<String>
    {
        public RunnableLineGenerator (char[] chars, int offset, int length)
        {
            super(() -> run(chars,offset,length));
        }

        public RunnableLineGenerator (Reader reader)
        {
            super(() -> run(reader));
        }

        private static void run (char[] chars, int offset, int length)
        {
            int position = offset;
            final int limit = offset + length;

            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    RunnableGenerator.yield( new String(chars, position, i - position) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                RunnableGenerator.yield( new String(chars, position, limit - position) );
            }

            RunnableGenerator.yield(null);
        }

        private static void run (Reader reader)
        {
            final var buffer = new StringBuilder();

            try
            {
                int c = -1;
                while ((c = reader.read()) != -1)
                {
                    if (c == '\r' || c == '\n') {
                        RunnableGenerator.yield( buffer.toString() );
                        buffer.setLength(0);
                    }
                    else {
                        buffer.append((char)c);
                    }
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                RunnableGenerator.yield( buffer.toString() );
            }

            RunnableGenerator.yield(null);
        }
    }
}
