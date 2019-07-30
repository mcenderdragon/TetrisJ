 package dragon.tetris;
 
 import java.awt.Color;
 import java.awt.Frame;
 import java.awt.Graphics;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.awt.event.WindowEvent;
 import java.awt.event.WindowListener;
 
 
 
 
 
 
 
 
 
 
 public class TetrisFrame
   extends Frame
 {
   private static final long serialVersionUID = 2799508602273231127L;
   public static final int KEY_UP = 38;
   public static final int KEY_DOWN = 40;
   public static final int KEY_LEFT = 37;
   public static final int KEY_RIGHT = 39;
   public final int blockSize;
   public Color[][] grid;
   
   public TetrisFrame(final GameControler control)
   {
     super("Tetris");
     
     this.grid = new Color[control.screenW][control.screenH];
     setSize(375, 500);
     this.blockSize = (500 / control.screenH);
     
     addKeyListener(new KeyListener()
     {
       public void keyTyped(KeyEvent e) {}
       
 
 
 
       public void keyReleased(KeyEvent e) {}
       
 
 
       public void keyPressed(KeyEvent e)
       {
         if (Main.remote) {
           return;
         }
         switch (e.getKeyCode())
         {
         case 38: 
           control.rotate();
           break;
         case 37: 
           control.shiftLeft();
           break;
         case 39: 
           control.shiftRight();
           break;
         case 40: 
           control.putDown();
           break;
         
         }
         
       }
     });
     addWindowListener(new WindowListener()
     {
       public void windowOpened(WindowEvent paramWindowEvent) {}
       
 
 
       public void windowIconified(WindowEvent paramWindowEvent) {}
       
 
 
       public void windowDeiconified(WindowEvent paramWindowEvent) {}
       
 
       public void windowDeactivated(WindowEvent paramWindowEvent) {}
       
 
       public void windowClosing(WindowEvent paramWindowEvent)
       {
         Main.run = false;
       }
       
 
 
       public void windowClosed(WindowEvent paramWindowEvent) {}
       
 
       public void windowActivated(WindowEvent paramWindowEvent) {}
     });
     setResizable(false);
     setVisible(true);
   }
   
   private int lastScore = -1;
   
   public void render()
   {
     Graphics graph = getGraphics();
     
     for (int x = 0; x < this.grid.length; x++)
     {
       for (int y = 0; y < this.grid[x].length; y++)
       {
         if (this.grid[x][y] == null)
         {
           graph.setColor(Color.LIGHT_GRAY);
           graph.fillRect(5 + this.blockSize * x, 5 + this.blockSize * y, this.blockSize, this.blockSize);
         }
         else
         {
           graph.setColor(Color.BLACK);
           graph.fillRect(5 + this.blockSize * x, 5 + this.blockSize * y, this.blockSize, this.blockSize);
           graph.setColor(this.grid[x][y]);
           graph.fillRect(5 + this.blockSize * x + 1, 5 + this.blockSize * y + 1, this.blockSize - 2, this.blockSize - 2);
         }
       }
     }
     if (Main.score != this.lastScore)
     {
       graph.setColor(Color.WHITE);
       graph.fillRect(this.blockSize * 11, this.blockSize * 4, this.blockSize * 5, this.blockSize * 2);
       graph.setColor(Color.BLACK);
       graph.drawString("Score: " + Main.score, this.blockSize * 12, this.blockSize * 5);
       this.lastScore = Main.score;
     }
   }
 }


/* Location:              C:\Users\Tüp\Desktop\tetrs ai\TetrisJ v5.1.jar!\dragon\tetris\TetrisFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */