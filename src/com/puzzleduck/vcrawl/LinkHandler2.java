package com.puzzleduck.vcrawl;

public interface LinkHandler2
{

//  void queueLink(String link) throws Exception;

  int size();

  boolean visited(String link);

  void addVisited(String link);

}//interface
