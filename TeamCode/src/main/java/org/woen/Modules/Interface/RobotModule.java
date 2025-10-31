package org.woen.Modules.Interface;

public interface RobotModule {
    void init();

    default void update(){}

    default boolean isAtTarget(){
        return true;
    }
}
