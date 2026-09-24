package simulation;

import gui.panels.SimulationPanel;

public class SimulationTimer extends Thread{

	private SimulationPanel panel;
    private volatile boolean running = true;
    private volatile boolean paused=false;
    private volatile int minutes = 0;

    public SimulationTimer(SimulationPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (running) {
        	if(!paused) {
                panel.setSimulationTime(formatTime());
        	}
            try {
                sleep(200); // 1s realno prosla
            } catch (InterruptedException e) {
                return;
            }
            if(!paused) {
            	minutes += 2; // 10min u simulaciji proslo
            }
        }
    }

    private String formatTime() {
        int h = (minutes / 60) % 24;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    public void stopTimer() {
        running = false;
        interrupt();
    }

    public void pauseTimer() {
        paused=true;
    }
    
    public void resumeTimer() {
        paused=false;
    }
  
//    public void togglePause() {
//        paused = !paused;
//    }
    
    public int getMinutes() {
    	return minutes;
    }
}
