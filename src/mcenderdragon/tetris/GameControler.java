/*
 * Decompiled with CFR 0.139.
 */
package mcenderdragon.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameControler {
	public final int screenW;
	public final int screenH;
	public final int maxTypes;
	public int xPos = 0;
	public int yPos = 0;
	private ArrayList<boolean[][]> blocks = new ArrayList<>();
	private boolean[][] aktiveBlock;
	private int type = 0;
	private int[][] finishedGrid;
	private final Main main;

	private Random r;

	public GameControler(Main main, int weidth, int height, int maxtypes) {
		this.screenW = weidth;
		this.screenH = height;
		this.finishedGrid = new int[weidth][height];
		this.maxTypes = maxtypes;
		this.main = main;

		r = new Random(Long.getLong("dragon.tetris.seed", System.nanoTime()));
	}

	public void addShape(boolean[][] shape) {
		this.blocks.add(shape);
	}

	public void shiftLeft() {
		if (this.type == 0) {
			return;
		}
		if (--this.xPos < 0) {
			this.xPos = 0;
		}
		if (this.isInsideBlocks()) {
			++this.xPos;
		}
		main.update();
	}

	public void shiftRight() {
		if (this.type == 0) {
			return;
		}
		int m = ++this.xPos + this.aktiveBlock.length;
		if ((m) > this.screenW) {
			this.xPos = this.screenW - this.aktiveBlock.length;
		}
		if (this.isInsideBlocks()) {
			--this.xPos;
		}
		main.update();
	}

	public void rotate() {
		if (this.type == 0) {
			return;
		}
		main.logger.rotate();
		this.aktiveBlock = GameControler.rotateAround(this.aktiveBlock.length, this.aktiveBlock[0].length,
				this.aktiveBlock);
		while (this.xPos + this.aktiveBlock.length > this.screenW) {
			--this.xPos;
		}
		main.update();
	}

	public boolean putDown() {
		if (this.type == 0) {
			return false;
		}
		++this.yPos;
		for (int x = 0; x < this.aktiveBlock.length; ++x) {
			for (int y = 0; y < this.aktiveBlock[x].length; ++y) {
				if (!this.aktiveBlock[x][y])
					continue;
				int h = y + this.yPos;
				int w = x + this.xPos;
				if (h >= this.finishedGrid[w].length) {
					--this.yPos;
					return false;
				}
				if (this.finishedGrid[w][h] == 0)
					continue;
				--this.yPos;
				return false;
			}
		}
		main.update();
		return true;
	}

	public void tick() 
	{
		main.logger.onKeyPressed(Main.Keys.NONE);
		if (this.type == 0) {
			this.initNewShape();
			this.xPos = this.screenW / 2;
			this.yPos = 0;
			if (this.isInsideBlocks()) {
				throw new IllegalStateException("Game Over!");
			}
			main.addScore(1);
			return;
		}
		if (!this.putDown()) 
		{
			for (int x = 0; x < this.aktiveBlock.length; ++x) 
			{
				for (int y = 0; y < this.aktiveBlock[x].length; ++y) 
				{
					if (!this.aktiveBlock[x][y])
						continue;
					int w = x + this.xPos;
					int h = y + this.yPos;
					if(h < this.screenH)
						this.finishedGrid[w][h] = this.type;
				}
			}
			main.logger.shapeCollided(this.xPos, this.yPos);
			this.aktiveBlock = null;
			this.type = 0;
		}
		this.applyGravity();
		this.removeFullLines();
		main.update();
	}

	public void initNewShape() 
	{
		int rand = r.nextInt(this.blocks.size());
		this.aktiveBlock = this.blocks.get(rand);
		this.type = 1 + (int) (r.nextInt(maxTypes));
		int rot = r.nextInt(4);
		for (int i = 0; i < rot; ++i) 
		{
			this.aktiveBlock = GameControler.rotateAround(this.aktiveBlock.length, this.aktiveBlock[0].length, this.aktiveBlock);
		}
		main.logger.onNewShape(this.finishedGrid, this.aktiveBlock);
		main.aiServer.blockUpdate();
	}

	public void removeFullLines() {
		for (int y = 0; y < this.screenH; ++y) {
			int x;
			boolean full = true;
			for (x = 0; x < this.screenW; ++x) {
				if (this.finishedGrid[x][y] != 0)
					continue;
				full = false;
				break;
			}
			if (!full)
				continue;
			for (x = 0; x < this.screenW; ++x) {
				this.finishedGrid[x][y] = 0;
			}
			main.addScore(100);
			main.logger.onRemoveLine();
		}
	}

	public void applyGravity() {
		for (int y = this.screenH - 1; y >= 0; --y) {
			int x;
			boolean lineEmpty = true;
			for (x = 0; x < this.screenW; ++x) {
				if (this.finishedGrid[x][y] == 0)
					continue;
				lineEmpty = false;
				break;
			}
			if (!lineEmpty || y <= 0)
				continue;
			for (x = 0; x < this.screenW; ++x) {
				this.finishedGrid[x][y] = this.finishedGrid[x][y - 1];
				this.finishedGrid[x][y - 1] = 0;
			}
		}
	}

	public boolean isInsideBlocks() {
		for (int x = 0; x < this.aktiveBlock.length; ++x) {
			for (int y = 0; y < this.aktiveBlock[x].length; ++y) {
				int w = x + this.xPos;
				int h = y + this.yPos;
				if (!this.aktiveBlock[x][y] || this.finishedGrid[w][h] == 0)
					continue;
				return true;
			}
		}
		return false;
	}

	public int[][] getFinishedGrid() {
		int x;
		int[][] done = new int[this.screenW][this.screenH];
		for (x = 0; x < done.length; ++x) {
			done[x] = Arrays.copyOf(this.finishedGrid[x], this.screenH);
		}
		if (this.type != 0) {
			for (x = 0; x < this.aktiveBlock.length; ++x) {
				for (int y = 0; y < this.aktiveBlock[x].length; ++y) {
					if (!this.aktiveBlock[x][y])
						continue;
					int w = x + this.xPos;
					int h = y + this.yPos;
					if (h >= this.screenH)
						break;
					done[w][h] = this.type;
				}
			}
		}
		return done;
	}

	public static boolean[][] rotateAround(int w, int h, boolean[][] shape) {
		boolean[][] rotated = new boolean[h][w];
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				boolean base = shape[x][y];
				rotated[h - y - 1][x] = base;
			}
		}
		return rotated;
	}
}