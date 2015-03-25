package org.uth.textanalytics.tests;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Quick test of Boilerplate functionality.
 * @author Ian Lawson
 */
public class BoilerplateTest1
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.err.println( "Usage: java BoilerplateTest1 url" );
      System.exit(0);
    }
    
    new BoilerplateTest1( args[0] );
  }
  
  public BoilerplateTest1( String targetURL )
  {
    try
    {
      URL url = new URL( targetURL );
      
      String contents = ArticleExtractor.INSTANCE.getText(url);
      
      System.out.println( contents );
    }
    catch( MalformedURLException | BoilerpipeProcessingException exc )
    {
      System.err.println( "Malformed URL : " + targetURL );
    }
  }
}
