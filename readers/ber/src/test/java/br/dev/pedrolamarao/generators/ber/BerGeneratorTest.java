package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.Generator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyPairGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

public abstract class BerGeneratorTest
{
    abstract Generator<BerObject> create (InputStream stream);

    @Test
    public void keys () throws Exception
    {
        final var keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        final var subjectPublicKeyInfoReader = create( new ByteArrayInputStream( keys.getPublic().getEncoded() ) );
        // open: subjectPublicKeyInfo
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerOpen.class) );
        // open: subjectPublicKeyInfo.algorithm : AlgorithmIdentifier
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerOpen.class) );
        // algorithmIdentifier.algorithm : OBJECT IDENTIFIER
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerObjectIdentifier.class) );
        // algorithmIdentifier.parameters : ANY
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerNull.class) );
        // close: subjectPublicKeyInfo.algorithm : algorithmIdentifier
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerClose.class) );
        // subjectPublicKeyInfo.subjectPublicKey : BIT STRING
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerBytes.class) );
        // close: subjectPublicKeyInfo
        assertThat( subjectPublicKeyInfoReader.next(), isA(BerClose.class) );
        // finish
        assertThat( subjectPublicKeyInfoReader.next(), nullValue() );

        final var privateKeyInfoReader = create( new ByteArrayInputStream( keys.getPrivate().getEncoded() ) );
        // open: privateKeyInfo
        assertThat( privateKeyInfoReader.next(), isA(BerOpen.class) );
        // privateKeyInfo.version : INTEGER
        assertThat( privateKeyInfoReader.next(), isA(BerInteger.class) );
        // open: privateKeyInfo.privateKeyAlgorithm : AlgorithmIdentifier
        assertThat( privateKeyInfoReader.next(), isA(BerOpen.class) );
        // algorithmIdentifier.algorithm : OBJECT IDENTIFIER
        assertThat( privateKeyInfoReader.next(), isA(BerObjectIdentifier.class) );
        // algorithmIdentifier.parameters : ANY
        assertThat( privateKeyInfoReader.next(), isA(BerNull.class) );
        // close: privateKeyInfo.algorithm : AlgorithmIdentifier
        assertThat( privateKeyInfoReader.next(), isA(BerClose.class) );
        // privateKeyInfo.privateKey : OCTET STRING
        assertThat( privateKeyInfoReader.next(), isA(BerBytes.class) );
        // close: privateKeyInfo
        assertThat( privateKeyInfoReader.next(), isA(BerClose.class) );
        // finish
        assertThat( privateKeyInfoReader.next(), nullValue() );

    }
}
