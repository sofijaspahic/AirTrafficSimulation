package gui.panels;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import map.AircraftRerouting;
import map.AirportSelection;
import map.MapProjection;
import model.Airport;
import simulation.Aircraft;
import simulation.SimulationListener;
import time.Timer;

// crta mapu, aerodrome, avione
// obradjuje klik na aerodrome, delegira AirportSelectionu
// prima listu aerodroma i crta sivi kvadratic na mapi i kretnju aviona 
// prima azuriranja pozicija aviona od simulation engine, implementira simulation listener
public class MapPanel extends Canvas implements SimulationListener {

	private static final long serialVersionUID = 1L;
	private List<Airport> airports = new ArrayList<Airport>();
	private List<Airport> hiddenAirports = new ArrayList<Airport>();
	private List<Aircraft> aircrafts = new ArrayList<Aircraft>();
	private static final int size = 10;
	private static final int marginX = 20;
	private static final int marginY = 15;

	private static final double MIN_ZOOM = 1.0;
	private static final double MAX_ZOOM = 8.0;
	private static final double ZOOM_STEP = 1.2;

	private double zoom = 1.0;

	private final AirportSelection selection = new AirportSelection(() -> render());
	private final AircraftRerouting rerouting = new AircraftRerouting(() -> render());
	private Timer timer;

	private BufferStrategy bufferStrategy;

	public MapPanel() {
		setBackground(new Color(220, 238, 255));
		setIgnoreRepaint(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClick(e.getX(), e.getY());
				render();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				handlePress(e.getX(), e.getY());
				render();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				handleRelease(e.getX(), e.getY());
				render();
			}

		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				render();
			}
		});
	}

	private void handlePress(int x, int y) {

	}

	private void handleRelease(int x, int y) {

	}

	private void handleClick(int x, int y) {
		MapProjection projection = new MapProjection(getWidth(), getHeight(), marginX, marginY, zoom);

		if (rerouting.isActive()) {

			Airport airport = hitTestAirport(projection, x, y);
			if (airport != null) {
				rerouting.finish(airport);
			} else {
				rerouting.addWaypoints(projection.fromPixelX(x), projection.fromPixelY(y));
			}
			return;
		}

		Airport airport = hitTestAirport(projection, x, y);
		if (airport != null) {
			selection.toggle(airport);
			if (timer != null) {
				timer.setPaused(selection.getSelected() != null);
			}
			return;
		}

		Aircraft aircraft = hitTestAircraft(projection, x, y);
		if (aircraft != null) {
			if (rerouting.isEnabled()) {
				rerouting.selectAircraft(aircraft);
			}
		}

	}

	private Airport hitTestAirport(MapProjection projection, int x, int y) {
		for (Airport a : airports) {
			if (hiddenAirports.contains(a))
				continue;
			int aX = projection.toPixelX(a.getX());
			int aY = projection.toPixelY(a.getY());
			if (x >= aX - size / 2 && x <= aX + size / 2 && y >= aY - size / 2 && y <= aY + size / 2) { // in airport
				return a;
			}
		}
		return null;
	}

	private Aircraft hitTestAircraft(MapProjection projection, int x, int y) {
		for (Aircraft a : aircrafts) {
			if (!a.isActive())
				continue;
			double aX = projection.toDoublePixelX(a.getX());
			double aY = projection.toDoublePixelY(a.getY());
			double dx = x - aX, dy = y - aY;
			if (dx * dx + dy * dy <= size * size)
				return a;
		}
		return null;
	}

	public void render() {
		if (!isDisplayable())
			return; // panel jos nije na ekranu, nema peer, nema smisla crtati

		if (bufferStrategy == null) {
			createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
			if (bufferStrategy == null)
				return; // peer jos nije spreman, pokusaj opet
		}

		int w = getWidth();
		int h = getHeight();
		if (w <= 0 || h <= 0)
			return;

		do {
			do { // crta u nevidljivom buferu
				Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
				try {
					g2.setColor(getBackground());
					g2.fillRect(0, 0, w, h);
					g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // sredjuje
																														// tekst
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // oblike
					g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE); // debljinu
					MapProjection projection = new MapProjection(w, h, marginX, marginY, zoom);
					drawMapBackground(g2, w, h);
					drawCoordSystem(g2, projection, w, h);
					drawAirports(g2, projection);
					drawPlanes(g2, projection);

				} finally {
					g2.dispose();
				}
			} while (bufferStrategy.contentsRestored()); // ako se desi nesto sa sadrzajem bafera
			bufferStrategy.show(); // menja se koji se prikazuje, postaje vidljiv
		} while (bufferStrategy.contentsLost()); // ako se izgubi pri swapovanju

		// Toolkit.getDefaultToolkit().sync(); // forsira flush na ekran
	}

	public void stopBlinking() {
		selection.clear();
	}

	public void zoomIn() {
		zoom = Math.min(MAX_ZOOM, zoom * ZOOM_STEP);
		render();
	}

	public void zoomOut() {
		zoom = Math.max(MIN_ZOOM, zoom / ZOOM_STEP);
		render();
	}

	public void resetZoom() {
		zoom = 1.0;
		render();
	}

	private void drawMapBackground(Graphics g, int w, int h) {
		g.setColor(new Color(220, 238, 255)); // plava pozadina mape
		g.fillRect(marginX, marginY, w - 2 * marginX, h - 2 * marginY);

		g.setColor(Color.WHITE); // beli okvir
		g.drawRect(marginX, marginY, w - 2 * marginX, h - 2 * marginY);
	}

	private void drawCoordSystem(Graphics g, MapProjection projection, int w, int h) {
		int x1 = marginX;
		int y1 = marginY;
		int width = w - 2 * marginX;
		int height = h - 2 * marginY;

		g.setColor(Color.GRAY);
		g.drawLine(x1, y1 + height / 2, x1 + width, y1 + height / 2);
		g.drawLine(x1 + width / 2, y1, x1 + width / 2, y1 + height);

		int centerX = x1 + width / 2;
		int centerY = y1 + height / 2;

		g.setFont(new Font("Arial", Font.PLAIN, 7));
		for (int x = -180; x <= 180; x += 15) {
			int dx = projection.toPixelX(x);
			g.drawLine(dx, centerY - 3, dx, centerY + 3);
			g.drawString(String.valueOf(x), dx - 6, centerY + 15);
		}
		for (int y = -90; y <= 90; y += 15) {
			int dy = projection.toPixelY(y);
			g.drawLine(centerX - 3, dy, centerX + 3, dy);
			if (y != 0) {
				g.drawString(String.valueOf(y), centerX - 20, dy + 3);
			}
		}
	}

	private void drawAirport(Graphics g, MapProjection projection, Airport a, boolean blinkOn) {
		int mapX = projection.toPixelX(a.getX());
		int mapY = projection.toPixelY(a.getY());

		g.setColor(blinkOn ? Color.RED : Color.DARK_GRAY);
		g.fillRect(mapX - size / 2, mapY - size / 2, size, size);
		g.setColor(Color.GRAY);
		g.drawString(a.getCode(), mapX + size, mapY - size);
	}

	private void drawAirports(Graphics g, MapProjection projection) {
		Airport selected = selection.getSelected();
		for (Airport a : airports) {
			if (a == null || hiddenAirports.contains(a) || a == selected) // crta sve sem selektovanog
				continue;
			drawAirport(g, projection, a, false);
		}
		if (selected != null && !hiddenAirports.contains(selected)) {
			drawAirport(g, projection, selected, selection.isBlinkOn());
		}
	}

	public void drawPlanes(Graphics g, MapProjection projection) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // sredjuje pravilnost
																									// kruzica
		g2.setColor(Color.BLUE);
		for (Aircraft a : aircrafts) {
			if (!a.isActive())
				continue;
			double x = projection.toDoublePixelX(a.getX());
			double y = projection.toDoublePixelY(a.getY());
			Ellipse2D.Double circle = new Ellipse2D.Double(x - 5, y - 5, 10, 10);
			g2.fill(circle);
		}
	}


	
	public void setAirports(List<Airport> airports) {
		this.airports = airports;
		render();
	}

	public void setAircrafts(List<Aircraft> aircrafts) {
		this.aircrafts = aircrafts;
		render();
	}

	public void clearAircrafts() {
		aircrafts.clear();
		render();
	}

	public void setAirportVisible(Airport a, boolean visible) {
		if (visible) {
			hiddenAirports.remove(a);
		} else {
			if (!hiddenAirports.contains(a)) {
				hiddenAirports.add(a);
			}
		}
		render();
	}

	public void setReroutingAllowed(boolean allowed) {
		rerouting.setEnabled(allowed);
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void onAircraftsUpdated(List<Aircraft> aircrafts) {
		setAircrafts(aircrafts);
	}

	
}
