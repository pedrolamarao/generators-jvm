package br.dev.pedrolamarao.generators.line;

import java.util.function.Supplier;

final class CharArrayLineIterator implements Supplier<String>
{
    private final char[] chars;

    private int position;

    private final int limit;

    CharArrayLineIterator (char[] chars, int position, int limit)
    {
        this.chars = chars;
        this.position = position;
        this.limit = limit;
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
