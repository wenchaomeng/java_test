package javabasic.collection.agrona.mymap;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 07, 2018
 */
public class StringUnsafe {

    public final static Unsafe unsafe;
    private static final long valueOffset;
    private static Field valueField;

    static {
        try {
            unsafe = getUnsafe();
        } catch (Exception e) {
            throw new IllegalStateException("error get unsafe", e);
        }

        try {
            valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);
            valueOffset = unsafe.objectFieldOffset(valueField);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("error get String value offset", e);
        }
    }
    public static void setStringValueField(String str, Object value) {
        unsafe.putObjectVolatile(str, valueOffset, value);
    }


    public static Object getStringValueField(String str) {

        try {
            return valueField.get(str);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("error get value field from" + str, e);
        }
    }

    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {

        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        return (Unsafe) unsafeField.get(null);
    }

}
