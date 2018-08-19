package com.lcydream.project.multicast;

/**
 * Test
 *
 * @author Luo Chun Yun
 * @date 2018/8/18 20:39
 */
@SuppressWarnings("all")
public enum Test {
    SECONDS{
        @Override
        int excessNanos(long d, long m) { return 0; }
    };

    abstract int excessNanos(long d, long m);

    public void sleep(long timeout){
        System.out.println(excessNanos(1,2));
        System.out.println(timeout);
    }
}
