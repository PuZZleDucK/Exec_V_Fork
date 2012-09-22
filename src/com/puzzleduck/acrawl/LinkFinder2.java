
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

import java.net.HttpURLConnection;

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
    //custom host verifier... hint, always allow 
    HostnameVerifier hostnameVerifier = new HostVerifier();
    HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

 
    if(!thisHandler.visited(new AppData("Filler Name",thisUrl)))
    {
      try
      {

        int threadNumber = Integer.parseInt(Thread.currentThread().getName().charAt(22)+"");
        //for(int i = 0; i<=threadNumber; i++){System.out.print("\t");}
        System.out.println("thread " + threadNumber + " checking: " + thisUrl);
        
        List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
        URL urlObject = new URL(thisUrl);// remove thisURL
        System.out.println("0");

         HttpURLConnection hCon = (HttpURLConnection)urlObject.openConnection();

        System.out.println("0.1 - " + hCon.getFollowRedirects());

        //urlObject.set("https","play.google.com",80,"play.google.com:80","","/store","","http://docs.oracle.com/javase/1.4.2/docs/api/java/net/URL.html");
        //damn protected
        Parser parser = new Parser(urlObject.openConnection());
        System.out.println("1");
        NodeList list = parser.extractAllNodesThatMatch( new NodeClassFilter(LinkTag.class) );

        System.out.println("2");
        //List<String> urlList = new ArrayList<String>();

        System.out.println("thread " + threadNumber + " checking: " + thisUrl + " - link list size: " + list.size());
        for(int i = 0; i < list.size(); i++)
        {
//          int iChar = ((i / list.size()) * 10) %10;
//          char dChar = Character.forDigit(iChar, 10);
          LinkTag currentFoundLink = (LinkTag) list.elementAt(i);
          AppData appDataObject = new AppData("Filler Name",currentFoundLink.getLink());
          System.out.println("thread " + threadNumber + " checking: " + thisUrl + "pre test");
          if(!currentFoundLink.getLink().isEmpty() && !thisHandler.visited(appDataObject))//all valid links
          {
            System.out.println("thread " + threadNumber + " checking: " + thisUrl + "valid and unvisited");
          //  urlList.add(currentFoundLink.getLink());
            if(currentFoundLink.getLink().startsWith("https://play.google.com") 
               || currentFoundLink.getLink().startsWith("http://www.appbrain.com") )//stay in play store or app brain
            {
              System.out.println("thread " + threadNumber + " checking: " + thisUrl + "app store link :D");
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
        
        //update canvas
        ACrawl2.sPanel.setVisible(true);        

         if(thisHandler.size() >= 10)
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
        System.out.print("\nhandler error: " + e.toString() );
      }

    }


  }//compute



}//class


