//(c)me & gpl3
//java web crawler
package com.puzzleduck.jcrawl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.puzzleduck.jcrawl.LinkHandler;

public class JCrawl implements LinkHandler 
{

  private static JCrawl jCrawler;//
  private final Collection<String> doneLinkList = Collections.synchronizedSet(new HashSet<String>());
  private String workingUrl;
  private ExecutorService executorService;

  private JCrawl()
  {
    System.out.println("new JC");
  }

  public static void main(String args[])//
  {
 //   private static JCrawl jCrawler;
// a contracted one

    jCrawler = new JCrawl();


    if(args.length > 0)
    {
      //System.out.println("Starting with:" + args[0]);
      jCrawler.workingUrl = args[0];
    }else
    {
      //System.out.println("Please provide ");//affect change here after
      jCrawler.workingUrl = "www.slashdot.com";
    }
    System.out.println("initial url: " + jCrawler.workingUrl);
    jCrawler.executorService = Executors.newFixedThreadPool(6);
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
    startNewThread(this.workingUrl);
  }


  private void startNewThread(String link)
  {
    executorService.execute(new LinkFinder(link, this) );
  }

  @Override
  public boolean visited(String link)
  {
    return doneLinkList.contains(link);
  }

  @Override
  public int size()
  {
    return doneLinkList.size();
  }

  @Override
  public void queueLink(String link) throws Exception
  {
    startNewThread(link);
  }

  @Override
  public void addVisited(String s)
  {
    doneLinkList.add(s);
  }



}//class
