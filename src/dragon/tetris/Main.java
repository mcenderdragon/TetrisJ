 package dragon.tetris;
 
 import java.awt.Color;
 import java.io.IOException;
 
 public class Main
 {
   public static TetrisFrame frame;
   public static GameControler con;
   public static int score = 0;
   
   public static LogGenerator logger;
   
   public static AIConnection aiServer;
   public static boolean run = true;
   public static boolean remote = false;
   
   public static boolean doTick = true;
   
   public static void main(String[] args)
   {
     try
     {
       con = new GameControler(10, 30, 5);
       con.addShape(new boolean[][] { { true, true, true, true } });
       con.addShape(new boolean[][] { { true, true }, { true, true } });
       con.addShape(new boolean[][] { { true ,false,false}, { true, true, true } });
       con.addShape(new boolean[][] { { false, false, true }, { true, true, true } });
       con.addShape(new boolean[][] { { false, true ,false}, { true, true, true } });
       con.addShape(new boolean[][] { { true, true , false}, { false, true, true } });
       con.addShape(new boolean[][] { { false, true, true }, { true, true ,false} });
       frame = new TetrisFrame(con);
       
       logger = new LogGenerator();
       aiServer = new AIConnection();
       aiServer.start();
 
       while (run)
       {
         try
         {
           Thread.sleep(remote ? 1 : 750);
         }
         catch (InterruptedException e) {
           e.printStackTrace();
         }
         if (doTick) {
           con.tick();
         }
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     try
     {
       logger.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
     System.exit(0);
   }
   
   private static Color[] colors = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN };
   
   public static void update()
   {
     int[][] data = con.getFinishedGrid();
     for (int x = 0; x < data.length; x++)
     {
       for (int y = 0; y < data[x].length; y++)
       {
         if (data[x][y] == 0)
         {
           frame.grid[x][y] = null;
         }
         else
         {
           frame.grid[x][y] = colors[(data[x][y] - 1)];
         }
       }
     }
     
     frame.render();
   }
   
   public static void addScore(int score)
   {
     Main.score += score;
     System.out.println(Main.score);
   }
   
   public static enum Keys
   {
     UP,  DOWN,  LEFT,  RIGHT,  NONE;
   }
 }


/* Location:              C:\Users\Tüp\Desktop\tetrs ai\TetrisJ v5.1.jar!\dragon\tetris\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */