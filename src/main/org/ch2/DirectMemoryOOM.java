package org.ch2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * VM 매개변수: -Xmx20M -XX:MaxDirectMemorySize=10M
 * MaxDirectMemorySize는 네이티브 메모리 용량이다. default는 -Xmx로 설정한 Java heap 최댓값이랑 같다.
 * 이 Java 코드는 JVM의 직접 메모리 할당을 사용하여 메모리 부족(OutOfMemory) 상황을 유발하는 예제입니다.
 * 이 코드는 매우 위험할 수 있으므로 실제로 실행하지 않는 것이 좋습니다.
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafedField = Unsafe.class.getDeclaredFields()[0]; // Unsafe 클래스의 첫 번째 필드를 가져옴
        unsafedField.setAccessible(true); // 비공개 필드를 접근할 수 있도록 설정
        Unsafe unsafe = (Unsafe) unsafedField.get(null); // Unsafe 인스턴스를 얻음
        while (true) {
            unsafe.allocateMemory(_1MB); // 1MB씩 직접 메모리를 할당하여 메모리 부족 상황을 유발
        }
    }
}
