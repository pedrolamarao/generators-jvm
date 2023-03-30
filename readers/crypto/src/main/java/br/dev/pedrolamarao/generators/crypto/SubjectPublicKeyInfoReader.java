package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.*;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.util.Arrays;

public final class SubjectPublicKeyInfoReader
{
    static final byte[] rsa = { 42, -122, 72, -122, -9, 13, 1, 1, 1 };

    public static PublicKey read (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen))
            throw new RuntimeException();

        final var algorithmIdentifier = AlgorithmIdentifierReader.read(reader);

        if (! (reader.read() instanceof BerBytes subjectPublicKey))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose))
            throw new RuntimeException();

        if (Arrays.equals(algorithmIdentifier.algorithm().bytes(),rsa))
            return RsaPublicKeyReader.parse( new BerRunnableReader( new ByteArrayInputStream( subjectPublicKey.bytes() ) ) );
        else
            throw new RuntimeException();
    }
}