package br.dev.pedrolamarao.generators.rsa;

import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerInteger;
import br.dev.pedrolamarao.generators.ber.BerOpen;
import br.dev.pedrolamarao.generators.ber.BerReader;

import java.security.interfaces.RSAPublicKey;

public final class RsaPublicKeyReader
{
    public static RSAPublicKey parse (BerReader reader)
    {
        if (! (reader.read() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger modulus))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerInteger exponent))
            throw new RuntimeException();

        if (! (reader.read() instanceof BerClose))
            throw new RuntimeException();

        return new RsaPublicKeyImpl(exponent.asBigInteger(),modulus.asBigInteger());
    }
}
