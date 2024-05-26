package org.ch2;


public class RuntimeConstantPoolOOM_2 {
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        String str = stringBuffer.append("컴퓨터").append("소프트웨어").toString();

        // JDK 6일 경우 영구세대에서 꺼내옮으로 false
        // JDK 7이상일 경우 힙에서 꺼내와서 true
        System.out.println(str.intern() == str); // 동등성 비교
    }
}
