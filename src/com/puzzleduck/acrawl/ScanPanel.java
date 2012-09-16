package com.puzzleduck.acrawl;

import  javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class ScanPanel extends JPanel
{

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);  
    g.setColor(Color.red);  
    g.fillRect(0, 0 , 100, 100);  
      



  }//paint



}//class
