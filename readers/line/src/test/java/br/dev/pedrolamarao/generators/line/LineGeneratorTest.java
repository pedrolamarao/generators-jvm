package br.dev.pedrolamarao.generators.line;

import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@Timeout(value=1,threadMode=SEPARATE_THREAD)
public abstract class LineGeneratorTest
{
    abstract LineReader create (Reader reader, int buffer);

    final String text_1 = "foo,bar,1.0, ,\nmeh,duh,2.22,\nhmmm,hmmmmmmm,3.33\n";

    @Test
    public void test_1_1 ()
    {
        final var reader = create( new StringReader(text_1), 1 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    @Test
    public void test_1_8 ()
    {
        final var reader = create( new StringReader(text_1), 8 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    @Test
    public void test_1_16 ()
    {
        final var reader = create( new StringReader(text_1), 16 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    @Test
    public void test_1_32 ()
    {
        final var reader = create( new StringReader(text_1), 32 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    final String text_2 = "foo,bar,1.0, ,\nmeh,duh,2.22,\nhmmm,hmmmmmmm,3.33";

    @Test
    public void test_2_1 ()
    {
        final var reader = create( new StringReader(text_2), 1 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    @Test
    public void test_2_8 ()
    {
        final var reader = create( new StringReader(text_2), 8 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    @Test
    public void test_2_16 ()
    {
        final var reader = create( new StringReader(text_2), 16 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }

    @Test
    public void test_2_32 ()
    {
        final var reader = create( new StringReader(text_2), 32 );

        assertEquals( "foo,bar,1.0, ,", reader.read() );
        assertEquals( "meh,duh,2.22,", reader.read() );
        assertEquals( "hmmm,hmmmmmmm,3.33", reader.read() );
        assertNull( reader.read() );
    }
}
