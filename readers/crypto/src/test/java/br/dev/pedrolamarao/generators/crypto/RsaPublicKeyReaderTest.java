package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RsaPublicKeyReaderTest
{
    @Test
    public void abstract_ () throws Exception
    {
        if (! (KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic() instanceof RSAPublicKey generated))
            throw new AssertionError("expected instanceof RSAPublicKey");

        final var encoded = new org.bouncycastle.asn1.pkcs.RSAPublicKey(generated.getModulus(),generated.getPublicExponent()).getEncoded();

        final var parsed = RsaPublicKeyReader.read(
            new BerAbstractReader(
                new ByteArrayInputStream( encoded )
            )
        );

        assertThat( parsed.getPublicExponent(), equalTo( generated.getPublicExponent() ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
    }

    @Test
    public void runnable () throws Exception
    {
        if (! (KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic() instanceof RSAPublicKey generated))
            throw new AssertionError("expected instanceof RSAPublicKey");

        final var encoded = new org.bouncycastle.asn1.pkcs.RSAPublicKey(generated.getModulus(),generated.getPublicExponent()).getEncoded();

        final var parsed = RsaPublicKeyReader.read(
            new BerRunnableReader(
                new ByteArrayInputStream( encoded )
            )
        );

        assertThat( parsed.getPublicExponent(), equalTo( generated.getPublicExponent() ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
    }
}
