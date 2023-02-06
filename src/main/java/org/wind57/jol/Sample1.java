package org.wind57.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * 1) I am first building the project: "gradle clean build"
 * 2) sudo java -cp build/libs/jol-sandbox-1.0-SNAPSHOT.jar:/Users/wind57/.m2/repository/org/openjdk/jol/jol-core/0.17-SNAPSHOT/jol-core-0.17-SNAPSHOT.jar -Djdk.attach.allowAttachSelf -Djol.tryWit
 * hSudo=true org.wind57.jol.Sample1
 *
 * 3) A few things to note from the output: fields headers take 12 bytes, boolean b takes 1 byte (yup! 1 byte, not 1 bit), there are 3 bytes of padding
 * and a total instance size of 16 bytes.
 *
 *    Each java instance has two headers, klass and oops header, they server different purposes (not going to go into details here),
 *    but the point is that they can be "compressed", meaning they can take less than the native machine size.
 *
 *    So if I am running on a 64-bit CPU, we would expect that both headers take 16 bytes (2 * 64 bits), but they take
 *    12 bytes (8 + 4), this is because one of the headers is "compressed" (this compression is achieved because all objects are aligned to a byte in java
 *    and this means that the last 3 bits are _always_ zero. more details: https://shipilev.net/jvm/anatomy-quarks/23-compressed-references/ )
 *    Things can get very tricky from here:
 *
 *    - if you are running with a large heap that can not be addressed via compressed headers, the header is going to be 8 bytes, not 4;
 *      _per_every_single_instance_. It means we will increase the overall size of each and every single instance, simply because we have a bigger
 *      heap.
 *
 *    - it can be disabled/enabled via flags: UseCompressedClassPointers and UseCompressedOops
 *
 *    - changing the alignment might bring surprises also: ObjectAlignmentInBytes
 *
 *    - it can differ from VM to VM : https://stackoverflow.com/questions/62879157/usecompressedoops-usecompressedclasspointers-in-jdk-13-and-jdk-15
 *
 *
 *    The bottom-line is that we need to know exact invariants that we have to tell how much an actual instance weights.
 */
public class Sample1 {

    public static void main(String[] args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(WithASingleBoolean.class).toPrintable());
    }

    private static class WithASingleBoolean {
        boolean b;
    }

}
