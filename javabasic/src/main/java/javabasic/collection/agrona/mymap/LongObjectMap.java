package javabasic.collection.agrona.mymap;

/**
 * @author leoliang
 */
public class LongObjectMap {

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    private transient volatile Node[] table;
    private int bucketCount;

    private static final sun.misc.Unsafe U;
    private static final long ABASE;
    private static final int ASHIFT;

    static {
        try {
            U = StringUnsafe.unsafe;
            Class<?> k = LongObjectMap.class;
            Class<?> ak = Node[].class;
            ABASE = U.arrayBaseOffset(ak);
            int scale = U.arrayIndexScale(ak);
            if ((scale & (scale - 1)) != 0)
                throw new Error("data type scale not a power of two");
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (Exception e) {
            throw new Error(e);
        }
    }


    public LongObjectMap(int bucketCount) {
        this.bucketCount = bucketCount;
        this.table = new Node[bucketCount];

    }


    public Object get(long key) {
        Node e = null;
        int h = spread((int)key);
        if ((e = tabAt(table, (bucketCount - 1) & h)) != null) {

            if (e.getKey() == key)
                return e;
        }
        while ((e = e.getNext()) != null) {
            if (e.getKey() == key) {
                return e;
            }
        }
        return null;
    }


    static final int spread(int h) {
//        return (h ^ (h >>> 16)) & HASH_BITS;
//        int h = (int)(value ^ (value >>> 32));
        return (int) ((h ^ (h >>> 16)) & HASH_BITS);
    }

    static final Node tabAt(Node[] tab, int i) {
        return (Node) U.getObjectVolatile(tab, ((long) i << ASHIFT) + ABASE);
    }

    static final <K, V> boolean casTabAt(Node[] tab, int i,
                                         Node c, Node v) {
        return U.compareAndSwapObject(tab, ((long) i << ASHIFT) + ABASE, c, v);
    }


    public void put(long key, Node value) {
        if (value == null) throw new NullPointerException();
        int hash = spread((int)key);
        for (Node[] tab = table; ; ) {
            Node f;
            int n, i;
            n = table.length;
            if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                if (casTabAt(tab, i, null,
                        value))
                    break;                   // no lock when adding to empty bin
            } else {
//                synchronized (f) {
//                    if (tabAt(tab, i) == f) {

                value.setNext(f);
                if (casTabAt(tab, i, f, value))
                    break;
            }

//                }
//            }
        }
    }

}
