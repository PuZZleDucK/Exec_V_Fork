
package com.puzzleduck.acrawl;


import java.util.concurrent.RecursiveAction;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;


import com.puzzleduck.acrawl.LinkHandler2;
import com.puzzleduck.acrawl.HostVerifier;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class LinkFinder2 extends RecursiveAction
{

  //private static StringBuffer threadDisplay = new StringBuffer("        ");
  private String thisUrl;
  private LinkHandler2 thisHandler;

  private static final long time0 = System.nanoTime();

  public LinkFinder2(String inUrl, LinkHandler2 inHandler)
  {
    this.thisUrl = inUrl;
    this.thisHandler = inHandler;
  }//link handler

  @Override
  public void compute()
  {
   
  HostnameVerifier hostnameVerifier = new HostVerifier();
  //{ 
  //  public boolean verify(String urlHostName, SSLSession session) 
  //  { 
  //    System.out.println("Warning: URL Host: " + urlHostName + " v/s " + session.getPeerHost());
  //    return true; 
  //  } 
  //}; 
  HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);



 
    if(!thisHandler.visited(new AppData("Filler Name",thisUrl)))
    {
      try
      {
//        System.out.println("Thread: "+  Thread.currentThread().getName().charAt(22)   +"          ."); //Thread: ForkJoinPool-1-worker-1
       // System.out.println("\n" + thisHandler.size() + ": Processing: "+thisUrl);

        //System.out.println();

        int threadNumber = Integer.parseInt(Thread.currentThread().getName().charAt(22)+"");
        //for(int i = 0; i<=threadNumber; i++){System.out.print("\t");}
        //System.out.print(threadNumber);
    //    threadDisplay.setCharAt(threadNumber, '*');
    //    System.out.print("\b\b\b\b\b\b\b\b"+threadDisplay);
        List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
        URL urlObject = new URL(thisUrl);
        Parser parser = new Parser(urlObject.openConnection());
        NodeList list = parser.extractAllNodesThatMatch( new NodeClassFilter(LinkTag.class) );

        //List<String> urlList = new ArrayList<String>();

        for(int i = 0; i < list.size(); i++)
        {
//          int iChar = ((i / list.size()) * 10) %10;
//          char dChar = Character.forDigit(iChar, 10);
    //      threadDisplay.setCharAt(threadNumber, '-');
    //      System.out.print("\b\b\b\b\b\b\b\b"+threadDisplay);
          LinkTag currentFoundLink = (LinkTag) list.elementAt(i);
          if(!currentFoundLink.getLink().isEmpty() && !thisHandler.visited(new AppData("Filler Name",currentFoundLink.getLink())))//all valid links
          {
  //          System.out.print("(" +i+ ").");
          //  urlList.add(currentFoundLink.getLink());i
            if(currentFoundLink.getLink().startsWith("https://play.google.com"))//stay in play store
            {
              //if(currentFoundLink.getLink.contains("details?id="))
              
              actions.add(new LinkFinder2(currentFoundLink.getLink(), thisHandler));
              //System.out.println("\n" + thisHandler.size() + ": QUEUED: "+thisUrl);

              String appString = "details?id=";
             // System.out.println("app string:" + appString);

              if(currentFoundLink.getLink().contains(appString))//app
              {
                System.out.println( "t"+threadNumber +"-"+ thisHandler.size() + ":APP: "+currentFoundLink.getLink());
              }

            }//if in play store
          }
        }//for
        thisHandler.addVisited(new AppData("Filler Name",thisUrl));

         if(thisHandler.size() >= 100)
         {
           System.out.print("\n\n...Time: " + ((System.nanoTime()-time0)/1000000) +"sec...\n\n");
           System.exit(0);
         }

         invokeAll(actions);
         //for(String aLink : urlList)
         //{
         //  thisHandler.queueLink(aLink);
         //}

      //  threadDisplay.setCharAt(threadNumber, ' ');
      //  System.out.print("\b\b\b\b\b\b\b\b"+threadDisplay);
      }catch(Exception e)
      {
        //System.out.print("\nhandler error: " + e.toString() );
      }

    }


  }//compute



}//class


