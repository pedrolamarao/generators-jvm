package br.dev.pedrolamarao.generators.rsa;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RsaPrivateKeyReaderTest
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

        final var parsed = RsaPrivateKeyReader.parse(
            new BerAbstractReader(
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

        final var parsed = RsaPrivateKeyReader.parse(
            new BerRunnableReader(
                new ByteArrayInputStream( encoded )
            )
        );

        assertThat( parsed.getPrivateExponent(), equalTo( generated.getPrivateExponent() ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
    }
}
