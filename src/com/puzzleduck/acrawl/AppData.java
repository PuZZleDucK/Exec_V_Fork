package com.puzzleduck.acrawl;


public class AppData
{

  private String appName;
  private String appUrl;
  private String appDescription;


  public AppData()
  {
    appName = "";
    appUrl = "";
  }

//  public AppData(String newName)
//  {
//    appName = newName;
//  }

  public AppData(String newName, String newUrl)
  {
    appName = newName;
    appUrl = newUrl;
  }

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj)
    {
      return true;
    }
    if( obj == null || getClass() != obj.getClass() )
    {
      return false;
    }
    
    AppData objAppData = (AppData) obj;

    if( appName.equals(objAppData.getAppName()) && appUrl.equals(objAppData.getAppUrl()) )
    {
      return true;
    }else
    {
      return false;
    }

  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((appName == null) ? 0 : appName.hashCode());
    result = prime * result + ((appUrl == null) ? 0 : appUrl.hashCode());

//cheers to for posing eclipse demo:  http://javarevisited.blogspot.com/2011/10/override-hashcode-in-java-example.html#ixzz26fJjBkpQ
    return result;
  }

  public String getAppName()
  {
    return appName;
  }

  public String getAppUrl()
  {
    return appUrl;
  }



}//class
