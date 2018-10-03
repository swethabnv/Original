package com.wsnweb.image;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;


public class ImageVerification
{
    private String value;
    
    public ImageVerification (OutputStream out) throws IOException
    {
        this(40,140,out);
        
    }
    public ImageVerification (int height, int width, OutputStream out) throws IOException
    {
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);        
        Random rand=new Random(System.currentTimeMillis());
        Graphics2D g = bimage.createGraphics();

        try{
	       
	        // create a random color
	        //Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	
	        // the the background to the random color to fill the 
	        // background and make it darker
	        //g.setColor(color.darker());
	       // g.fillRect(0, 0, width, height);
	        
	        // set the font
	        g.setFont(new Font("Poor Richard",Font.BOLD,32)); //arial
	        
	        // generate a random value
	        this.value = UUID.randomUUID().toString().replace("-","").substring(0,5);
	
	        int w = (g.getFontMetrics()).stringWidth(value);
	        //int d = (g.getFontMetrics()).getDescent();
	        int a = (g.getFontMetrics()).getMaxAscent();
	        
	        int x = 0, y =0;
	        
	        // randomly set the color and draw some straight lines through it
	        for (int i = 0; i < height; i += 5)
	        {
	           //g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
	          // g.drawLine(x, y + i, width, y+i);
	        }
	
	        // reset x and y
	        x=0;
	        y=0;
	        
	        // randomly set the color of the lines and just draw think at an angle
	        for (int i = 0; i < height; i += 5)
	        {
	          // g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
	        }
	        
	        // randomly set the color and make it really bright for more readability
	        //g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)).brighter().brighter());
	
	        // we need to position the text in the center of the box
	        x = width/2 - w/2;
	        y = height/2 + a/2 - 2;
	        
	        // affine transform is used to rock the text a bit
	        AffineTransform fontAT = new AffineTransform();
	        int xp = x-2;
	        // walk through each character and rotate it randomly
	        for (int c=0;c<value.length();c++)
	        {
	            // apply a random radian either left or right (left is half since it's too far back)
	            int rotate = rand.nextInt(7);
	            fontAT.rotate(rand.nextBoolean() ? Math.toRadians(rotate) : -Math.toRadians(rotate/2));
	            Font fx = new Font("Poor Richard", Font.BOLD, 32).deriveFont(fontAT); //arial
	            g.setFont(fx);
	            String ch = String.valueOf(value.charAt(c));
	            int ht = rand.nextInt(3);
	            // draw the string and move the y either up or down slightly
	            g.drawString(ch, xp, y + (rand.nextBoolean()?-ht:ht));
	            // move our pointer
	            xp+=g.getFontMetrics().stringWidth(ch) + 2;
	        }
	        // write out the PNG file
	        ImageIO.write(bimage, "png", out);
	        
	        // make sure your clean up the graphics object
	        g.dispose();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

    public String getVerificationValue ()
    {
        return this.value;
    }
}
