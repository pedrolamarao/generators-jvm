package br.dev.pedrolamarao.generators.crypto;

import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

record EcPublicKeyImpl (ECPoint point) implements ECPublicKey
{
    @Override
    public ECPoint getW ()
    {
        return point;
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
