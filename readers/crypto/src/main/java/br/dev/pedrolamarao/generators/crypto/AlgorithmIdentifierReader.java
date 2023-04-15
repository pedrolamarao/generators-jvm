package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.*;

public final class AlgorithmIdentifierReader
{
    public static AlgorithmIdentifier read (BerReader reader)
    {
        if (! (reader.get() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerObjectIdentifier algorithm))
            throw new RuntimeException();

        skip0(reader); // parameters

        if (! (reader.get() instanceof BerClose))
            throw new RuntimeException();

        return new AlgorithmIdentifier(algorithm,null);
    }

    static void skip0 (BerReader reader)
    {
        if (reader.get() instanceof BerOpen)
            skip1(reader);
    }

    static void skip1 (BerReader reader)
    {
        BerObject object;
        while (! ((object = reader.get()) instanceof BerClose))
            if (object instanceof BerOpen)
                skip1(reader);
    }
}