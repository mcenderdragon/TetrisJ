/*
 * Decompiled with CFR 0.139.
 */
package dragon.tetris;

import dragon.tetris.GameControler;
import dragon.tetris.Main;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class LogGenerator {
    private OutputStream out = new GZIPOutputStream(new FileOutputStream("./teris_" + System.currentTimeMillis() / 1000L + ".t7"));
    private int rotation = 0;
    private StringBuilder builder;

    public void close() throws IOException {
        this.out.close();
    }

    public void onKeyPressed(Main.Keys key) {
        if (key == Main.Keys.UP) {
            ++this.rotation;
            this.rotation %= 4;
        }
    }

    private String currentState(int[][] data) {
        String s = "";
        for (int y = 0; y < Main.con.screenH; ++y) {
            for (int x = 0; x < Main.con.screenW; ++x) {
                s = String.valueOf(s) + Math.min(1, data[x][y]);
            }
        }
        return s;
    }

    private String currentShape(boolean[][] aktiveBlock) {
        String s = "";
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                if (x < aktiveBlock.length) {
                    if (y < aktiveBlock[x].length) {
                        s = String.valueOf(s) + (aktiveBlock[x][y] ? (char)'1' : '0');
                        continue;
                    }
                    s = String.valueOf(s) + '0';
                    continue;
                }
                s = String.valueOf(s) + '0';
            }
        }
        return s;
    }

    public void onNewShape(int[][] finishedGrid, boolean[][] aktiveBlock) {
        if (this.builder != null) {
            this.builder.append(";;\n");
            try {
                this.out.write(this.builder.toString().getBytes());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.builder = new StringBuilder();
        this.rotation = 0;
        this.builder.append(this.currentState(finishedGrid));
        this.builder.append(';');
        this.builder.append(this.currentShape(aktiveBlock));
    }

    public void shapeCollided(int xPos, int yPos) {
        this.builder.append("=" + xPos + ";" + yPos + ";" + this.rotation);
    }

    public void onRemoveLine() {
        if (this.builder != null) {
            this.builder.append('\n');
            try {
                this.out.write(this.builder.toString().getBytes());
                this.out.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.builder = null;
        }
    }

    public String getStatus() {
        return this.builder == null ? null : this.builder.toString();
    }
}
