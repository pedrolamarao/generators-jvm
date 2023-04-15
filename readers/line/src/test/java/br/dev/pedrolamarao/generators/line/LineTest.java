package br.dev.pedrolamarao.generators.line;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@Timeout(value=1,threadMode=SEPARATE_THREAD)
public abstract class LineTest
{
    abstract Supplier<String> parse (String text, int capacity);

    final String text_1 = "foo,bar,1.0, ,\nmeh,duh,2.22,\nhmmm,hmmmmmmm,3.33\n";

    final String text_2 = "foo,bar,1.0, ,\nmeh,duh,2.22,\nhmmm,hmmmmmmm,3.33";

    @Test
    public void test_1_1 ()
    {
        final var lines = parse(text_1,1);

        assertEquals( "foo,bar,1.0, ,", lines.get() );
        assertEquals( "meh,duh,2.22,", lines.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", lines.get() );
        assertNull( lines.get() );
    }

    @Test
    public void test_1_8 ()
    {
        final var reader = parse(text_1,8);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }

    @Test
    public void test_1_16 ()
    {
        final var reader = parse(text_1,16);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }

    @Test
    public void test_1_32 ()
    {
        final var reader = parse(text_1,32);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }

    @Test
    public void test_2_1 ()
    {
        final var reader = parse(text_2,1);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }

    @Test
    public void test_2_8 ()
    {
        final var reader = parse(text_2,8);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }

    @Test
    public void test_2_16 ()
    {
        final var reader = parse(text_2,16);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }

    @Test
    public void test_2_32 ()
    {
        final var reader = parse(text_2,32);

        assertEquals( "foo,bar,1.0, ,", reader.get() );
        assertEquals( "meh,duh,2.22,", reader.get() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.get() );
        assertNull( reader.get() );
    }
}
