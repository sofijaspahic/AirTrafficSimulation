package controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import time.Timer;

public class ActivityTracker {

	private Timer timer;

	public ActivityTracker(Timer timer) {
		this.timer = timer;
	}

	public void userActivity() {
		if (timer != null) {
			timer.reset();
		}
	}

	public MouseListener mouseListener() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				userActivity();
			}
		};
	}

	public void addActivityListeners(Component c) {
		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				userActivity();
			}
		});
		c.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				userActivity();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				userActivity();
			}
		});
		if (c instanceof Container) {
			Container container = (Container) c;
			for (Component child : container.getComponents()) {
				addActivityListeners(child);
			}
		}
	}

}
