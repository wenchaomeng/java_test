package javabasic.netty.arenatest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PoolArenaMetric;
import io.netty.buffer.PoolChunkListMetric;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author wenchao.meng
 *         <p>
 *         Jul 03, 2018
 */
public class ArenaTest {

    @Test
    public void testFreePoolChunk() {

        int chunkSize = 16 * 1024 * 1024;
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true, 1, 0, 8192, 11, 0, 0, 0);
        ByteBuf buffer = allocator.heapBuffer(chunkSize);
        List<PoolArenaMetric> arenas = allocator.heapArenas();
        assertEquals(1, arenas.size());
        List<PoolChunkListMetric> lists = arenas.get(0).chunkLists();
        assertEquals(6, lists.size());

        assertFalse(lists.get(0).iterator().hasNext());
        assertFalse(lists.get(1).iterator().hasNext());
        assertFalse(lists.get(2).iterator().hasNext());
        assertFalse(lists.get(3).iterator().hasNext());
        assertFalse(lists.get(4).iterator().hasNext());

        // Must end up in the 6th PoolChunkList
        assertTrue(lists.get(5).iterator().hasNext());
        assertTrue(buffer.release());

        // Should be completely removed and so all PoolChunkLists must be empty
        assertFalse(lists.get(0).iterator().hasNext());
        assertFalse(lists.get(1).iterator().hasNext());
        assertFalse(lists.get(2).iterator().hasNext());
        assertFalse(lists.get(3).iterator().hasNext());
        assertFalse(lists.get(4).iterator().hasNext());
        assertFalse(lists.get(5).iterator().hasNext());
    }}
