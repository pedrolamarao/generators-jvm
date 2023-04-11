package br.dev.pedrolamarao.generators.ofx;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OfxReaderTest
{
    @Test
    public void inter_20220721 () throws IOException
    {
        try (var stream = OfxReaderTest.class.getResourceAsStream("/inter-20220721.ofx"))
        {
            assert stream != null;
            final var reader = OfxReader.from(stream);
            assertThat(reader.read()).isInstanceOf(OfxHeader.class);
            assertThat(reader.read()).isNull();
        }
    }

    @Test
    public void nubank_20220721 () throws IOException
    {
        try (var stream = OfxReaderTest.class.getResourceAsStream("/nubank-20220721.ofx"))
        {
            assert stream != null;
            final var reader = OfxReader.from(stream);
            assertThat(reader.read()).isInstanceOf(OfxHeader.class);
            assertThat(reader.read()).isNull();
        }
    }
}
