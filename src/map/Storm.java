package map;

public class Storm {
	
	private double centerX;
	private double centerY;
	private double radius;

	public Storm(double centerX, double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = 0;
	}

	public void grow(double amount) {
		radius += amount;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public double getRadius() {
		return radius;
	}
	
}
