package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerInteger;
import br.dev.pedrolamarao.generators.ber.BerOpen;
import br.dev.pedrolamarao.generators.ber.BerReader;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public final class RsaPrivateKeyReader
{
    public static RSAPrivateKey read(BerReader reader)
    {
        if (! (reader.get() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger version))
            throw new RuntimeException();

        if (! version.asBigInteger().equals(BigInteger.ZERO))
            throw new RuntimeException("unsupported version");

        if (! (reader.get() instanceof BerInteger modulus))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger publicExponent))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger privateExponent))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger prime1))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger prime2))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger exponent1))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger exponent2))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger coefficient))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerClose))
            throw new RuntimeException();

        return new RsaPrivateKeyImpl(privateExponent.asBigInteger(),modulus.asBigInteger());
    }
}
