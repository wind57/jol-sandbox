package org.wind57.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * Look at Sample1 on how to run this.
 *
 * The interesting thing here is that the VM tries to put fields in such a manner that the
 * retained size of an instance is as small as possible. As such "declared field order" !=
 * "final field order".
 *
 */
public class Sample3 {

    public static void main(String[] args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(Packing.class).toPrintable());
    }

    public static class Packing {
        boolean bo1, bo2;
        byte b1, b2;
        char c1, c2;
        double d1, d2;
        float f1, f2;
        int i1, i2;
        long l1, l2;
        short s1, s2;
    }

}
