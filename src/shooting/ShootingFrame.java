package shooting;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ShootingFrame extends JFrame {
	public ShootingPanel panel;
	public static final int FRAME_HEIGHT = 500;
	public static final int FRAME_WIDTH = 500;

	public ShootingFrame() {

		panel = new ShootingPanel();
		this.add(panel);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				Shooting.loop = true;
			}
		});

		this.addKeyListener(new Keyboard());

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Shooting Game");
		this.pack();
		this.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
