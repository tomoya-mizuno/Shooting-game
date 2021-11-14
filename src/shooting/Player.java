package shooting;

public class Player {
	static final int LIMIT_UP = 100;
	static final int LIMIT_DOWN = 420;
	static final int LIMIT_RIGHT = 440;
	static final int LIMIT_LEFT = 10;

	int playerX;
	int playerY;
	int moveDistance = 15;

	Player() {
		this.playerX = 250;
		this.playerY = 400;
	}

	public void goUp() {
		if (this.playerY > LIMIT_UP) {
			this.playerY -= moveDistance;
		}
	}

	public void goDown() {
		if (this.playerY < LIMIT_DOWN) {
			this.playerY += moveDistance;
		}
	}

	public void goRight() {
		if (this.playerX < LIMIT_RIGHT) {
			this.playerX += moveDistance;
		}
	}

	public void goLeft() {
		if (this.playerX > LIMIT_LEFT) {
			this.playerX -= moveDistance;
		}
	}
	public int returnPlayerCenterX(Player p) {
		return p.playerX + 15;
	}
	public int returnPlayerCenterY(Player p) {
		return p.playerY + 5;
	}
}
