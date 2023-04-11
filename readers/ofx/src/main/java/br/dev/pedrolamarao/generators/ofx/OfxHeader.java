package br.dev.pedrolamarao.generators.ofx;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.US_ASCII;

public record OfxHeader (Map<String,String> properties) implements OfxObject
{
    static OfxParseResult<OfxHeader> parse (byte[] bytes, int remaining) throws IOException
    {
        final var properties = new HashMap<String,String>();
        final var reader = new BufferedReader( new StringReader( new String(bytes,US_ASCII) ) );
        String line = null;
        while ((line = reader.readLine()) != null) {
            final var property = line.split("\\:");
            if (property.length != 2) throw new RuntimeException("unexpected line at header");
            properties.put(property[0],property[1]);
        }
        return new OfxParseResult<OfxHeader>(new OfxHeader(properties),remaining);
    }

    static OfxParseResult<OfxHeader> parse (InputStream stream) throws IOException
    {
        final var buffer = new ByteArrayOutputStream();
        int byte_ = -1;
        int lf = 0;
        while ((byte_ = stream.read()) != -1) {
            if (byte_ == '<') {
                break;
            }
            else if (byte_ == '\n') {
                if (++lf == 2) break;
                buffer.write('\n');
                continue;
            }
            else if (byte_ == '\r') {
                byte_ = stream.read();
                if (byte_ == -1) {
                    break;
                }
                else if (byte_ == '\n') {
                    if (++lf == 2) break;
                    buffer.write('\n');
                    continue;
                }
            }

            lf = 0;
            buffer.write(byte_);
        }
        return parse(buffer.toByteArray(),byte_);
    }
}
