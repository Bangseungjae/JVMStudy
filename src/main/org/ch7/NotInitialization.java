package org.ch7;


/**
 * 클래스 필드를 이용한 수동 참조 시연:
 * 하위 클래스를 통해 부모 클래스의 정적 필드를 참조하는 경우 하위 클래스 초기화는 필요없다.
 */
class SuperClass {
    static {
        System.out.println("상위 클래스 초기화!");
    }
    public static int value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("하위 클래스 초기화!");
    }
}

public class NotInitialization {
    public static void main(String[] args) {
        System.out.println(SubClass.value);
    }
}
