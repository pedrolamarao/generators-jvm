package br.dev.pedrolamarao.generators.rsa;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public record RsaPrivateKeyImpl(BigInteger exponent, BigInteger modulus) implements RSAPrivateKey
{
    @Override
    public BigInteger getPrivateExponent ()
    {
        return exponent;
    }

    @Override
    public String getAlgorithm ()
    {
        return "RSA";
    }

    @Override
    public String getFormat ()
    {
        return null;
    }

    @Override
    public byte[] getEncoded ()
    {
        return null;
    }

    @Override
    public BigInteger getModulus ()
    {
        return modulus;
    }
}
