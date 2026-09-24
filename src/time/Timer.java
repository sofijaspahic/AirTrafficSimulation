package time;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Timer extends Thread {

	private Label label; // time
	private volatile boolean working = true;
	private volatile boolean paused = false;
	private volatile int s; // since last activity
	private final Frame owner; // main window
	private QuitDialog dialog;

	private class QuitDialog extends Dialog {

		private static final long serialVersionUID = 1L;
		private Label text1 = new Label("No user activity detected.", Label.CENTER);
		private Label text2 = new Label("", Label.CENTER);
		private Label text3 = new Label("Do you want to continue working?", Label.CENTER);
		private Button continueBtn = new Button("Continue");
		private Button closeBtn = new Button("EXIT");

		public QuitDialog(Frame owner) {
			super(owner, "Quit", false);

			setLayout(new BorderLayout());
			setSize(600, 200);
			setLocation(owner.getX() + owner.getWidth() / 2 - getWidth() / 2,
					owner.getY() + owner.getHeight() / 2 - getHeight() / 2);
			setResizable(false);
			setBackground(new Color(255, 76, 76));

			Panel textPanel = new Panel(new GridLayout(3, 1, 0, 2));
			text2.setText("The application will close automatically in 5 seconds.");
			textPanel.add(text1);
			textPanel.add(text2);
			textPanel.add(text3);
			add(textPanel, BorderLayout.NORTH);

			Panel buttons = new Panel();
			continueBtn.addActionListener((ae) -> {
				reset();
			});

			closeBtn.addActionListener((ae) -> {
				closeProgram();
			});

			buttons.add(continueBtn);
			buttons.add(closeBtn);

			add(buttons, BorderLayout.SOUTH);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					reset();
				}
			});

			setVisible(true);
		}

		public void setRemainingTime(int remaining) {
			text2.setText("The application will close automatically in " + remaining + " seconds.");
		}
	}

	public Timer(Label label, Frame owner) {
		this.label = label;
		this.owner = owner;
		updateLabel();
	}

	public void run() {
		// sve dok aplikacija radi nit se vrti ovde
		while (working) {
			try {
				countUntilWarning();
				// proveravamo da li nas je neko ugasio u medjuvremenu
				if (!working)
					break;
				showWarningDialog();
				countWarningSeconds();
				if (!working)
					break;
				// nismo prekinuti i vreme je isteklo -> gasi
				closeProgram();
			} catch (InterruptedException e) {
				// uhvacen interrupt() iz reset() metode
				closeDialog();
				s = 0;
				updateLabel();
			}
		}
		// working=false
		if (owner != null) {
			owner.dispose();
		}
	}

	private void countUntilWarning() throws InterruptedException {

		while (s < 55 && working) {
			sleep(1000);
			if (!paused) {
				s++;
				updateLabel();
			}

		}
	}

	private void updateLabel() {
		label.setText(toString());
	}

	private void showWarningDialog() throws InterruptedException {
		if (!working)
			return;
		if (paused)
			throw new InterruptedException();
		dialog = new QuitDialog(owner);
	}

	private void countWarningSeconds() throws InterruptedException {
		for (int remaining = 5; remaining > 0 && working; remaining--) {
			if (paused) {
				closeDialog();
				throw new InterruptedException();
			}

			if (dialog != null) {
				dialog.setRemainingTime(remaining);
			}
			Thread.sleep(1000);
		}
	}

	private void closeDialog() {
		if (dialog != null) {
			dialog.dispose();
			dialog = null;
		}

	}

	public synchronized void closeProgram() {
		if (!working)
			return; // ako je vec pokrenuto zatvaranje, ignorisati
		working = false;
		closeDialog();
		if (Thread.currentThread() != this) {
			this.interrupt(); // u run ce odraditi sve sto je potrebno
		}
	}

	public void setPaused(boolean paused) { // true - selektovan aerodrom ili simulacija krenula
		this.paused = paused;
		if (paused) {
			closeDialog();
		} else {
			reset();
		}
	}

	public synchronized void reset() { // korisnik uradio nesto
		if (!working)
			return;
		s = 0;
		closeDialog();
		updateLabel();
		this.interrupt(); // reset thread ( resetujemo sleep u petlji )
	}

	@Override
	public String toString() {
		int min = s / 60;
		int sec = s % 60;
		return String.format("%02d:%02d", min, sec);
	}
}