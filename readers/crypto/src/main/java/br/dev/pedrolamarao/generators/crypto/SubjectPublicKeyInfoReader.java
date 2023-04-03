package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerBits;
import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerOpen;
import br.dev.pedrolamarao.generators.ber.BerReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.PublicKey;
import java.util.Arrays;

import static br.dev.pedrolamarao.generators.crypto.AlgorithmIdentifier.EC;
import static br.dev.pedrolamarao.generators.crypto.AlgorithmIdentifier.RSA;

public final class SubjectPublicKeyInfoReader
{
    public static PublicKey read (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen))
            throw new RuntimeException();

        final var algorithmIdentifier = AlgorithmIdentifierReader.read(reader);

        if (! (reader.read() instanceof BerBits subjectPublicKey))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose))
            throw new RuntimeException();

        final var keyBytes = subjectPublicKey.bytes();
        if (keyBytes[0] != 0) throw new RuntimeException();

        final var algorithmBytes = algorithmIdentifier.algorithm().bytes();
        if (Arrays.equals(algorithmBytes,RSA))
            return RsaPublicKeyReader.read(
                create( reader.getClass(), new ByteArrayInputStream(keyBytes,1,keyBytes.length-1) )
            );
        else if (Arrays.equals(algorithmBytes,EC))
            return EcPublicKeyParser.parse(keyBytes,1,keyBytes.length-1);
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