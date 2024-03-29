package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerClose;
import br.dev.pedrolamarao.generators.ber.BerInteger;
import br.dev.pedrolamarao.generators.ber.BerOpen;
import br.dev.pedrolamarao.generators.ber.BerReader;

import java.security.interfaces.RSAPublicKey;

public final class RsaPublicKeyReader
{
    public static RSAPublicKey read(BerReader reader)
    {
        if (! (reader.get() instanceof BerOpen))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger modulus))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerInteger exponent))
            throw new RuntimeException();

        if (! (reader.get() instanceof BerClose))
            throw new RuntimeException();

        return new RsaPublicKeyImpl(exponent.asBigInteger(),modulus.asBigInteger());
    }
}
