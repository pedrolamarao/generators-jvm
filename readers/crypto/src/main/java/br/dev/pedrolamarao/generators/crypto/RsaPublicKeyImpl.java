package br.dev.pedrolamarao.generators.crypto;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

record RsaPublicKeyImpl (BigInteger exponent, BigInteger modulus) implements RSAPublicKey
{
    @Override
    public BigInteger getPublicExponent ()
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