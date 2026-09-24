package gui.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionListener;

//start pause reset i simulation time
public class SimulationPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private Label timerLabel = new Label("00:00", Label.CENTER);
	private Label simTextLabel = new Label("", Label.CENTER);
	private Label simTimerLabel = new Label("", Label.CENTER);
	private Button startBtn = new Button("Start");
	private Button pauseBtn = new Button("Pause");
	private Button resetBtn = new Button("Reset");
	private Button zoomIn = new Button("ZOOM IN");
	private Button zoomOut = new Button("ZOOM OUT");

	public SimulationPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(204, 229, 255));

		Panel timePanel = new Panel(new FlowLayout());
		timePanel.add(new Label("Time:", Label.CENTER));
		timePanel.add(timerLabel);

		Panel buttonPanel = new Panel(new GridLayout(6, 1, 5, 5));
		buttonPanel.add(startBtn);
		buttonPanel.add(pauseBtn);
		buttonPanel.add(resetBtn);
		buttonPanel.add(zoomIn);
		buttonPanel.add(zoomOut);

		Panel simTimePanel = new Panel(new GridLayout(2, 1));
		simTimePanel.add(simTextLabel);
		simTimePanel.add(simTimerLabel);

		Panel centerPanel = new Panel(new BorderLayout());
		centerPanel.add(buttonPanel, BorderLayout.CENTER);
		centerPanel.add(simTimePanel, BorderLayout.SOUTH);
		centerPanel.add(new Label(""), BorderLayout.EAST);
		centerPanel.add(new Label(""), BorderLayout.WEST);
		centerPanel.add(new Label(""), BorderLayout.NORTH);

		add(timePanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		add(new Label(""), BorderLayout.SOUTH);
	}

	public Label getTimerLabel() {
		return timerLabel;
	}

	public Label getSimTimerLabel() {
		return simTimerLabel;
	}

	public void setSimulationTime(String time) {
		simTimerLabel.setText(time);
	}

	public void showSimulationTime() {
		simTextLabel.setText("Simulation time:");
		validate();
		repaint();
	}

	public void resetSimulationTime() {
		simTextLabel.setText("Simulation time:");
		simTimerLabel.setText("00:00");
		validate();
		repaint();
	}

	public void addStartListener(ActionListener listener) {
		startBtn.addActionListener(listener);
	}

	public void addPauseListener(ActionListener listener) {
		pauseBtn.addActionListener(listener);
	}

	public void addResetListener(ActionListener listener) {
		resetBtn.addActionListener(listener);
	}

	public void addZoomInListener(ActionListener listener) {
		zoomIn.addActionListener(listener);
	}

	public void addZoomOutListener(ActionListener listener) {
		zoomOut.addActionListener(listener);
	}
}
