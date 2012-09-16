
package com.puzzleduck.vcrawl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
//import javax.net.ssl.HttpsURLConnection;



public class HostVerifier implements HostnameVerifier
{ 
//hostnameVerifier = new HostnameVerifier()
  
    public boolean verify(String urlHostName, SSLSession session)
    {
      System.out.println("Warning: URL Host: " + urlHostName + " v/s " + session.getPeerHost());
      return true;
    }
}
//  HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);



