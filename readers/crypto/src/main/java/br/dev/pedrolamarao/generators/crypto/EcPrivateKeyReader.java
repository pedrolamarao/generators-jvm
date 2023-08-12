package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.*;

import java.math.BigInteger;
import java.security.PrivateKey;

public final class EcPrivateKeyReader
{
    public static PrivateKey read (BerReader reader)
    {
        if (! (reader.get() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger version))
            throw new RuntimeException();

        if (! version.asBigInteger().equals(BigInteger.ONE))
            throw new RuntimeException("unsupported version");

        if (! (reader.get() instanceof BerBytes privateKey))
            throw new RuntimeException();

        final var optional0 = reader.get();
        if (! (optional0 instanceof BerBytes parameters)) {
            if (! (optional0 instanceof BerClose))
                throw new RuntimeException();
            return new EcPrivateKeyImpl(
                new BigInteger( 1, privateKey.bytes() ),
                null,
                null
            );
        }

        final var optional1 = reader.get();
        if (! (optional1 instanceof BerBits publicKey)) {
            if (! (optional1 instanceof BerClose))
                throw new RuntimeException();
            return new EcPrivateKeyImpl(
                new BigInteger( 1, privateKey.bytes() ),
                parameters,
                null
            );
        }

        if (! (reader.get() instanceof BerClose))
            throw new RuntimeException();

        return new EcPrivateKeyImpl(
            new BigInteger( 1, privateKey.bytes() ),
            parameters,
            publicKey
        );
    }
}
