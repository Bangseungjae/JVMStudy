package org.ch3;


public class TestSerialGCAllocation {
    public static void main(String[] args) {
        if (args.length == 1) {
            int methodIndex = Integer.valueOf(args[0]);
            System.out.println("index: " + methodIndex);

            switch (methodIndex) {
                case 1: testAllocation(); break;
                case 2: testPretenureSizeThreshold(); break;
                case 3: testTenuringThreshold(); break;
                case 4: testTenuringThreshold2(); break;
            }
        } else {
            System.out.println("사용법: java <VM 매개변수> TestSerialGCAllocation <호출할 메서드 번호(1~4)>");
            System.out.println("  메서드 번호: 1 = testAllocation()");
            System.out.println("  메서드 번호: 2 = testPretenureSizeThreshold()");
            System.out.println("  메서드 번호: 3 = testTenuringThreshold()");
            System.out.println("  메서드 번호: 4 = testTenuringThreshold2()");
        }
    }
    private static final int _1MB = 1024 * 1024;

    /**
     * VM 매개 변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc*
     * 대부분의 경우 객체는 신세대의 에덴에 할당된다. 에덴의 공간이 부족해지면 가상 머신은 마이너 GC를 시작한다.
     * Xms: 힙 초기 사이즈
     * Xmx: 힙 최대 사이즈
     * Xmn: 신세대 사이즈 지정, 나머지는 구세대에 배정된다.
     * -XX:SurvivorRatio=8 = 신세대의 에덴과 생존자 공간 비율을 8:1로 설정한다.
     */
    public static void testAllocation() {
        byte[] alloc1, alloc2, alloc3, alloc4;
        alloc1 = new byte[2 * _1MB];
        alloc2 = new byte[2 * _1MB];
        alloc3 = new byte[2 * _1MB];
        alloc4 = new byte[4 * _1MB]; // 마이너 GC 발생
    }

    /**
     * VM 매개 변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -XX:PretenureSizeThreshold=3M
     * -XX:PretenureSizeThreshold=3M -> 3M 이상 크기의 객체를 바로 구세대에 배치한다. 이 매개 변수의 목적은 에덴과 두 생존자 공간 사이의 대규모
     * 복사를 줄이는 데 있다.
     * PretenureSizeThreshold 이 매개변수는 시리얼과 파뉴에만 사용 가능하다.
     */
    public static void testPretenureSizeThreshold() {
        byte[] alloc;
        alloc = new byte[4 * _1MB]; // 곧장 구세대에 할당
    }

    /**
     * 나이가 차면 구세대로 옮겨진다.
     * 객체가 구세대로 승격되는 나이는 -XX:MaxTenuringThreshold 값을 바꿔가며 두 번 실행해 보자. 처음에는 1 다음에는 15
     * VM 매개 변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -Xlog:gc+age=trace -XX:MaxTenuringThreshold=1
     * 또는
     * VM 매개 변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -Xlog:gc+age=trace -XX:MaxTenuringThreshold=15 -XX:TargetSurvivorRatio=80
     * -XX:TargetSurvivorRatio=80 -> 생존자 공간의 80% 비율 이상 차면 살아남은 객체들은 구세대로 승격시킨다.
     */
    public static void testTenuringThreshold() {
        byte[] alloc1, alloc2, alloc3;
        // 구세대 이동 시기는 -XX:MaxTenuringThreshold 가 결정
        alloc1 = new byte[_1MB / 8];
        alloc2 = new byte[4 * _1MB];
        alloc3 = new byte[4 * _1MB]; // 첫 번째 GC 발생
        alloc3 = null;
        alloc3 = new byte[4 * _1MB]; // 두 번째 GC 발생
    }

    /**
     * 공간이 비좁으면 각제로 승격시킨다
     * -XX:MaxTenuringThreshold 에 설정한 나이보다 더 적어도 구세대로 승격시킬 수 있다.
     * 기본으로 생존자 공간의 50% 이상을 넘어서면 모든 객체를 구세대로 옮긴다. -XX:MaxTenuringThreshold 로 정한 나이는 무시된다.
     * VM 매개 변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -Xlog:gc+age=trace -XX:MaxTenuringThreshold=15 -XX:TargetSurvivorRatio=80
     */
    public static void testTenuringThreshold2() {
        byte[] alloc1, alloc_new, alloc2, alloc3;
        alloc1 = new byte[_1MB / 10 * 8];
        System.out.println("============" + alloc1.length);
        alloc_new = new byte[_1MB / 16];
        System.out.println("============" + alloc_new.length);
        // alloc1 + alloc_new = 생존자 공간의 80% 초과
        alloc2 = new byte[4 * _1MB];
        alloc3 = new byte[4 * _1MB]; // 첫 번째 GC 발생
        alloc3 = null;
        alloc3 = new byte[4 * _1MB]; // 두 번째 GC 발생
    }
}
