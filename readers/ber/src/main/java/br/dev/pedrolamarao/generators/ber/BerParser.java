package br.dev.pedrolamarao.generators.ber;

import br.dev.pedrolamarao.generators.AbstractGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class BerParser
{
    public static void parse (InputStream stream, Consumer<BerObject> consumer) throws IOException
    {
        final int type = stream.read();
        if (type == -1) {
            return;
        }

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
            if (b0 == -1) {
                return;
            }

            if ((b0 & 0x80) == 0)
            {
                // definite short length
                length = b0;
            }
            else
            {
                if (b0 != 0x80)
                {
                    int tmp = 0;
                    for (int i = 0, j = (b0 & 0x7F); i != j; ++i) {
                        final int b1 = stream.read();
                        if (b1 == -1) {
                            return;
                        }
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

            final var object = switch (tag)
            {
                case 2 -> {
                    // #TODO
                    final byte[] bytes = new byte[length];
                    final int r0 = stream.read(bytes);
                    if (r0 == -1) yield null;
                    yield new BerInteger(bytes);
                }
                case 3, 4 -> {
                    final byte[] bytes = new byte[length];
                    final int r0 = stream.read(bytes);
                    if (r0 == -1) yield null;
                    yield new BerBytes(bytes);
                }
                case 5 -> new BerNull();
                case 6 -> {
                    // #TODO
                    final byte[] bytes = new byte[length];
                    final int r0 = stream.read(bytes);
                    if (r0 == -1) yield null;
                    yield new BerObjectIdentifier(bytes);
                }
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
