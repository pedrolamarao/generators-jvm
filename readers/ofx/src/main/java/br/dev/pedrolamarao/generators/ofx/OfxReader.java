package br.dev.pedrolamarao.generators.ofx;

import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.InputStream;

public class OfxReader extends RunnableGenerator<OfxObject>
{
    private OfxReader (Runnable runnable)
    {
        super(runnable);
    }

    public static OfxReader from (InputStream stream)
    {
        return new OfxReader(() -> run(stream));
    }

    private static void run (InputStream stream)
    {
        try
        {
            final var header = OfxParser.parseHeader(stream);
            RunnableGenerator.yield(header.value());

            final var properties = header.value().properties();
            if (! properties.containsKey("OFXHEADER")) throw new RuntimeException("unexpected header: OFXHEADER not found");
            final int version = Integer.parseInt( properties.get("OFXHEADER") );
            if (version != 100) throw new RuntimeException("unsupported version: " + version);

            // #TODO: parse OFX

            RunnableGenerator.yield(null);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
