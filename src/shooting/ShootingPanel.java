package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class ShootingPanel extends JPanel {
	public BufferedImage image;

	public ShootingPanel() {
		super();
		this.image = new BufferedImage(ShootingFrame.FRAME_HEIGHT, ShootingFrame.FRAME_WIDTH,
				BufferedImage.TYPE_INT_RGB);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, this);

	}

	public void draw() {
		this.repaint();
	}
}
