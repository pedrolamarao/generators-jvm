package br.dev.pedrolamarao.generators.rsa;

import br.dev.pedrolamarao.generators.Generator;
import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerInteger;
import br.dev.pedrolamarao.generators.ber.BerObject;
import br.dev.pedrolamarao.generators.ber.BerOpen;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public class RsaPrivateKeyParser
{
    public static RSAPrivateKey parse (Generator<BerObject> source)
    {
        if (! (source.next() instanceof BerOpen))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger version))
            throw new RuntimeException();

        if (! version.asBigInteger().equals(BigInteger.ZERO))
            throw new RuntimeException("unsupported version");

        if (! (source.next() instanceof BerInteger modulus))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger publicExponent))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger privateExponent))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger prime1))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger prime2))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger exponent1))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger exponent2))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger coefficient))
            throw new RuntimeException();

        if (! (source.next() instanceof BerClose))
            throw new RuntimeException();

        return new RsaPrivateKeyImpl(privateExponent.asBigInteger(),modulus.asBigInteger());
    }
}
