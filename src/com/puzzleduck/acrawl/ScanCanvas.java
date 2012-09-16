package com.puzzleduck.acrawl;


import java.awt.Canvas;

import java.awt.Color;

import java.awt.Graphics;


class ScanCanvas extends Canvas
{

  public void paint(Graphics g)
  {

   // setVisible(true);
    g.setColor(new Color(255, 0, 0));
    g.drawString("ScanStart", 200, 200);


  }//paint





}//class



