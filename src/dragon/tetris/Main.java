package dragon.tetris;
 
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
public class Main
{
	public TetrisFrame frame;
	public GameControler con;
	public int score = 0;
   
	public LogGenerator logger;
   
	public AIConnection aiServer;
	public boolean run = true;
	public boolean remote = false;
   
	public boolean doTick = true;
	
	public static void main(String[] args)
	{
		Integer port = Integer.getInteger("dragon.tetris.port", 4404);
		Integer amount = Integer.getInteger("dragon.tetris.amount", 1);
		
		List<Thread> list = new ArrayList<>(amount);
		for(int i=0;i<amount;i++)
		{
			int j = i+port;
			Thread t = new Thread(new Runnable()
			{	
				@Override
				public void run() 
				{
					new Main(j);
				}
			});
			t.start();
			list.add(t);
		}
		for(Thread t : list)
		{
			try 
			{
				t.join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
   
	private static Color[] colors = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN };
   
   
	public Main(int port) 
	{
		try
		{	
		   con = new GameControler(this, 10, 30, 5);
	       con.addShape(new boolean[][] { { true, true, true, true } });
	       con.addShape(new boolean[][] { { true, true }, { true, true } });
	       con.addShape(new boolean[][] { { true ,false,false}, { true, true, true } });
	       con.addShape(new boolean[][] { { false, false, true }, { true, true, true } });
	       con.addShape(new boolean[][] { { false, true ,false}, { true, true, true } });
	       con.addShape(new boolean[][] { { true, true , false}, { false, true, true } });
	       con.addShape(new boolean[][] { { false, true, true }, { true, true ,false} });
	       frame = new TetrisFrame(con, this);
	       
	       logger = new LogGenerator(this, port);
	       aiServer = new AIConnection(port, this);
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
	   frame.setVisible(false);
   }
   
   
   
   public void update()
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
     if(frame.isVisible())
    	 frame.render();
   }
   
   public void addScore(int score)
   {
     score += score;
     System.out.println(score);
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