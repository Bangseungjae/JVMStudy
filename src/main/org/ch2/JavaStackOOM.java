package org.ch2;

public class JavaStackOOM {
    private void dontStop() {
        while (true) {}
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() -> dontStop());
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaStackOOM javaStackOOM = new JavaStackOOM();
        javaStackOOM.stackLeakByThread();
    }
}
