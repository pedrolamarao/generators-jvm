package br.dev.pedrolamarao.generators.line;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.function.Consumer;

public class LineParser
{
    public static void parse (Reader reader, char[] buffer, Consumer<CharSequence> consumer) throws IOException
    {
        final var remainder = new StringBuilder();

        while (true)
        {
            final int limit = reader.read(buffer,0,buffer.length);
            if (limit == -1) break;

            final int first;
            if (remainder.isEmpty()) {
                first = 0;
            }
            else {
                final int position = find(buffer,0,limit,'\n');
                if (position == -1) {
                    remainder.append(buffer,0,limit);
                    continue;
                }
                else {
                    remainder.append(buffer,0,position);
                    consumer.accept(remainder.toString());
                    remainder.setLength(0);
                    first = position + 1;
                }
            }

            int current = first;
            while (current < limit) {
                final int position = find(buffer,current,limit,'\n');
                if (position == -1) break;
                consumer.accept( CharBuffer.wrap(buffer,current,position-current) );
                current = position + 1;
            }

            if (current < limit) {
                remainder.append(buffer, current, limit - current);
            }
        }

        if (! remainder.isEmpty())
            consumer.accept(remainder.toString());
    }

    static int find (char[] buffer, int position, int limit, char target)
    {
        for (int i = position, j = limit; i != j; ++i)
            if (buffer[i] == target)
                return i;
        return -1;
    }
}
