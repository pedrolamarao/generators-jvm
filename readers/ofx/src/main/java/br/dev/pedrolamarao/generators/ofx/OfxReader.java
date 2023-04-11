package br.dev.pedrolamarao.generators.ofx;

import br.dev.pedrolamarao.generators.RunnableGenerator;

import java.io.IOException;
import java.io.InputStream;

public class OfxReader
{
    private final RunnableGenerator<OfxObject> generator;

    private OfxReader (RunnableGenerator<OfxObject> generator)
    {
        this.generator = generator;
    }

    public static OfxReader from (InputStream stream)
    {
        return new OfxReader( new RunnableGenerator<>(() -> run(stream)) );
    }

    public OfxObject read ()
    {
        return generator.next();
    }

    private static void run (InputStream stream)
    {
        try
        {
            final var headerParsed = OfxHeader.parse(stream);
            final var header = headerParsed.value();
            int byte_ = headerParsed.remaining();
            RunnableGenerator.yield(header);

            if (! header.properties().containsKey("OFXHEADER")) throw new RuntimeException("unexpected header: OFXHEADER not found");
            final int version = Integer.parseInt( header.properties().get("OFXHEADER") );
            if (version != 100) throw new RuntimeException("unsupported version: " + version);

            // #TODO: parse OFX

            RunnableGenerator.yield(null);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
