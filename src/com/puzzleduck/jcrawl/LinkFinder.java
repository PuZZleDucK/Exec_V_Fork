
package com.puzzleduck.jcrawl;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import com.puzzleduck.jcrawl.LinkHandler;

public class LinkFinder implements Runnable
{

  private String thisUrl;
  private LinkHandler thisHandler;

  private static final long time0 = System.nanoTime();

  public LinkFinder(String inUrl, LinkHandler inHandler)
  {
    this.thisUrl = inUrl;
    this.thisHandler = inHandler;
  }//link handler

  @Override
  public void run()
  {
    
    if(!thisHandler.visited(thisUrl))
    {
      try
      {
        System.out.println("." + thisHandler.size() + ". Processing: "+thisUrl);
        URL urlObject = new URL(thisUrl);
        Parser parser = new Parser(urlObject.openConnection());
        NodeList list = parser.extractAllNodesThatMatch( new NodeClassFilter(LinkTag.class) );

        List<String> urlList = new ArrayList<String>();

        for(int i = 0; i < list.size(); i++)
        {
          LinkTag currentFoundLink = (LinkTag) list.elementAt(i);
          if(!currentFoundLink.getLink().isEmpty() && !thisHandler.visited(currentFoundLink.getLink()))
          {
            System.out.println("  (" +i+ ")  --->  "+currentFoundLink.getLink());
            urlList.add(currentFoundLink.getLink());
          }
        }//for
        thisHandler.addVisited(thisUrl);

         if(thisHandler.size() >= 1500)
         {
           System.out.println("Time: " + (System.nanoTime()-time0));
           System.exit(0);
         }

         for(String aLink : urlList)
         {
           thisHandler.queueLink(aLink);
         }

      }catch(Exception e)
      {
        System.out.println("handler error: " + e.toString() );
      }

    }


  }//run



}//class



