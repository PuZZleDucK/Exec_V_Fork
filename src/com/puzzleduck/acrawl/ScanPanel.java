package com.puzzleduck.acrawl;

import  javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*; 

public class ScanPanel extends JPanel
{

  private int drawX = 50;
  private int drawY = 50;


  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);  
    g.setColor(Color.red);  
    g.fillRect(0, 0 , 100, 100);  
      
    Iterator i = ACrawl2.appLinkList.iterator();
    while( i.hasNext() )
    {
      Object o = i.next(); 
      g.fillRect(drawX, drawY, drawX+20, drawY+20);
      drawX += 40;
      if( drawX >= 200 )
      {
        drawX = 20;
        drawY += 40;
      }

    }    


  }//paint



}//class
