package br.dev.pedrolamarao.generators.line;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Supplier;

public class LineSuppliers
{
    public static Supplier<String> from (char[] chars)
    {
        return new CharArraySupplier(chars,0,chars.length);
    }

    public static Supplier<String> from (Reader reader)
    {
        return new ReaderSupplier(reader,8192);
    }

    public static Supplier<String> from (Reader reader, int capacity)
    {
        return new ReaderSupplier(reader,capacity);
    }

    private static final class CharArraySupplier implements Supplier<String>
    {
        private final char[] chars;

        private int position;

        private final int limit;

        CharArraySupplier (char[] chars, int offset, int length)
        {
            this.chars = chars;
            position = offset;
            limit = offset + length;
        }

        @Override
        public String get ()
        {
            if (position == limit) return null;
            // next
            for (int i = position, j = limit; i != j; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    final var line = new String(chars, position, i - position);
                    position = i + 1;
                    return line;
                }
            }
            // last
            {
                final var line = new String(chars, position, limit - position);
                position = limit;
                return line;
            }
        }
    }

    private static final class ReaderSupplier implements Supplier<String>
    {
        private final StringBuilder buffer = new StringBuilder();

        private final char[] chars;

        private int limit = 0;

        private int position = 0;

        private final Reader reader;

        ReaderSupplier (Reader reader, int capacity)
        {
            this.chars = new char[capacity];
            this.reader = reader;
        }

        @Override
        public String get ()
        {
            if (limit == -1) return null;

            try
            {
                while (true)
                {
                    if (position == limit) {
                        limit = reader.read(chars);
                        if (limit == -1) break;
                        position = 0;
                    }

                    for (int i = position, j = limit; i != j; ++i) {
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
                            return line;
                        }
                    }

                    buffer.append(chars,position,limit-position);
                    position = limit;
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            return buffer.isEmpty() ? null : buffer.toString();
        }
    }
}
