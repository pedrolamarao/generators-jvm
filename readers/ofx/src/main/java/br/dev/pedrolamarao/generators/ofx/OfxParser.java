package br.dev.pedrolamarao.generators.ofx;

import java.io.*;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.US_ASCII;

final class OfxParser
{
    public record Result<T> (T value, int remainder)
    {
    }

    static Result<OfxHeader> parseHeader (byte[] bytes, int remaining) throws IOException
    {
        final var properties = new HashMap<String,String>();
        final var reader = new BufferedReader( new StringReader( new String(bytes,US_ASCII) ) );
        String line = null;
        while ((line = reader.readLine()) != null) {
            final var property = line.split("\\:");
            if (property.length != 2) throw new RuntimeException("unexpected line at header");
            properties.put(property[0],property[1]);
        }
        return new Result<OfxHeader>(new OfxHeader(properties),remaining);
    }

    static Result<OfxHeader> parseHeader (InputStream stream) throws IOException
    {
        final var buffer = new ByteArrayOutputStream();
        int byte_ = -1;
        int eol = 0;
        while ((byte_ = stream.read()) != -1) {
            if (byte_ == '\n') {
                if (++eol == 2) break;
                buffer.write('\n');
            }
            else if (byte_ == '\r') {
                if ((byte_ = stream.read()) == -1) break;
                eol = (byte_ == '\n') ? (eol + 1) : 0;
                if (eol == 2) break;
                buffer.write('\r');
                buffer.write(byte_);
            }
            else if (eol == 1 && byte_ == '<') {
                break;
            }
            else {
                eol = 0;
                buffer.write(byte_);
            }
        }
        return parseHeader(buffer.toByteArray(),byte_);
    }
}
