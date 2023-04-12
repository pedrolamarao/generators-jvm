package br.dev.pedrolamarao.generators.line;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.function.Consumer;

public class LineParser
{
    public static void parse (char[] chars, Consumer<String> consumer)
    {
        final var line = new StringBuilder();

        int byte_ = -1;
        for (int i = 0, j = chars.length; i != j; ++i) {
            byte_ = chars[i];
            if (byte_ == '\n') {
                consumer.accept( line.toString() );
                line.setLength(0);
            }
            else if (byte_ == '\r') {
                if (++i == j) break;
                byte_ = chars[i];
                if (byte_ == '\n') {
                    consumer.accept( line.toString() );
                    line.setLength(0);
                }
                else {
                    line.append('\r');
                    line.append((char)byte_);
                }
            }
            else {
                line.append((char)byte_);
            }
        }

        if (! line.isEmpty()) consumer.accept( line.toString() );
    }

    public static void parse (Reader reader, Consumer<String> consumer) throws IOException
    {
        final var line = new StringBuilder();

        int byte_ = -1;
        while ((byte_ = reader.read()) != -1) {
            if (byte_ == '\n') {
                consumer.accept( line.toString() );
                line.setLength(0);
            }
            else if (byte_ == '\r') {
                if ((byte_ = reader.read()) == -1) break;
                if (byte_ == '\n') {
                    consumer.accept( line.toString() );
                    line.setLength(0);
                }
                else {
                    line.append('\r');
                    line.append((char)byte_);
                }
            }
            else {
                line.append((char)byte_);
            }
        }

        if (! line.isEmpty()) consumer.accept( line.toString() );
    }

    public static void parseWithMarkReset (char[] chars, Consumer<String> consumer)
    {
        int mark = 0;
        for (int i = 0, j = chars.length; i != j; ++i) {
            final char c = chars[i];
            if (c == '\n') {
                final int length = i - mark;
                consumer.accept( new String(chars,mark,length) );
                mark = i + 1;
            }
            else if (c == '\r') {
                if (++i == j) break;
                final char cc = chars[i];
                if (cc == '\n') {
                    final int length = i - mark - 1;
                    consumer.accept( new String(chars,mark,length) );
                    mark = i + 1;
                }
            }
        }

        if (mark < chars.length) {
            final int length = chars.length - mark;
            consumer.accept( new String(chars,mark,length) );
        }
    }

    public static void parseWithMarkReset (Reader reader, Consumer<String> consumer) throws IOException
    {
        if (! reader.markSupported()) throw new IllegalArgumentException();

        char[] line = new char[16];

        reader.mark(Integer.MAX_VALUE);
        int count = 0;
        int byte_ = -1;
        while ((byte_ = reader.read()) != -1) {
            if (byte_ == '\n') {
                final int length = count+1;
                line = (line.length >= length) ? line : new char[length];
                reader.reset();
                final int read = reader.read(line,0,length);
                assert read == length;
                reader.mark(Integer.MAX_VALUE);
                consumer.accept(new String(line,0,count));
                count = 0;
            }
            else if (byte_ == '\r') {
                if ((byte_ = reader.read()) == -1) break;
                if (byte_ == '\n') {
                    final int length = count+2;
                    line = (line.length >= length) ? line : new char[length];
                    reader.reset();
                    final int read = reader.read(line,0,length);
                    assert read == length;
                    reader.mark(Integer.MAX_VALUE);
                    consumer.accept(new String(line,0,count));
                    count = 0;
                }
                else {
                    count += 2;
                }
            }
            else {
                count += 1;
            }
        }

        if (count > 0) {
            line = (line.length >= count) ? line : new char[count];
            reader.reset();
            final int read = reader.read(line,0,count);
            assert read == count;
            consumer.accept(new String(line,0,count));
        }
    }

    public static void parse (InputStream stream, Consumer<byte[]> consumer) throws IOException
    {
        final var buffer = new ByteArrayOutputStream();

        int byte_ = -1;
        while ((byte_ = stream.read()) != -1) {
            if (byte_ == '\n') {
                consumer.accept( buffer.toByteArray() );
                buffer.reset();
            }
            else if (byte_ == '\r') {
                if ((byte_ = stream.read()) == -1) break;
                if (byte_ == '\n') {
                    consumer.accept( buffer.toByteArray() );
                    buffer.reset();
                }
                else {
                    buffer.write('\r');
                    buffer.write(byte_);
                }
            }
            else {
                buffer.write(byte_);
            }
        }

        if (buffer.size() != 0) consumer.accept( buffer.toByteArray() );
    }
}
