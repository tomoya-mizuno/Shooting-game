package shooting;

public class Burret {
	static final int PLAYER_BURRET_SPEED = 15;
	static int PLAYER_BURRET_INTERVAL = 5;
	static final int ENEMY_BURRET_SPEED = 4;
	static int ENEMY_BURRET_INTERVAL = 15;

	int burretX;
	int burretY;

	Burret(int burretX, int burretY) {
		this.burretX = burretX;
		this.burretY = burretY;
	}
	public int returnBurretCenterX(Burret b) {
		return b.burretX + 2;
	}
	public int returnBurretCenterY(Burret b) {
		return b.burretY + 2;
	}
}
