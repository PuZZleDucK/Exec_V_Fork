//(c)me & gpl3
//java web crawler
package com.puzzleduck.acrawl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import com.puzzleduck.acrawl.LinkHandler2;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;


public class ACrawl2 implements LinkHandler2 
{

  private static ACrawl2 jCrawler;//
  //private   final Collection<String> doneLinkList = Collections.synchronizedSet(new HashSet<String>());
  protected final static Collection<AppData> appLinkList = Collections.synchronizedSet(new HashSet<AppData>());
  private String workingUrl;
//  private ExecutorService executorService;
  private ForkJoinPool forkPool;

  protected static ScanPanel sPanel;

  private ACrawl2()
  {
    System.out.println("new JC");
  }

  public static void main(String args[])//
  {

//ui init:
    JFrame mainWindow = new JFrame();

    int windowHeight = 800;
    int windowWidth = 600;
    mainWindow.setBounds(0, 0, windowWidth, windowHeight);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
    mainWindow.getContentPane().add(mainPanel);

    JLabel versionLabel = new JLabel("MarketScanner: Mk.1.1-UI");
    mainPanel.add(versionLabel);
    mainWindow.setTitle("ScanWindow");

    sPanel = new ScanPanel();
    Dimension panelD = new Dimension(400,400);
    sPanel.setPreferredSize(panelD);
    mainPanel.add(sPanel);
    
    JButton startButton = new JButton("Start Crawl");
    JButton stopButton = new JButton("Stop Crawl");

    //startButton.addActionListener();
    //stopButton.addActionListener();
    mainPanel.add(startButton);
    mainPanel.add(stopButton);

    mainWindow.setVisible(true);

 //   private static JCrawl jCrawler;
// a contracted one

    jCrawler = new ACrawl2();


    if(args.length > 0)
    {
      //System.out.println("Starting with:" + args[0]);
      jCrawler.workingUrl = args[0];
    }else
    {
      //System.out.println("Please provide ");//affect change here after
      jCrawler.workingUrl = "http://play.google.com/store";//starting in android market
    }
    System.out.println("initial url: " + jCrawler.workingUrl);


 //   jCrawler.executorService = Executors.newFixedThreadPool(6);
    //jCrawler.forkPool = new ForkJoinPool(8);
    jCrawler.forkPool = new ForkJoinPool(4);//downsizing
    //executorService.execute( new LinkFinder(jCrawler.workingURL, this) );
    try
    {    
      jCrawler.startCrawl();
    }catch(Exception e)
    {
      System.out.println( "Error starting crawl: " + e.toString() );
    }
  }//main


  private void startCrawl() throws Exception
  {
//    startNewThread(this.workingUrl);
    this.forkPool.invoke(new LinkFinder2(this.workingUrl, this));
  }


//  private void startNewThread(String link)
//  {
//    executorService.execute(new LinkFinder2(link, this) );
//  }

  @Override
  //public boolean visited(String link)
  public boolean visited(AppData thisAppData)
  {
    //return doneLinkList.contains(link);
    if(appLinkList.contains(thisAppData))
    {
      return true;
    }else
    {
      System.out.println("new: " + jCrawler.workingUrl);
      return false;
    }
  }

  @Override
  public int size()
  {
    //return doneLinkList.size();
    return appLinkList.size();
  }

//  @Override
//  public void queueLink(String link) throws Exception
//  {
//    startNewThread(link);
//  }

  @Override
  //public void addVisited(String s)
  public void addVisited(AppData thisAppData)
  {
    //doneLinkList.add(s);
    appLinkList.add( thisAppData );
    sPanel.setVisible(true);
  }



}//class
