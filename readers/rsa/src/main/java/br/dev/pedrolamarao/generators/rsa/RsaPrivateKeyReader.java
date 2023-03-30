package br.dev.pedrolamarao.generators.rsa;

import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerInteger;
import br.dev.pedrolamarao.generators.ber.BerOpen;
import br.dev.pedrolamarao.generators.ber.BerReader;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public final class RsaPrivateKeyReader
{
    public static RSAPrivateKey parse (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger version))
            throw new RuntimeException();

        if (! version.asBigInteger().equals(BigInteger.ZERO))
            throw new RuntimeException("unsupported version");

        if (! (reader.read() instanceof BerInteger modulus))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger publicExponent))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger privateExponent))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger prime1))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger prime2))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger exponent1))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger exponent2))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger coefficient))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose))
            throw new RuntimeException();

        return new RsaPrivateKeyImpl(privateExponent.asBigInteger(),modulus.asBigInteger());
    }
}
