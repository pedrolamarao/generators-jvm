package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerBits;
import br.dev.pedrolamarao.generators.ber.BerBytes;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;

record EcPrivateKeyImpl (BigInteger privateKey, BerBytes parameters, BerBits publicKey) implements ECPrivateKey
{
    @Override
    public BigInteger getS ()
    {
        return privateKey;
    }

    @Override
    public String getAlgorithm ()
    {
        return "EC";
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
    public ECParameterSpec getParams ()
    {
        return null;
    }
}
