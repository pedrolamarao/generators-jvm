package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SubjectPublicKeyInfoReaderTest
{
    @Test
    public void abstract_ec () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("EC").generateKeyPair();
        final var generated = (ECPublicKey) keys.getPublic();
        final var encoded = generated.getEncoded();
        final var parsed = (ECPublicKey) SubjectPublicKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getW().getAffineX(), equalTo( generated.getW().getAffineX()) );
        assertThat( parsed.getW().getAffineY(), equalTo( generated.getW().getAffineY()) );
        assertThat( parsed.getParams(), equalTo( generated.getParams() ) );
    }

    @Test
    public void abstract_rsa () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        final var generated = (RSAPublicKey) keys.getPublic();
        final var encoded = generated.getEncoded();
        final var parsed = (RSAPublicKey) SubjectPublicKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
        assertThat( parsed.getPublicExponent(), equalTo( generated.getPublicExponent() ) );
    }

    @Test
    public void runnable_ec () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("EC").generateKeyPair();
        final var generated = (ECPublicKey) keys.getPublic();
        final var x = generated.getW().getAffineX().toByteArray();
        final var y = generated.getW().getAffineY().toByteArray();
        final var encoded = generated.getEncoded();
        final var parsed = (ECPublicKey) SubjectPublicKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getW().getAffineX(), equalTo( generated.getW().getAffineX()) );
        assertThat( parsed.getW().getAffineY(), equalTo( generated.getW().getAffineY()) );
        assertThat( parsed.getParams(), equalTo( generated.getParams() ) );
    }

    @Test
    public void runnable_rsa () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        final var generated = (RSAPublicKey) keys.getPublic();
        final var encoded = generated.getEncoded();
        final var parsed = (RSAPublicKey) SubjectPublicKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream(encoded) ) );
        assertThat( parsed.getModulus(), equalTo( generated.getModulus() ) );
        assertThat( parsed.getPublicExponent(), equalTo( generated.getPublicExponent() ) );
    }
}
