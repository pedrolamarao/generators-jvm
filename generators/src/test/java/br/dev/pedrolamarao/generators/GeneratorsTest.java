package br.dev.pedrolamarao.generators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorsTest
{
    @Test
    public void iterator ()
    {
        final var generator = new AbstractGenerator<Integer>()
        {
            protected void run ()
            {
                int i = 0;
                while (true)
                    this.yield( i++ );
            }
        };

        final var unlimited = Generators.iterator(generator);

        final var limited = Generators.iterator(generator, 1000);

        while (unlimited.hasNext() && limited.hasNext()) {
            assertEquals( unlimited.next(), limited.next() );
        }
    }
}
