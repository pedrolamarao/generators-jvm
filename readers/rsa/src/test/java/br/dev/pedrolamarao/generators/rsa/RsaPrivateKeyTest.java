package br.dev.pedrolamarao.generators.rsa;

import br.dev.pedrolamarao.generators.ber.BerAbstractGenerator;
import br.dev.pedrolamarao.generators.ber.BerRunnableGenerator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RsaPrivateKeyTest
{
    @Test
    public void abstract_ () throws Exception
    {
        if (! (KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate() instanceof RSAPrivateCrtKey generated))
            throw new AssertionError("expected instanceof RSAPrivateCrtKey");

        final var encoded = new org.bouncycastle.asn1.pkcs.RSAPrivateKey(
                generated.getModulus(),
                generated.getPrivateExponent(),
                generated.getPrivateExponent(),
                generated.getPrimeP(),
                generated.getPrimeQ(),
                generated.getPrimeExponentP(),
                generated.getPrimeExponentQ(),
                generated.getCrtCoefficient()
            )
            .getEncoded();

        final var parsed = RsaPrivateKeyParser.parse(
            new BerAbstractGenerator(
                new ByteArrayInputStream( encoded )
            )
        );

        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
    }

    @Test
    public void runnable () throws Exception
    {
        if (! (KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate() instanceof RSAPrivateCrtKey generated))
            throw new AssertionError("expected instanceof RSAPrivateCrtKey");

        final var encoded = new org.bouncycastle.asn1.pkcs.RSAPrivateKey(
                generated.getModulus(),
                generated.getPrivateExponent(),
                generated.getPrivateExponent(),
                generated.getPrimeP(),
                generated.getPrimeQ(),
                generated.getPrimeExponentP(),
                generated.getPrimeExponentQ(),
                generated.getCrtCoefficient()
            )
            .getEncoded();

        final var parsed = RsaPrivateKeyParser.parse(
            new BerRunnableGenerator(
                new ByteArrayInputStream( encoded )
            )
        );

        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
    }
}
