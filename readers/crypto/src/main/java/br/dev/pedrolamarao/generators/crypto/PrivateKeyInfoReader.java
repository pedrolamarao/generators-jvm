package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.*;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Arrays;

public final class PrivateKeyInfoReader
{
    static final byte[] rsa = { 42, -122, 72, -122, -9, 13, 1, 1, 1 };

    public static PrivateKey read (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger version))
            throw new RuntimeException();

        if (! version.asBigInteger().equals(BigInteger.ZERO))
            throw new RuntimeException("unsupported version");

        final var algorithmIdentifier = AlgorithmIdentifierReader.read(reader);

        if (! (reader.read() instanceof BerBytes privateKey))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose))
            throw new RuntimeException();

        if (Arrays.equals(algorithmIdentifier.algorithm().bytes(),rsa))
            return RsaPrivateKeyReader.parse( new BerRunnableReader( new ByteArrayInputStream( privateKey.bytes() ) ) );
        else
            throw new RuntimeException();
    }
}