package org.woen.core.device.trait;


@FunctionalInterface
public interface TickSleeper {
    void sleep() throws InterruptedException;
}
