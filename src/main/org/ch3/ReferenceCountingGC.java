package org.ch3;

/**
 * VM Args: -Xlog:gc*
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[2 *_1MB];

    public static void testGC() {
        // 두 객체 생성
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        // 내부 필드로 서로를 참조
        objA.instance = objA;
        objB.instance = objA;

        // 참조 해제
        objA = null;
        objB = null;

        // 이 라인에서 GC가 수행된다면 objA와 objB가 회수될까?
        System.gc(); // 여기서 사실 회수가 된다. JVM이 객체 생사 판단에 참조 카운팅 알고리즘을 사용하지 않음을 알 수 있다.
    }
    public static void main(String[] args) {
        testGC();
    }
}
