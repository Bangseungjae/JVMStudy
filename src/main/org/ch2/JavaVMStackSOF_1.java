package org.ch2;

/**
 * VM 매개변수: -Xss128k
 */
public class JavaVMStackSOF_1 {
    private int stackLength = 1;
    public void stackLink() {
        stackLength++;
        stackLink();
    }

    public static void main(String[] args) {
        JavaVMStackSOF_1 oom = new JavaVMStackSOF_1();
        try {
            oom.stackLink();
        } catch (Throwable e) {
            System.out.println("스택 길이: " + oom.stackLength);
            throw e;
        }
    }
}
