package org.wind57.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * look at how to run in Sample1.
 *
 * The interesting point here is that the padding was inserted between the headers and the field "l".
 * Things are very different when we have an int, no padding at all.
 *
 */
public class Sample2 {

    public static void main(String[] args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(LongAlignment.class).toPrintable());
        System.out.println(ClassLayout.parseClass(IntAlignment.class).toPrintable());
    }

    private static class LongAlignment {
        long l;
    }

    private static class IntAlignment {
        int i;
    }

}
