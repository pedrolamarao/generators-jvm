package br.dev.pedrolamarao.generators.ber;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public final class BerParser
{
    public static void parse (InputStream stream, Consumer<BerObject> consumer) throws IOException
    {
        final int type = stream.read();
        if (type == -1) throw new IOException();

        final int tag;
        {
            if ((type & 0x1F) != 0x1F)
            {
                // low tag
                tag = (type & 0x1F);
            }
            else
            {
                // #TODO: high tag
                throw new RuntimeException("high tag is unsupported");
            }
        }

        final int length;
        {
            final int b0 = stream.read();
            if (b0 == -1) throw new IOException();

            if ((b0 & 0x80) == 0)
            {
                // definite short length
                length = b0;
            }
            else
            {
                final int count = b0 & 0x7F;
                if (count != 0)
                {
                    // definite long length
                    final byte[] bytes = stream.readNBytes(count);
                    int tmp = 0;
                    for (var b1 : bytes) {
                        tmp <<= 8;
                        tmp |= (b1 & 0xFF);
                    }
                    length = tmp;
                }
                else
                {
                    // #TODO: indefinite length
                    throw new RuntimeException("indefinite length is unsupported");
                }
            }
        }

        if ((type & 0x20) == 0)
        {
            // primitive

            final byte[] bytes = stream.readNBytes(length);

            final var object = switch (tag)
            {
                case 2 -> new BerInteger(bytes);
                case 3 -> new BerBits(bytes);
                case 4 -> new BerBytes(bytes);
                case 5 -> new BerNull();
                case 6 -> new BerObjectIdentifier(bytes);
                default -> throw new RuntimeException("unsupported tag: " + tag);
            };

            consumer.accept(object);
        }
        else
        {
            // constructed

            consumer.accept(new BerOpen());

            final var bounded = new BoundedInputStream(stream,length);
            while (bounded.remaining() > 0) {
                parse(bounded,consumer);
            }

            consumer.accept(new BerClose());
        }
    }
}
