/*
 * Decompiled with CFR 0.139.
 */
package dragon.tetris;

import dragon.tetris.GameControler;
import dragon.tetris.LogGenerator;
import dragon.tetris.Main;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TetrisFrame
extends Frame {
    private static final long serialVersionUID = 2799508602273231127L;
    public static final int KEY_UP = 38;
    public static final int KEY_DOWN = 40;
    public static final int KEY_LEFT = 37;
    public static final int KEY_RIGHT = 39;
    public final int blockSize;
    public Color[][] grid;
    private int lastScore = -1;

    public TetrisFrame(final GameControler control) {
        super("Tetris");
        this.grid = new Color[control.screenW][control.screenH];
        this.setSize(375, 500);
        this.blockSize = 500 / control.screenH;
        this.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (Main.remote) {
                    return;
                }
                switch (e.getKeyCode()) {
                    case 38: {
                        Main.logger.onKeyPressed(Main.Keys.UP);
                        control.rotate();
                        break;
                    }
                    case 37: {
                        Main.logger.onKeyPressed(Main.Keys.LEFT);
                        control.shiftLeft();
                        break;
                    }
                    case 39: {
                        Main.logger.onKeyPressed(Main.Keys.RIGHT);
                        control.shiftRight();
                        break;
                    }
                    case 40: {
                        Main.logger.onKeyPressed(Main.Keys.LEFT);
                        control.putDown();
                        break;
                    }
                }
            }
        });
        this.addWindowListener(new WindowListener(){

            @Override
            public void windowOpened(WindowEvent paramWindowEvent) {
            }

            @Override
            public void windowIconified(WindowEvent paramWindowEvent) {
            }

            @Override
            public void windowDeiconified(WindowEvent paramWindowEvent) {
            }

            @Override
            public void windowDeactivated(WindowEvent paramWindowEvent) {
            }

            @Override
            public void windowClosing(WindowEvent paramWindowEvent) {
                Main.run = false;
            }

            @Override
            public void windowClosed(WindowEvent paramWindowEvent) {
            }

            @Override
            public void windowActivated(WindowEvent paramWindowEvent) {
            }
        });
        this.setResizable(false);
        this.setVisible(true);
    }

    public void render() {
        Graphics graph = this.getGraphics();
        for (int x = 0; x < this.grid.length; ++x) {
            for (int y = 0; y < this.grid[x].length; ++y) {
                if (this.grid[x][y] == null) {
                    graph.setColor(Color.LIGHT_GRAY);
                    graph.fillRect(5 + this.blockSize * x, 5 + this.blockSize * y, this.blockSize, this.blockSize);
                    continue;
                }
                graph.setColor(Color.BLACK);
                graph.fillRect(5 + this.blockSize * x, 5 + this.blockSize * y, this.blockSize, this.blockSize);
                graph.setColor(this.grid[x][y]);
                graph.fillRect(5 + this.blockSize * x + 1, 5 + this.blockSize * y + 1, this.blockSize - 2, this.blockSize - 2);
            }
        }
        if (Main.score != this.lastScore) {
            graph.setColor(Color.WHITE);
            graph.fillRect(this.blockSize * 11, this.blockSize * 4, this.blockSize * 5, this.blockSize * 2);
            graph.setColor(Color.BLACK);
            graph.drawString("Score: " + Main.score, this.blockSize * 12, this.blockSize * 5);
            this.lastScore = Main.score;
        }
    }

}