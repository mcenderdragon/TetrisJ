 package dragon.tetris;
 
 import java.net.ServerSocket;
 import java.net.Socket;
import java.util.Map.Entry;

import org.omg.CosNaming.IstringHelper;
 
 public class AIConnection implements Runnable
 {
   public boolean update = false;
   
 
 
 
 
 
   public void start()
   {
     Thread t = new Thread(this, "Network Thread");
     t.start();
   }
   
 
   public void run()
   {
/*     */     try
/*     */     {
				Integer port = Integer.getInteger("dragon.tetris.port", 4404);
				System.out.println("Using Port: " + port);
/*  26 */       ServerSocket server = new ServerSocket(port);
/*  27 */       Socket client = server.accept();
/*  28 */       Main.remote = true;
				Boolean keepOpen = Boolean.getBoolean("dragon.tetris.visible");
				Main.frame.setVisible(keepOpen);
/*     */       
/*  30 */       System.out.println("Remote Connected");
/*     */       
/*  32 */       java.io.InputStream in = client.getInputStream();
/*  33 */       java.io.OutputStream out = client.getOutputStream();
/*     */       
/*  35 */       while (Main.run)
/*     */       {
/*  37 */         Thread.sleep(10L);
/*     */         
/*  39 */         if (this.update)
/*     */         {
/*  41 */           this.update = false;
/*  42 */           String grid = Main.logger.getStatus().replaceFirst(";", "") + "\n";
/*  43 */           System.out.println(grid);
/*  44 */           out.write(grid.getBytes());
/*     */           
/*  46 */           Main.doTick = false;
/*  47 */           Main.con.tick();
/*     */           
/*  49 */           int x = in.read();
/*     */           
/*  51 */           int r = in.read();
/*  52 */           System.out.println(x + " " + r);
/*     */           
/*  56 */           if (x < Main.con.xPos)
/*     */           {
/*  58 */             int delta = Main.con.xPos - x;
/*  59 */             for (int i = 0; i < delta; i++)
/*     */             {
/*  61 */               Main.con.shiftLeft();
						
/*     */             }
/*     */           }
/*  65 */           if (x > Main.con.xPos)
/*     */           {
/*  67 */             int delta = x - Main.con.xPos;
/*  68 */             for (int i = 0; i < delta; i++)
/*     */             {
/*  70 */               Main.con.shiftRight();
/*  71 */               
/*     */             }
/*     */           }
/*     */           
/*  75 */           for (int i = 0; i < r; i++)
/*     */           {
/*  77 */             Main.con.rotate();
/*  78 */          
/*     */           }
					Main.doTick = true;
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
/* 119 */       Main.remote = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void blockUpdate()
/*     */   {
/* 125 */     this.update = true;
				if(Main.remote)
				Main.doTick = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Tüp\Desktop\tetrs ai\TetrisJ v5.1.jar!\dragon\tetris\AIConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */