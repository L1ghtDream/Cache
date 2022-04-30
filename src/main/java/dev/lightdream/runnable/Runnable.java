package dev.lightdream.runnable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Runnable implements IRunnable {

    public long updatePeriod;
    public long delay;

    public Consumer<Runnable> runner;
    private boolean enabled = true;

    private TimerTask task;
    private Timer timer;

    public Runnable(Consumer<Runnable> updater, long updatePeriod) {
        this.runner = updater;
        this.updatePeriod = updatePeriod;
        registerUpdater();
    }

    public Runnable(Consumer<Runnable> updater, long updatePeriod, long delay) {
        this.runner = updater;
        this.updatePeriod = updatePeriod;
        registerUpdater();
    }

    public void runOnce() {
        this.runner.accept(this);
    }

    public void registerUpdater() {
        task = new TimerTask() {
            public void run() {
                if(!enabled){
                    return;
                }
                runOnce();
            }
        };
        timer = new Timer();

        timer.schedule(task, delay, updatePeriod);
    }

    public void cancel() {
        task.cancel();
        timer.cancel();
    }

    public void pause() {
        enabled = false;
    }

    public void resume() {
        enabled = true;
    }

}
