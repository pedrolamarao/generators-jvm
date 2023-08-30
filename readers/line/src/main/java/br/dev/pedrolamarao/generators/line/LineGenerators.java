package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.AbstractGenerator;
import br.dev.pedrolamarao.generators.ConsumerGenerator;
import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LineGenerators
{
    public static Supplier<String> abstractFrom (char[] chars)
    {
        return new CharArrayAbstractGenerator(chars,0,chars.length);
    }

    public static Supplier<String> abstractFrom (Reader reader)
    {
        return new ReaderAbstractGenerator(reader,8192);
    }

    public static Supplier<String> abstractFrom (Reader reader, int capacity)
    {
        return new ReaderAbstractGenerator(reader,capacity);
    }

    public static Supplier<String> consumerFrom (char[] chars)
    {
        return new ConsumerLineGenerator(chars,0,chars.length);
    }

    public static Supplier<String> consumerFrom (Reader reader)
    {
        return new ConsumerLineGenerator(reader,8192);
    }

    public static Supplier<String> consumerFrom (Reader reader, int capacity)
    {
        return new ConsumerLineGenerator(reader,capacity);
    }

    public static Supplier<String> runnableFrom (char[] chars)
    {
        return new RunnableLineGenerator(chars,0,chars.length);
    }

    public static Supplier<String> runnableFrom (Reader reader)
    {
        return new RunnableLineGenerator(reader,8192);
    }

    public static Supplier<String> runnableFrom (Reader reader, int capacity)
    {
        return new RunnableLineGenerator(reader,capacity);
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
                    this.jield( new String(chars, position, i - position) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                this.jield( new String(chars, position, limit - position) );
            }

            this.jield(null);
        }
    }

    private static final class ReaderAbstractGenerator extends AbstractGenerator<String>
    {
        private final int capacity;

        private final Reader reader;

        public ReaderAbstractGenerator (Reader reader, int capacity)
        {
            this.capacity = capacity;
            this.reader = reader;
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
                            this.jield(line);
                        }
                    }

                    buffer.append(chars,position,limit-position);
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                this.jield( buffer.toString() );
            }

            this.jield(null);
        }
    }

    private static final class ConsumerLineGenerator extends ConsumerGenerator<String>
    {
        public ConsumerLineGenerator (char[] chars, int offset, int length)
        {
            super(generator -> run(chars,offset,length,generator));
        }

        public ConsumerLineGenerator (Reader reader, int capacity)
        {
            super(generator -> run(reader,capacity,generator));
        }

        private static void run (char[] chars, int offset, int length, Consumer<String> generator)
        {
            int position = offset;
            final int limit = offset + length;

            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    generator.accept( new String(chars, position, i - position) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                generator.accept( new String(chars, position, limit - position) );
            }

            generator.accept(null);
        }

        private static void run (Reader reader, int capacity, Consumer<String> generator)
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
                            generator.accept(line);
                        }
                    }

                    buffer.append(chars,position,limit-position);
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                generator.accept( buffer.toString() );
            }

            generator.accept(null);
        }
    }

    private static final class RunnableLineGenerator extends RunnableGenerator<String>
    {
        public RunnableLineGenerator (char[] chars, int offset, int length)
        {
            super(() -> run(chars,offset,length));
        }

        public RunnableLineGenerator (Reader reader, int capacity)
        {
            super(() -> run(reader,capacity));
        }

        private static void run (char[] chars, int offset, int length)
        {
            int position = offset;
            final int limit = offset + length;

            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    RunnableLineGenerator.yield( new String(chars, position, i - position) );
                    position = i + 1;
                }
            }

            if (position < limit) {
                RunnableLineGenerator.yield( new String(chars, position, limit - position) );
            }

            RunnableLineGenerator.yield(null);
        }

        private static void run (Reader reader, int capacity)
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
                            RunnableLineGenerator.yield(line);
                        }
                    }

                    buffer.append(chars,position,limit-position);
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            if (! buffer.isEmpty()) {
                RunnableLineGenerator.yield( buffer.toString() );
            }

            RunnableLineGenerator.yield(null);
        }
    }
}
