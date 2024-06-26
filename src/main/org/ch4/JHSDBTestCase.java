package org.ch4;

/**
 * VM 매개 변수: -Xmx10m -XX:+UseSerialGC -XX:-UseCompressedOops
 *
 * staticObj, instanceObj, localObj는 어디에 저장될까?
 */
public class JHSDBTestCase {
    static class Test {
        static ObjectHolder staticObj = new ObjectHolder(); // 메서드영역에 저장
        ObjectHolder instanceObj = new ObjectHolder(); // 자바 힙에 저장

        void foo() {
            ObjectHolder localObj = new ObjectHolder(); // foo 메서드의 지역 변수 테이블에 저장
            System.out.println("done"); // 중단점 단말
        }

    }
    private static class ObjectHolder{}

    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
