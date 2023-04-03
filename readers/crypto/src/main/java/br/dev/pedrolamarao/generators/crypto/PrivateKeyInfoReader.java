package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Arrays;

import static br.dev.pedrolamarao.generators.crypto.AlgorithmIdentifier.EC;
import static br.dev.pedrolamarao.generators.crypto.AlgorithmIdentifier.RSA;

public final class PrivateKeyInfoReader
{
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

        final var algorithmBytes = algorithmIdentifier.algorithm().bytes();
        if (Arrays.equals(algorithmBytes,RSA))
            return RsaPrivateKeyReader.read(
                create( reader.getClass(), new ByteArrayInputStream(privateKey.bytes()) )
            );
        else if (Arrays.equals(algorithmBytes,EC))
            return EcPrivateKeyReader.read(
                create( reader.getClass(), new ByteArrayInputStream(privateKey.bytes()) )
            );
        else
            throw new RuntimeException();
    }

    static BerReader create (Class<? extends BerReader> type, InputStream stream)
    {
        try {
            return type.getConstructor(InputStream.class).newInstance(stream);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}