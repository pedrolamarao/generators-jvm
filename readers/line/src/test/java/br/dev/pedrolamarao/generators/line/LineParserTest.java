package br.dev.pedrolamarao.generators.line;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@Timeout(value=1,threadMode=SEPARATE_THREAD)
public abstract class LineParserTest
{
    abstract Iterator<String> parse (String text);

    final String text_1 = "foo,bar,1.0, ,\nmeh,duh,2.22,\nhmmm,hmmmmmmm,3.33\n";

    @Test
    public void test_1_1 ()
    {
        final var lines = parse(text_1);

        assertEquals( "foo,bar,1.0, ,", lines.next() );
        assertEquals( "meh,duh,2.22,", lines.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", lines.next() );
        assertNull( lines.next() );
    }

    @Test
    public void test_1_8 ()
    {
        final var reader = parse(text_1);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }

    @Test
    public void test_1_16 ()
    {
        final var reader = parse(text_1);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }

    @Test
    public void test_1_32 ()
    {
        final var reader = parse(text_1);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }

    final String text_2 = "foo,bar,1.0, ,\nmeh,duh,2.22,\nhmmm,hmmmmmmm,3.33";

    @Test
    public void test_2_1 ()
    {
        final var reader = parse(text_2);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }

    @Test
    public void test_2_8 ()
    {
        final var reader = parse(text_2);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }

    @Test
    public void test_2_16 ()
    {
        final var reader = parse(text_2);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }

    @Test
    public void test_2_32 ()
    {
        final var reader = parse(text_2);

        assertEquals( "foo,bar,1.0, ,", reader.next() );
        assertEquals( "meh,duh,2.22,", reader.next() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.next() );
        assertNull( reader.next() );
    }
}
