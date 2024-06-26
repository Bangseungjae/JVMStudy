package org.ch3;

/**
 * 이 코드가 보여 주려는 핵심은 두 가지다.
 * 1. 객체는 GC 프로세스 중간에 스스로를 구원할 수 있다.
 * 2. 이 자가 구원 기회는 단 한 번뿐이다. finalize() 메서드는 시스템이 최대 한 번만 호출해 주기 때문이다.
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("이야, 나 아직 살아 있어. :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("메서드 실행됨!");
        FinalizeEscapeGC.SAVE_HOOK = this; // 자신의 참조를 할당
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 회수 대상이 첫 번째 기회를 잡아 스스로를 구원한다.
        SAVE_HOOK = null;
        System.gc();
        // 종료자 스레드의 우선순위가 낮으니 0.5초간 기다린다.
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("안 돼, 내가 죽다니. :(");
        }

        // 다음 코드는 위와 완전히 같지만 이번에는 자가 구원에 실패한다.
        SAVE_HOOK = null;
        System.gc();
        // 종료자 스레드의 우선순위가 낮으니 0.5초간 기다린다.
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("안 돼, 내가 죽다니. :(");
        }
    }
}
