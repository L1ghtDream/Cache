package dev.lightdream.runnable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Cache<T> implements IRunnable {

    public long updatePeriod;
    public long delay;

    public T value;
    public Consumer<Cache<T>> updater;

    private boolean enabled = true;

    private TimerTask task;
    private Timer timer;


    public Cache(Consumer<Cache<T>> updater, long updatePeriod) {
        this.updater = updater;
        this.updatePeriod = updatePeriod;
        registerUpdater();
    }

    public Cache(Consumer<Cache<T>> updater, long updatePeriod, long delay) {
        this.updater = updater;
        this.updatePeriod = updatePeriod;
        this.delay = delay;
        registerUpdater();
    }

    public void update(T value) {
        this.value = value;
    }

    @Override
    public void runOnce() {
        this.updater.accept(this);
    }

    public void registerUpdater() {
        task = new TimerTask() {
            public void run() {
                if (!enabled) {
                    return;
                }
                runOnce();
            }
        };
        timer = new Timer();

        timer.schedule(task, delay, updatePeriod);
    }

    public T get() {
        return value;
    }

    public void cancel() {
        task.cancel();
        timer.cancel();
    }

    @Override
    public void pause() {
        enabled = false;
    }

    @Override
    public void resume() {
        enabled = true;
    }

}
