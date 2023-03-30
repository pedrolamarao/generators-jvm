package br.dev.pedrolamarao.generators.akp;

import br.dev.pedrolamarao.generators.ber.*;
import br.dev.pedrolamarao.generators.rsa.RsaPrivateKeyReader;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Arrays;

public final class PrivateKeyInfoReader
{
    static final byte[] rsa = { 42, -122, 72, -122, -9, 13, 1, 1, 1 };

    public static PrivateKey read (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen)) // privateKeyInfo.open
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger version)) // privateKeyInfo.version
            throw new RuntimeException();

        if (! version.asBigInteger().equals(BigInteger.ZERO))
            throw new RuntimeException("unsupported version");

        if (! (reader.read() instanceof BerOpen)) // privateKeyInfo.privateKeyAlgorithm.open
            throw new RuntimeException();

        if (! (reader.read() instanceof BerObjectIdentifier algorithm))
            throw new RuntimeException();

        skip0(reader);

        if (! (reader.read() instanceof BerClose)) // privateKeyInfo.privateKeyAlgorithm.close
            throw new RuntimeException();

        if (! (reader.read() instanceof BerBytes privateKey))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose)) // privateKeyInfo.close
            throw new RuntimeException();

        if (Arrays.equals(algorithm.bytes(),rsa))
            return RsaPrivateKeyReader.parse( new BerRunnableReader( new ByteArrayInputStream( privateKey.bytes() ) ) );
        else
            throw new RuntimeException();
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