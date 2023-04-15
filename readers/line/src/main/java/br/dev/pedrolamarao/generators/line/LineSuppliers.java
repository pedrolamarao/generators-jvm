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
        return new ReaderSupplier(reader);
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

        private boolean hasNext = true;

        private final Reader reader;

        ReaderSupplier (Reader reader)
        {
            this.reader = reader;
        }

        @Override
        public String get ()
        {
            if (! hasNext) return null;

            try
            {
                int c = -1;
                while ((c = reader.read()) != -1)
                {
                    if (c == '\r' || c == '\n') {
                        final var line = buffer.toString();
                        buffer.setLength(0);
                        return line;
                    }
                    else {
                        buffer.append((char)c);
                    }
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }

            hasNext = false;

            if (! buffer.isEmpty()) {
                final var line = buffer.toString();
                buffer.setLength(0);
                return line;
            }

            return null;
        }
    }
}
