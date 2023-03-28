package br.dev.pedrolamarao.generators.rsa;

import br.dev.pedrolamarao.generators.Generator;
import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerInteger;
import br.dev.pedrolamarao.generators.ber.BerObject;
import br.dev.pedrolamarao.generators.ber.BerOpen;

import java.security.interfaces.RSAPublicKey;

public class RsaPublicKeyParser
{
    public static RSAPublicKey parse (Generator<BerObject> source)
    {
        if (! (source.next() instanceof BerOpen))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger modulus))
            throw new RuntimeException();

        if (! (source.next() instanceof BerInteger exponent))
            throw new RuntimeException();

        if (! (source.next() instanceof BerClose))
            throw new RuntimeException();

        return new RsaPublicKeyImpl(exponent.asBigInteger(),modulus.asBigInteger());
    }
}
