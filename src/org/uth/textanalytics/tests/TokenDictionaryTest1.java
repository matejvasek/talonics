package org.uth.textanalytics.tests;

import java.util.List;
import org.uth.textanalytics.currency.TokenDictionary;
import org.uth.textanalytics.utils.SentenceParser;
import org.uth.textanalytics.utils.TokenDictionaryIDUtils;

/**
 *
 * @author uther
 */
public class TokenDictionaryTest1
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java TokenDictionaryTest1 'phrase'");
      System.exit(0);
    }
    
    String phrase = args[0];
    
    List<String> sentences = SentenceParser.getSentences(phrase);
    
    System.out.println( "Found " + sentences.size() + " sentences." );
    
    TokenDictionary dictionary = new TokenDictionary();
    TokenDictionaryIDUtils idUtils = new TokenDictionaryIDUtils( dictionary );
    
    for( String sentence : sentences )
    {
      List<String> words = SentenceParser.getWords(sentence);
      
      for( String word : words )
      {
        if( dictionary.getID(word.toLowerCase()) == null )
        {
          int nextID = idUtils.nextID();
          dictionary.add(nextID, word.toLowerCase());
        }
      }
    }
    
    System.out.println( "Dictionary size: " + dictionary.size());
    
    for( int ID : dictionary.getIDs())
    {
      System.out.println( ID + ":" + dictionary.get(ID));
    }
    
    System.out.println( "Encoded sentences:" );
    
    int count = 1;
    
    for( String sentence : sentences )
    {
      List<String> words = SentenceParser.getWords(sentence);
      
      System.out.print( count + ": " );
      
      for( String word : words )
      {
        System.out.print( dictionary.getID(word.toLowerCase()) + " " );        
      }
      
      System.out.println();
      count++;
    }
    
  }
}
