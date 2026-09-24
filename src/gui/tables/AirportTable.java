package gui.tables;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.MouseListener;
import java.util.List;

import gui.panels.MapPanel;
import model.Airport;

// uzima podatke iz liste koja je u klasi AirTrafficModel i osvezava tabelarni prikaz 
// ima checkbox koji preko itemlistenera javlja mapPanelu da li treba aerodrom da se prikaze/sakrije
public class AirportTable extends ScrollPane {

	private static final long serialVersionUID = 1L;

	private Panel content = new Panel(new GridLayout(0, 5));

	private MapPanel mapPanel;
	private MouseListener activityListener;

	public AirportTable() {
		super();
		addColumnNames();
		add(content);
	}

	public void addColumnNames() {
		content.add(new Label("Show", Label.CENTER));
		content.add(new Label("Airport name", Label.CENTER));
		content.add(new Label("Airport code", Label.CENTER));
		content.add(new Label("X coordinate", Label.CENTER));
		content.add(new Label("Y coordinate", Label.CENTER));
	}

	// prijavljuje aktivnost korisnika na klik bilo gde u tabeli: postojece celije + sve buduce
	public void setActivityListener(MouseListener listener) {
		this.activityListener = listener;
		attachActivity(content);
		for (Component c : content.getComponents()) {
			attachActivity(c);
		}
	}

	private void attachActivity(Component c) {
		if (activityListener != null) {
			c.addMouseListener(activityListener);
		}
	}

	public void drawAirportRow(Airport a) {
		Panel p = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		Checkbox cb = new Checkbox("", true);
		cb.addItemListener(e -> {
			if (mapPanel != null) {
				mapPanel.setAirportVisible(a, cb.getState());
			}
		});
		attachActivity(cb);
		attachActivity(p);
		p.add(cb);

		Label nameLabel = new Label(a.getName(), Label.CENTER);
		Label codeLabel = new Label(a.getCode(), Label.CENTER);
		Label xLabel = new Label(String.valueOf(a.getX()), Label.CENTER);
		Label yLabel = new Label(String.valueOf(a.getY()), Label.CENTER);
		attachActivity(nameLabel);
		attachActivity(codeLabel);
		attachActivity(xLabel);
		attachActivity(yLabel);

		content.add(p);
		content.add(nameLabel);
		content.add(codeLabel);
		content.add(xLabel);
		content.add(yLabel);
	}

	public void addAirport(Airport a) {
		drawAirportRow(a);
		content.validate();
		validate();
		repaint();
	}

	public void refreshTable(List<Airport> airports) {
		content.removeAll();
		addColumnNames();
		for (Airport a : airports) {
			drawAirportRow(a);
		}
		content.validate();
		validate();
		repaint();
	}

	// ne prave se mapa i tabela u isto vreme pa treba pomocu tabela.set(mapa) da se uvezu
	public void setMapPanel(MapPanel p) {
		this.mapPanel = p;
	}
	
}