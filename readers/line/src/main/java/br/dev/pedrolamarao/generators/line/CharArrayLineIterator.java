package br.dev.pedrolamarao.generators.line;

import java.util.Iterator;

final class CharArrayLineIterator implements Iterator<String>
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
    public boolean hasNext ()
    {
        return position < limit;
    }

    @Override
    public String next ()
    {
        if (position == limit) return null;
        for (int i = position, j = limit; i != j; ++i) {
            final char c = chars[i];
            if (c == '\r' || c == '\n') {
                final var line = new String(chars, position, i - position);
                position = i + 1;
                return line;
            }
        }
        // finish
        {
            final var line = new String(chars, position, limit - position);
            position = limit;
            return line;
        }
    }
}
