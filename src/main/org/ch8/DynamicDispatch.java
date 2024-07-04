package org.ch8;

public class DynamicDispatch {
    static abstract class Human {
        protected int x = 9;
        protected abstract void sayHello();
    }

    static class Man extends Human {
        int x = 10;
        @Override
        protected void sayHello() {
            System.out.println("Man said hello");
        }

    }

    static class Woman extends Human {
        static int x = 11;
        @Override
        protected void sayHello() {
            System.out.println("woman siad hello");
        }
    }

    private static Human human = new Man();
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();

        man.sayHello();
        woman.sayHello();

        man = new Woman();
        man.sayHello();
        System.out.println(human.x);
        human = new Woman();
        System.out.println(human.x);
    }
}
