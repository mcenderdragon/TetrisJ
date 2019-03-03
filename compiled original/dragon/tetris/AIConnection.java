/*
 * Decompiled with CFR 0.139.
 */
package dragon.tetris;

import dragon.tetris.GameControler;
import dragon.tetris.LogGenerator;
import dragon.tetris.Main;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AIConnection
implements Runnable {
    public boolean update = false;

    public void start() {
        Thread t = new Thread((Runnable)this, "Network Thread");
        t.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(4404);
            Socket client = server.accept();
            Main.remote = true;
            System.out.println("Remote Connected");
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            while (Main.run) {
                int delta;
                int i;
                Thread.sleep(10L);
                if (!this.update) continue;
                this.update = false;
                String grid = String.valueOf(Main.logger.getStatus().replaceFirst(";", "")) + "\n";
                System.out.println(grid);
                out.write(grid.getBytes());
                Main.doTick = false;
                Main.con.tick();
                int x = in.read();
                int r = in.read();
                System.out.println(String.valueOf(x) + " " + r);
                Main.doTick = true;
                if (x < Main.con.xPos) {
                    delta = Main.con.xPos - x;
                    for (i = 0; i < delta; ++i) {
                        Main.con.shiftLeft();
                        Thread.sleep(10L);
                    }
                }
                if (x > Main.con.xPos) {
                    delta = x - Main.con.xPos;
                    for (i = 0; i < delta; ++i) {
                        Main.con.shiftRight();
                        Thread.sleep(10L);
                    }
                }
                for (int i2 = 0; i2 < r; ++i2) {
                    Main.con.rotate();
                    Thread.sleep(10L);
                }
            }
            client.close();
            server.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            Main.remote = false;
        }
    }

    public void blockUpdate() {
        this.update = true;
    }
}