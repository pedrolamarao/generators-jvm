package br.dev.pedrolamarao.generators.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Supplier;

public class CsvSuppliers
{
    public static Supplier<String[]> from (char[] chars, char separator)
    {
        return new CharArraySupplier(chars,0,chars.length,separator);
    }

    public static Supplier<String[]> from (Reader reader, char separator)
    {
        return new ReaderSupplier(reader,8192,separator);
    }

    public static Supplier<String[]> from (Reader reader, int capacity, char separator)
    {
        return new ReaderSupplier(reader,capacity,separator);
    }

    private static final class CharArraySupplier implements Supplier<String[]>
    {
        private final char[] chars;

        private int position;

        private final int limit;

        private final String separator;

        CharArraySupplier (char[] chars, int offset, int length, char separator)
        {
            this.chars = chars;
            this.position = offset;
            this.limit = offset + length;
            this.separator = String.valueOf(separator);
        }

        @Override
        public String[] get ()
        {
            if (position == limit) return null;
            // next
            for (int i = position; i != limit; ++i) {
                final char c = chars[i];
                if (c == '\r' || c == '\n') {
                    final var line = new String(chars, position, i - position);
                    position = i + 1;
                    return line.split(separator,-1);
                }
            }
            // last
            {
                final var line = new String(chars, position, limit - position);
                position = limit;
                return line.split(separator,-1);
            }
        }
    }

    private static final class ReaderSupplier implements Supplier<String[]>
    {
        private final StringBuilder buffer = new StringBuilder();

        private final char[] chars;

        private int limit = 0;

        private int position = 0;

        private final Reader reader;

        private final String separator;

        ReaderSupplier (Reader reader, int capacity, char separator)
        {
            this.chars = new char[capacity];
            this.reader = reader;
            this.separator = String.valueOf(separator);
        }

        @Override
        public String[] get ()
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
                            return line.split(separator,-1);
                        }
                    }

                    buffer.append(chars,position,limit-position);
                    position = limit;
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            return buffer.isEmpty() ? null : buffer.toString().split(separator,-1);
        }
    }
}
