package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.*;

public final class AlgorithmIdentifierReader
{
    public static AlgorithmIdentifier read (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen)) // AlgorithmIdentifier.open
            throw new RuntimeException();

        if (! (reader.read() instanceof BerObjectIdentifier algorithm))
            throw new RuntimeException();

        skip0(reader); // parameters

        if (! (reader.read() instanceof BerClose)) // AlgorithmIdentifier.close
            throw new RuntimeException();

        return new AlgorithmIdentifier(algorithm,null);
    }

    static void skip0 (BerReader reader)
    {
        if (reader.read() instanceof BerOpen)
            skip1(reader);
    }

    static void skip1 (BerReader reader)
    {
        BerObject object;
        while (! ((object = reader.read()) instanceof BerClose))
            if (object instanceof BerOpen)
                skip1(reader);
    }
}