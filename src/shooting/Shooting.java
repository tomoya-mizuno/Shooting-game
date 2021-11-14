package shooting;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Shooting {
	public static ShootingFrame shootingFrame;
	public static boolean loop;
	
	public static void main(String[] args) {
		shootingFrame = new ShootingFrame();
		loop = true;
		
		Graphics gra = shootingFrame.panel.image.createGraphics();
		
		long startTime;
		long fpsTime = 0;
		int fps = 30;
		int FPS = 0;
		int fpsCount = 0;
		
		EnumShootingtScreen screen = EnumShootingtScreen.START;
		
		Player player = new Player();
		Random rand = new Random();
		ArrayList<Enemy> enemies = new ArrayList<>();
		ArrayList<Burret> burretsPlayer = new ArrayList<>();
		ArrayList<Burret> burretsEnemy = new ArrayList<>();
		int score = 0;
		int level = 1;
		int killCount = 0;
		
		while (loop) {
			// 実行時間がマイナスになった時は、fpsを下げて処理を継続する
			if ((System.currentTimeMillis() - fpsTime) >= 1000) {
				fpsTime = System.currentTimeMillis();
				FPS = fpsCount;
				fpsCount = 0;
			}
			fpsCount++;
			
			startTime = System.currentTimeMillis();
			
			// 背景の描画
			gra.setColor(Color.WHITE);
			gra.fillRect(0, 0, ShootingFrame.FRAME_HEIGHT, ShootingFrame.FRAME_WIDTH);
			
			// FPSを表示
			gra.setColor(Color.BLACK);
			Font font = new Font("SansSerif", Font.PLAIN, 10);
			gra.setFont(font);
			gra.drawString(String.format("%3d", FPS) + "FPS", 450, 460);
			
			// 各画面での挙動を制御
			switch (screen) {
			case START:
				// タイトル文字の設定
				String titleName = "Shooting Game";
				gra.setColor(Color.BLACK);
				font = new Font("SansSerif", Font.PLAIN, 40);
				gra.setFont(font);
				FontMetrics metrics = gra.getFontMetrics(font);
				gra.drawString(titleName, 250 - (metrics.stringWidth(titleName) / 2 + 10), 120);
				
				// ゲーム開始用文字の設定
				String stringGameStart = "Press ENTER to Start";
				font = new Font("SansSerif", Font.PLAIN, 25);
				gra.setFont(font);
				metrics = gra.getFontMetrics(font);
				gra.drawString(stringGameStart, 250 - (metrics.stringWidth(stringGameStart) / 2 + 10), 250);
				
				// ゲーム画面に進む
				if (Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
					screen = EnumShootingtScreen.GAME;
					player = new Player();
					enemies = new ArrayList<>();
					burretsPlayer = new ArrayList<>();
					burretsEnemy = new ArrayList<>();
					score = 0;
					level = 1;
					killCount = 0;
				}
				break;
			case GAME:
				// 一時停止の表示
				String stringPause = "Press SPACE to Pause";
				gra.setColor(Color.BLACK);
				font = new Font("SansSerif", Font.PLAIN, 10);
				gra.setFont(font);
				metrics = gra.getFontMetrics(font);
				gra.drawString(stringPause, 250 - (metrics.stringWidth(stringPause) / 2 + 10), 460);
				
				// SHIFTキー押下時に処理を一時停止する
				if (Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
					boolean isPause = true;
					while (isPause) {
						// ポーズ中の文字の設定
						String pausingNow = "Pausing...";
						gra.setColor(Color.BLACK);
						font = new Font("SansSerif", Font.PLAIN, 30);
						gra.setFont(font);
						metrics = gra.getFontMetrics(font);
						gra.drawString(pausingNow, 250 - (metrics.stringWidth(pausingNow) / 2 + 10), 180);
						
						// ゲーム画面に戻る文字の設定
						String restartGame = "Press SHIFT to Restart the Game";
						gra.setColor(Color.BLACK);
						font = new Font("SansSerif", Font.PLAIN, 20);
						gra.setFont(font);
						metrics = gra.getFontMetrics(font);
						gra.drawString(restartGame, 250 - (metrics.stringWidth(restartGame) / 2 + 10), 250);
						
						shootingFrame.panel.draw();
						
						// isPauseがtrueの間は処理を停止する
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							
						}
						// SHIFTキー再押下時にゲーム画面に戻る
						if (Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
							isPause = false;
						}
					}
				}
				
				// 画面左下にキル数とスコアとレベルを表示
				gra.setColor(Color.BLACK);
				font = new Font("SansSerif", Font.PLAIN, 10);
				gra.setFont(font);
				gra.drawString("LEVEL", 0, 460);
				gra.drawString(" : ", 40, 460);
				gra.drawString(String.format("%3d", level), 60, 460);
//				gra.drawString("KILL", 0, 440);
//				gra.drawString(" : ", 40, 440);
//				gra.drawString(String.format("%7d", killCount), 70, 440);
//				gra.drawString("SCORE", 0, 450);
//				gra.drawString(" : ", 40, 450);
//				gra.drawString(String.format("%7d", score), 70, 450);
				
				// プレイヤーの描画
				gra.setColor(Color.BLUE);
				gra.fillRect(player.playerX + 10, player.playerY - 10, 10, 10);
				gra.fillRect(player.playerX, player.playerY, 30, 10);
				
				// プレイヤーの移動設定(w:上 a:左 s:下 d:右)
				if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
					player.goUp();
				} else if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
					player.goLeft();
				} else if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
					player.goDown();
				} else if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
					player.goRight();
				}
				
				// 画面右上にプレイヤーの位置情報を表示
				font = new Font("SansSerif", Font.PLAIN, 10);
				gra.setColor(Color.BLACK);
				gra.setFont(font);
				gra.drawString("x : " + String.format("%4d", player.playerX) + " y : " + String.format("%4d", player.playerY), 410, 10);
				
				// 弾を撃つ
				if (Keyboard.isKeyPressed(KeyEvent.VK_ENTER) && Burret.PLAYER_BURRET_INTERVAL <= 0) {
					burretsPlayer.add(new Burret(player.playerX + 12, player.playerY - 10));
					score--;
					if (score < 0) {
						score = 0;
					}
					// スコアに応じて弾の発射間隔を短くする
					Burret.PLAYER_BURRET_INTERVAL = 5 - score / 20000;
				}
				if (Burret.PLAYER_BURRET_INTERVAL > 0) {
					Burret.PLAYER_BURRET_INTERVAL--;
				}
				// プレイヤーの弾の描画
				for (int i = 0; i < burretsPlayer.size(); i++) {
					Burret bp = burretsPlayer.get(i);
					gra.setColor(Color.BLUE);
					gra.fillRect(bp.burretX, bp.burretY, 5, 5);
					// スコアに応じて弾の速度を上げる
					bp.burretY -= Burret.PLAYER_BURRET_SPEED + score / 10000;
					// 画面外の弾の消去
					if (bp.burretY < 0) {
						burretsPlayer.remove(i);
						i--;
					}
				}
				// 敵の生成
				if (rand.nextInt(20 - (level - 1) / 5) == 0) {
					enemies.add(new Enemy(rand.nextInt(450) + 10, 0));
				}
				
				// 敵の描画
				for (int i = 0; i < enemies.size(); i++) {
					Enemy e = enemies.get(i);
					gra.setColor(Color.RED);
					gra.fillRect(e.enemyX, e.enemyY, 30, 10);
					gra.fillRect(e.enemyX + 10, e.enemyY + 10, 10, 10);
					// レベルに応じて敵の速度を上げる
					e.enemyY += e.enemyMoveDistance + level / 10;
					// 画面外の敵を消去
					if (e.enemyY > 500) {
						enemies.remove(i);
						i--;
					}
					if (rand.nextInt(10 - (level - 1) / 10) == 0 && Burret.ENEMY_BURRET_INTERVAL == 0) {
						burretsEnemy.add(new Burret(e.enemyX + 12, e.enemyY + 10));
						// レベルに応じて敵の弾の発射間隔を短くする
						Burret.ENEMY_BURRET_INTERVAL = 10 - level / 10;
					}
					if (Burret.ENEMY_BURRET_INTERVAL > 0) {
						Burret.ENEMY_BURRET_INTERVAL--;
					}
				}
				
				// 敵の弾の生成＋描画
				for (int i = 0; i < burretsEnemy.size(); i++) {
					Burret be = burretsEnemy.get(i);
					gra.setColor(Color.RED);
					gra.fillRect(be.burretX, be.burretY, 5, 5);
					be.burretY += Burret.ENEMY_BURRET_SPEED + level / 5;
					// 画面外の弾の消去
					if (be.burretY > 500) {
						burretsEnemy.remove(i);
						i--;
					}
				}
				
				// プレイヤーと敵の当たり判定
				double betweenPlayerToEnemy;
				for (int i = 0; i < enemies.size(); i++) {
					Enemy e = enemies.get(i);
					betweenPlayerToEnemy = Math.pow((player.returnPlayerCenterX(player) - e.returnEnemyCenterX(e)), 2)
							+ Math.pow((player.returnPlayerCenterY(player) - e.returnEnemyCenterY(e)), 2);
					if (betweenPlayerToEnemy <= 500) {
						screen = EnumShootingtScreen.GAMEOVER;
					}
				}
				
				// プレイヤーの弾と敵の当たり判定
				double betweenPlayerBurretToEnemy;
				for (int i = 0; i < burretsPlayer.size(); i++) {
					Burret bp = burretsPlayer.get(i);
					for (int j = 0; j < enemies.size(); j++) {
						Enemy e = enemies.get(j);
						betweenPlayerBurretToEnemy = Math.pow((bp.returnBurretCenterX(bp) - e.returnEnemyCenterX(e)), 2)
								+ Math.pow((bp.returnBurretCenterY(bp) - e.returnEnemyCenterY(e)), 2);
						if (betweenPlayerBurretToEnemy <= 300) {
							burretsPlayer.remove(i);
							enemies.remove(j);
							score += 100;
							killCount++;
							// 敵を10体倒すごとにレベルが上がる(レベル上限は100)
							if (killCount % 10 == 0) {
								if (level < 100) {
									level++;
								} else {
									level = 100;
								}
							}
						}
					}
				}
				
				// 敵の弾とプレイヤーの当たり判定
				double betweenPlayerToEnemyBurret;
				for (int i = 0; i < burretsEnemy.size(); i++) {
					Burret be = burretsEnemy.get(i);
					betweenPlayerToEnemyBurret = Math
							.pow((player.returnPlayerCenterX(player) - be.returnBurretCenterX(be)), 2)
							+ Math.pow((player.returnPlayerCenterY(player) - be.returnBurretCenterY(be)), 2);
					if (betweenPlayerToEnemyBurret <= 300) {
						screen = EnumShootingtScreen.GAMEOVER;
					}
				}
				
				break;
			case GAMEOVER:
				// キル数とスコアと到達レベルの表示
				String stringScore = "Score : " + score;
				String stringKill = "Kill : " + killCount;
				String stringLevel = "Level : " + level;
				font = new Font("SansSerif", Font.PLAIN, 40);
				gra.setColor(Color.BLACK);
				gra.setFont(font);
				metrics = gra.getFontMetrics(font);
				gra.drawString(stringScore, 250 - (metrics.stringWidth(stringScore) / 2 + 10), 160);
				gra.drawString(stringKill, 250 - (metrics.stringWidth(stringKill) / 2 + 10), 200);
				gra.drawString(stringLevel, 250 - (metrics.stringWidth(stringLevel) / 2 + 10), 240);
				
				// スタート画面に戻る
				String stringReturnToStart = "Press ESC to Return to Start";
				font = new Font("SansSerif", Font.PLAIN, 25);
				gra.setColor(Color.BLACK);
				gra.setFont(font);
				metrics = gra.getFontMetrics(font);
				gra.drawString(stringReturnToStart, 250 - (metrics.stringWidth(stringReturnToStart) / 2 + 10), 300);
				if (Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
					screen = EnumShootingtScreen.START;
				}
				break;
			}
			
			shootingFrame.panel.draw();
			
			try {
				long runTime = System.currentTimeMillis() - startTime;
				if (runTime < (1000 / fps)) {
					Thread.sleep(1000 / fps - runTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
