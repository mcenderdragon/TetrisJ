 package mcenderdragon.tetris;
 
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.util.zip.GZIPOutputStream;
 
 
 
 
public class LogGenerator
{
	private OutputStream out;
	private int rotation = 0;
	private StringBuilder builder;
	private final Main main;
	
	public LogGenerator(Main main, int port) throws FileNotFoundException, IOException 
	{
		this.out = new GZIPOutputStream(new FileOutputStream("./teris_" + System.currentTimeMillis() / 1000L+ "_" + port + ".t7"));
		this.main = main;
	}
	
	public void close() throws IOException
	{
		this.out.close();
	}
	
	public void onKeyPressed(Main.Keys key)
	{
	  if (key == Main.Keys.UP)
	  {
		rotate();
	  }
	}
	
	public void rotate()
	{
		 this.rotation += 1;
		 this.rotation %= 4;
	}
 
	private String currentState(int[][] data)
	{
	  String s = "";
	  for (int y = 0; y < main.con.screenH; y++)
	  {
		 for (int x = 0; x < main.con.screenW; x++)
		 {
			s = s + Math.min(1, data[x][y]);
		 }
	  }
	  return s;
	}
	
	private String currentShape(boolean[][] aktiveBlock)
	{
		String s = "";
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
			if (x < aktiveBlock.length)
			{
				if (y < aktiveBlock[x].length)
				{
					s = s + (aktiveBlock[x][y] ? '1' : '0');
				}
				else
				{
					s = s + '0';
				}
			}
			else
			{
				s = s + '0';
			}
			}
		}
		return s;
	}
	
	public void onNewShape(int[][] finishedGrid, boolean[][] aktiveBlock)
	{
	  if (this.builder != null)
	  {
		 this.builder.append(";;\n");
		 try
		 {
			this.out.write(this.builder.toString().getBytes());
		 }
		 catch (IOException e)
		 {
			e.printStackTrace();
		 }
	  }
	  this.builder = new StringBuilder();
	  this.rotation = 0;
	  this.builder.append(currentState(finishedGrid));
	  this.builder.append(';');
	  this.builder.append(currentShape(aktiveBlock));
	}
	
	public void shapeCollided(int xPos, int yPos)
	{
	  this.builder.append("=" + xPos + ";" + yPos + ";" + this.rotation);
	}
	
	public void onRemoveLine()
	{
	  if (this.builder != null)
	  {
		 this.builder.append('\n');
		 try
		 {
			this.out.write(this.builder.toString().getBytes());
			this.out.flush();
		 }
		 catch (IOException e)
		 {
			e.printStackTrace();
		 }
		 this.builder = null;
	  }
	}
	
	public String getStatus()
	{
	  return this.builder == null ? null : this.builder.toString();
	}
 }


/* Location:				  C:\Users\Tüp\Desktop\tetrs ai\TetrisJ v5.1.jar!\dragon\tetris\LogGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:		 0.7.1
 */