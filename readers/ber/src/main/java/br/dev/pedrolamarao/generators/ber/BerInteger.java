package br.dev.pedrolamarao.generators.ber;

import java.math.BigInteger;

public record BerInteger (byte[] bytes) implements BerObject
{
    public BigInteger asBigInteger()
    {
        return new BigInteger(bytes);
    }
}
