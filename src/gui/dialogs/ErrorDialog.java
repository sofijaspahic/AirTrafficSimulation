package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ErrorDialog extends Dialog {

	private static final long serialVersionUID = 1L;
	private static ErrorDialog instance = null;
	private Label text = new Label("", Label.CENTER);

	private ErrorDialog(Frame owner) {
		super(owner, "Error", true); // modalni dijalog - blokira koriscenje glavnog prozora
		setSize(450, 150);
		setLocation(owner.getX() + owner.getWidth() / 2 - getWidth() / 2,
				owner.getY() + owner.getHeight() / 2 - getHeight() / 2);
		setResizable(false);

		setBackground(new Color(255, 230, 230));

		add(text, BorderLayout.CENTER);
		Panel buttons = new Panel();
		Button continueB = new Button("OK");
		buttons.add(continueB);
		Button closeB = new Button("Close");
		buttons.add(closeB);
		add(buttons, BorderLayout.SOUTH);

		closeB.addActionListener(e -> { // zatvara celu aplikaciju
			owner.dispose();
			dispose();
		});

		continueB.addActionListener(e -> dispose()); // zatvara samo dialog

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

	}

	public static ErrorDialog getInstance(Frame owner) {
		if (instance == null) {
			instance = new ErrorDialog(owner);
		}
		return instance;
	}

	public static void showError(Frame owner, String message) {
		ErrorDialog dialog = getInstance(owner);
		dialog.text.setText(message);
		dialog.setLocation(owner.getX() + owner.getWidth() / 2 - dialog.getWidth() / 2,
				owner.getY() + owner.getHeight() / 2 - dialog.getHeight() / 2);
		dialog.setVisible(true);
	}

}
