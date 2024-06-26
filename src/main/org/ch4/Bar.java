package org.ch4;

public class Bar {
    int a = 1;
    static int b = 2;

    public int sum(int c) {
        return a + b + c;
    }

    /**
     * java -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*Bar.sum org.ch4.Bar
     * @param args
     */
    public static void main(String[] args) {
        new Bar().sum(3);
    }
}
