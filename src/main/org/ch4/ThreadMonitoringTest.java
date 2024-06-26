package org.ch4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadMonitoringTest {
    /**
     * 무한 루프 도는 스레드 생성
     */
    public static void createBusyThread() {
        Thread thread = new Thread(() -> {
            while (true) {

            }
        }, "testBusyThread");
        thread.start();
    }

    /**
     * 락을 대기하는 스레드 생성
     */
    public static void createLockThread(final Object lock) throws InterruptedException {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread");

        thread.start();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        // 콘솔(터미널)에서 '엔터' 키를 치면 다음 줄 실행
        bf.readLine();
        createBusyThread();

        // 콘솔(터미널)에서 '엔터' 키를 치면 다음 줄 실행
        bf.readLine();
        Object obj = new Object();
        createLockThread(obj);

    }
}
