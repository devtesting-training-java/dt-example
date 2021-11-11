package io.xp.kata.outsideteamcontrol;

public class HardToTestUtil {
    static void throwExceptionInTest() {
        if (isRunningInTest()) {
            throw new IllegalStateException("The code cannot run in test");
        }
    }

    public static boolean isRunningInTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}