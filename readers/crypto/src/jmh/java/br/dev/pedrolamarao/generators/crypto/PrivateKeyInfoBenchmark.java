package br.dev.pedrolamarao.generators.crypto;

import br.dev.pedrolamarao.generators.ber.BerAbstractReader;
import br.dev.pedrolamarao.generators.ber.BerRunnableReader;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PrivateKeyInfoBenchmark
{
    static final SecureRandom secureRandom = new SecureRandom( new byte[0] );

    @State(Scope.Benchmark)
    public static class Common
    {
        byte[] data;

        @Param({"1024","2048","4096"})
        int size;

        @Setup(Level.Trial)
        public void setup () throws Exception
        {
            final var generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(size,secureRandom);
            data = generator.generateKeyPair().getPrivate().getEncoded();
        }
    }

    @Benchmark
    public PrivateKey abstractGenerator (Blackhole hole, Common it)
    {
        final var key = (RSAPrivateKey) PrivateKeyInfoReader.read( new BerAbstractReader( new ByteArrayInputStream( it.data ) ) );
        hole.consume(key.getPrivateExponent());
        return key;
    }

    @Benchmark
    public PrivateKey jca (Blackhole hole, Common it) throws Exception
    {
        final var key = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate( new PKCS8EncodedKeySpec( it.data.clone() ) );
        hole.consume(key.getPrivateExponent());
        return key;
    }

    @Benchmark
    public PrivateKey runnableGenerator (Blackhole hole, Common it)
    {
        final var key = (RSAPrivateKey) PrivateKeyInfoReader.read( new BerRunnableReader( new ByteArrayInputStream( it.data ) ) );
        hole.consume(key.getPrivateExponent());
        return key;
    }
}
