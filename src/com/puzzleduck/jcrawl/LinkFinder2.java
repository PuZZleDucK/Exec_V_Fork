
package com.puzzleduck.jcrawl;


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

import com.puzzleduck.jcrawl.LinkHandler2;

public class LinkFinder2 extends RecursiveAction
{

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
    
    if(!thisHandler.visited(thisUrl))
    {
      try
      {
        System.out.println(".");
        System.out.print("\n" + thisHandler.size() + ": Processing: "+thisUrl);
        List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
        URL urlObject = new URL(thisUrl);
        Parser parser = new Parser(urlObject.openConnection());
        NodeList list = parser.extractAllNodesThatMatch( new NodeClassFilter(LinkTag.class) );

        //List<String> urlList = new ArrayList<String>();

        for(int i = 0; i < list.size(); i++)
        {
          LinkTag currentFoundLink = (LinkTag) list.elementAt(i);
          if(!currentFoundLink.getLink().isEmpty() && !thisHandler.visited(currentFoundLink.getLink()))
          {
            System.out.print("(" +i+ ").");
          //  urlList.add(currentFoundLink.getLink());
            actions.add(new LinkFinder2(currentFoundLink.getLink(), thisHandler));
          }
        }//for
        thisHandler.addVisited(thisUrl);

         if(thisHandler.size() >= 1500)
         {
           System.out.print("\n\n...Time: " + ((System.nanoTime()-time0)/1000000) +"sec...\n\n");
           System.exit(0);
         }

         invokeAll(actions);
         //for(String aLink : urlList)
         //{
         //  thisHandler.queueLink(aLink);
         //}

      }catch(Exception e)
      {
        System.out.print("\nhandler error: " + e.toString() );
      }

    }


  }//compute



}//class


