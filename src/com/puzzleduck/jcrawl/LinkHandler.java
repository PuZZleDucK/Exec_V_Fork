package com.puzzleduck.jcrawl;

public interface LinkHandler
{

  void queueLink(String link) throws Exception;

  int size();

  boolean visited(String link);

  void addVisited(String link);

}//interface
