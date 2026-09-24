package map;

import model.Airport;

// selekcija aerodroma i blinkanje
public class AirportSelection {

	public interface MapListener {
		void repaint();
	}

	private final MapListener listener;

	private volatile Airport selected = null;
	private volatile boolean blink = false;
	private volatile boolean running = false;
	private Thread blinkThread = null;

	public AirportSelection(MapListener callback) {
		this.listener = callback;
	}

	public Airport getSelected() {
		return selected;
	}

	public boolean isSelected(Airport a) {
		return selected == a;
	}

	public boolean isBlinkOn() {
		return blink;
	}

	// klik na aerodrom: ako je vec izabran onda iskljuci selekciju, inace ga izaberi i pokreni treptanje
	public void toggle(Airport a) {
		if (selected == a) {
			clear();
		} else {
			selected = a;
			startBlinking();
		}
		listener.repaint();
	}

	public void clear() {
		selected = null;
		stopBlinking();
		listener.repaint();
	}

	// pravi novu nit za treptanje (prvo bezbedno zaustavi prethodnu ako postoji)
	// synchronized sprecava da se start/stop pozivi ikad preplicu
	private synchronized void startBlinking() {
		stopBlinking();
		running = true;
		blink = true; 
		blinkThread = new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(500);
					blink = !blink;
					listener.repaint();
				} catch (InterruptedException e) {
					break;
				}
			}
		});
		blinkThread.start();
	}

	// gasi nit za treptanje, blinkThread polje se menja samo ovde
	private synchronized void stopBlinking() {
		running = false;
		if (blinkThread != null) {
			blinkThread.interrupt();
			blinkThread = null;
		}
		blink = false;
	}
}
