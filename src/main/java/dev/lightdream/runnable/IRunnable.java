package dev.lightdream.runnable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface IRunnable {


    void runOnce();

    void registerUpdater();

    void cancel();

    void pause();

    void resume();

}
