package dragon.tetris;

import java.net.ServerSocket;
import java.net.Socket;

public class AIConnection implements Runnable
{
	public boolean update = false;
	
	private final int port;
	private final Main main;
	
	public AIConnection(int port, Main main) 
	{
		super();
		this.port = port;
		this.main = main;
	}


	public void start()
	{
		Thread t = new Thread(this, "Network Thread");
		t.start();
	}
	
 
	public void run()
	{
/*     */     try
/*     */     {
				System.out.println("Using Port: " + port);
/*  26 */       ServerSocket server = new ServerSocket(port);
/*  27 */       Socket client = server.accept();
/*  28 */       main.remote = true;
				Boolean keepOpen = Boolean.getBoolean("dragon.tetris.visible");
				main.frame.setVisible(keepOpen);
/*     */       
/*  30 */       System.out.println("Remote Connected");
/*     */       
/*  32 */       java.io.InputStream in = client.getInputStream();
/*  33 */       java.io.OutputStream out = client.getOutputStream();
/*     */       
/*  35 */       while (main.run)
/*     */       {
/*  37 */         Thread.sleep(10L);
/*     */         
/*  39 */         if (this.update)
/*     */         {
/*  41 */           this.update = false;
/*  42 */           String grid = main.logger.getStatus().replaceFirst(";", "") + "\n";
/*  43 */           System.out.println(grid);
/*  44 */           out.write(grid.getBytes());
/*     */           
/*  46 */        	main.doTick = false;
/*  47 */           main.con.tick();
/*     */           
/*  49 */           int x = in.read();
/*     */           
/*  51 */           int r = in.read();
/*  52 */           System.out.println(x + " " + r);
/*     */           
/*  56 */           if (x < main.con.xPos)
/*     */           {
/*  58 */             int delta = main.con.xPos - x;
/*  59 */             for (int i = 0; i < delta; i++)
/*     */             {
/*  61 */               main.con.shiftLeft();
						
/*     */             }
/*     */           }
/*  65 */           if (x > main.con.xPos)
/*     */           {
/*  67 */             int delta = x - main.con.xPos;
/*  68 */             for (int i = 0; i < delta; i++)
/*     */             {
/*  70 */               main.con.shiftRight();
/*  71 */               
/*     */             }
/*     */           }
/*     */           
/*  75 */           for (int i = 0; i < r; i++)
/*     */           {
/*  77 */             main.con.rotate();
/*  78 */             
/*     */           }
					main.doTick = true;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */       client.close();
/* 114 */       server.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 118 */       e.printStackTrace();
/* 119 */       main.remote = false;
/*     */     }
	}
	
	public void blockUpdate()
	{
		this.update = true;
		if(main.remote)
			main.doTick = false;
	}
}