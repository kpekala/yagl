package com.example.paint;

public abstract class UpdateThread extends Thread {

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        float deltaTime = 10;
        while (true){
            long startTime = System.currentTimeMillis();
            onUpdate(deltaTime);
            deltaTime = System.currentTimeMillis();
        }
    }

    private void onUpdate(float deltaTime) {

    }
}
