package map;

// pretvara geografske kordinate u piksele
public class MapProjection {

	private final int width;
	private final int height;
	private final int marginX;
	private final int marginY;
	private final double zoom;

	public MapProjection(int width, int height, int marginX, int marginY) {
		this(width, height, marginX, marginY, 1.0);
	}

	public MapProjection(int width, int height, int marginX, int marginY, double zoom) {
		this.width = width;
		this.height = height;
		this.marginX = marginX;
		this.marginY = marginY;
		this.zoom = zoom;
	}

	public double toDoublePixelX(double x) {
		double mapWidth = width - 2 * marginX;
		double centerX = marginX + mapWidth / 2;
		return centerX + x * mapWidth / 360 * zoom;
	}

	public double toDoublePixelY(double y) {
		double mapHeight = height - 2 * marginY;
		double centerY = marginY + mapHeight / 2;
		return centerY - y * mapHeight / 180 * zoom; // opposite direction
	}

	public int toPixelX(int x) {
		return (int) Math.round(toDoublePixelX(x));
	}

	public int toPixelY(int y) {
		return (int) Math.round(toDoublePixelY(y));
	}

	public double fromPixelX(int px) {
		double mapWidth = width - 2 * marginX;
		double centerX = marginX + mapWidth / 2;
		return (px - centerX) * 360 / (zoom * mapWidth);
	}

	public double fromPixelY(int py) {
		double mapHeight = height - 2 * marginY;
		double centerY = marginY + mapHeight / 2;
		return (centerY - py) * 180 / (zoom * mapHeight);
	}
}
