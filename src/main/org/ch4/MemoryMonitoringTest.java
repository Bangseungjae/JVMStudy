package org.ch4;

import java.util.ArrayList;
import java.util.List;

/**
 * VM 매개 변수: -XX:+UseSerialGC -Xms100m -Xmx100m
 */
public class MemoryMonitoringTest {
    /**
     * 메모리 영역 확보 객체(placeholder), OOMObject의 크기는 약 64KB다.
     */
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // 모니터링 곡선의 변화를 더 분명하게 만들기 위한 약간의 지연
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000);
        while (true) { // 강제 종료 까지 대기
            System.out.println("대기 시작");
            Thread.sleep(1000);
        }
    }
}
