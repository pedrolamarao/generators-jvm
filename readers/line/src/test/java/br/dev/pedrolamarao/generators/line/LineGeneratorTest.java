package br.dev.pedrolamarao.generators.line;

import br.dev.pedrolamarao.generators.Generator;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class LineGeneratorTest
{
    abstract Generator<String> create (Reader reader, int buffer);

    @Test
    public void test ()
    {
        final var text =
        """
foo,bar,1.0, ,
meh,duh,2.22,
        """;

        final var reader = create( new StringReader(text), 8 );

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertNull( reader.next() );
    }
}
