package br.dev.pedrolamarao.generators.csv;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvReaderTest
{
    @Test
    public void test ()
    {
        final var text =
        """
foo,bar,1.0, ,
meh,duh,2.22,
        """;

        final var reader = new CsvReader( new StringReader(text) );

        assertEquals( "foo", reader.next() );
        assertEquals( "bar", reader.next() );
        assertEquals( "1.0", reader.next() );
        assertEquals( " ", reader.next() );
        assertEquals( "", reader.next() );
        assertEquals( "meh", reader.next() );
        assertEquals( "duh", reader.next() );
        assertEquals( "2.22", reader.next() );
    }
}
