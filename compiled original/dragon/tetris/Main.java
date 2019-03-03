/*
 * Decompiled with CFR 0.139.
 */
package dragon.tetris;

import dragon.tetris.AIConnection;
import dragon.tetris.GameControler;
import dragon.tetris.LogGenerator;
import dragon.tetris.TetrisFrame;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static TetrisFrame frame;
    public static GameControler con;
    public static int score;
    public static LogGenerator logger;
    public static AIConnection aiServer;
    public static boolean run;
    public static boolean remote;
    public static boolean doTick;
    private static Color[] colors;

    static {
        score = 0;
        run = true;
        remote = false;
        doTick = true;
        colors = new Color[]{Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN};
    }

    public static void main(String[] args) {
        try {
            con = new GameControler(10, 30, 5);
            con.addShape(new boolean[][]{{true, true, true, true}});
            con.addShape(new boolean[][]{{true, true}, {true, true}});
            boolean[][] arrarrbl = new boolean[2][];
            boolean[] arrbl = new boolean[3];
            arrbl[0] = true;
            arrarrbl[0] = arrbl;
            arrarrbl[1] = new boolean[]{true, true, true};
            con.addShape(arrarrbl);
            boolean[][] arrarrbl2 = new boolean[2][];
            boolean[] arrbl2 = new boolean[3];
            arrbl2[2] = true;
            arrarrbl2[0] = arrbl2;
            arrarrbl2[1] = new boolean[]{true, true, true};
            con.addShape(arrarrbl2);
            boolean[][] arrarrbl3 = new boolean[2][];
            boolean[] arrbl3 = new boolean[3];
            arrbl3[1] = true;
            arrarrbl3[0] = arrbl3;
            arrarrbl3[1] = new boolean[]{true, true, true};
            con.addShape(arrarrbl3);
            boolean[][] arrarrbl4 = new boolean[2][];
            boolean[] arrbl4 = new boolean[3];
            arrbl4[0] = true;
            arrbl4[1] = true;
            arrarrbl4[0] = arrbl4;
            boolean[] arrbl5 = new boolean[3];
            arrbl5[1] = true;
            arrbl5[2] = true;
            arrarrbl4[1] = arrbl5;
            con.addShape(arrarrbl4);
            boolean[][] arrarrbl5 = new boolean[2][];
            boolean[] arrbl6 = new boolean[3];
            arrbl6[1] = true;
            arrbl6[2] = true;
            arrarrbl5[0] = arrbl6;
            boolean[] arrbl7 = new boolean[3];
            arrbl7[0] = true;
            arrbl7[1] = true;
            arrarrbl5[1] = arrbl7;
            con.addShape(arrarrbl5);
            frame = new TetrisFrame(con);
            logger = new LogGenerator();
            aiServer = new AIConnection();
            aiServer.start();
            while (run) {
                try {
                    Thread.sleep(remote ? 100 : 750);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!doTick) continue;
                con.tick();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            logger.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void update() {
        int[][] data = con.getFinishedGrid();
        for (int x = 0; x < data.length; ++x) {
            for (int y = 0; y < data[x].length; ++y) {
                Main.frame.grid[x][y] = data[x][y] == 0 ? null : colors[data[x][y] - 1];
            }
        }
        frame.render();
    }

    public static void addScore(int score) {
        System.out.println(Main.score += score);
    }

    public static enum Keys {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE;
        
    }

}
