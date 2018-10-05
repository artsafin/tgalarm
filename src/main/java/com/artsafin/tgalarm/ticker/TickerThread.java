package com.artsafin.tgalarm.ticker;

public class TickerThread extends Thread {
    private final TickerService tickerService;

    public TickerThread(TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                tickerService.tick();

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
