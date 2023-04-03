package br.dev.pedrolamarao.generators.crypto;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.spec.ECPoint;

public final class EcPublicKeyParser
{
    public static PublicKey parse (byte[] bytes, int offset, int length)
    {
        final ECPoint point;
        if (bytes[offset] == 0x04) {
            final var half = length/2;
            final var x = new BigInteger(bytes,offset,half);
            final var y = new BigInteger(bytes,offset+half,half);
            point = new ECPoint(x,y);

        }
        else if (bytes[offset] == 0x02 || bytes[0] == 0x03)
            throw new RuntimeException("compressed encoding unsupported");
        else
            throw new RuntimeException("encoding unsupported");

        return new EcPublicKeyImpl(point);
    }
}
