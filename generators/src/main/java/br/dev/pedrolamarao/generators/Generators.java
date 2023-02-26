package br.dev.pedrolamarao.generators;

import java.util.Iterator;
import java.util.Objects;

public final class Generators
{
    public static <T> Iterator<T> iterator (Generator<T> generator)
    {
        return new Iterator<T>()
        {
            public boolean hasNext () { return true; }
            public T next () { return generator.next(); }
        };
    }

    public static <T> Iterator<T> iterator (Generator<T> generator, T sentinel)
    {
        return new Iterator<T>()
        {
            T next = generator.next();
            public boolean hasNext () {
                return Objects.equals(next,sentinel);
            }
            public T next () {
                final var current = next;
                next = generator.next();
                return current;
            }
        };
    }
}
