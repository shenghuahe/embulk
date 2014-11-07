package org.quickload.standards;

import java.io.InputStream;
import org.quickload.buffer.Buffer;
import org.quickload.buffer.BufferAllocator;
import org.quickload.channel.BufferOutput;

public class BufferPlugins
{
    public static long transferInputStream(BufferAllocator bufferAllocator,
            InputStream input, BufferOutput output) throws PartialTransferException
    {
        long transferredSize = 0;
        try {
            while (true) {
                Buffer buffer = bufferAllocator.allocateBuffer(1024);
                int len = input.read(buffer.get());
                if (len < 0) {
                    break;
                } else if (len > 0) {
                    buffer.limit(len);
                    output.add(buffer);
                    transferredSize += len;
                }
            }
        } catch (Exception ex) {
            throw new PartialTransferException(ex, transferredSize);
        }
        return transferredSize;
    }
}
