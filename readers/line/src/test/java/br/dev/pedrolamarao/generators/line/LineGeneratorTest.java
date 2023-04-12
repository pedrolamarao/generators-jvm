package br.dev.pedrolamarao.generators.line;

import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@Timeout(value=1,threadMode=SEPARATE_THREAD)
public abstract class LineGeneratorTest extends LineTest
{
    abstract LineReader create (Reader reader, int buffer);

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
