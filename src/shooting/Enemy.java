package shooting;

public class Enemy {
	int enemyX;
	int enemyY;
	int enemyMoveDistance = 3;
	
	Enemy(int enemyX, int enemyY){
		this.enemyX = enemyX;
		this.enemyY = enemyY;
	}
	public int returnEnemyCenterX(Enemy e) {
		return e.enemyX + 15;
	}
	public int returnEnemyCenterY(Enemy e) {
		return e.enemyY + 5;
	}
}
