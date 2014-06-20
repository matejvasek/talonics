package org.uth.textanalytics.tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;
import org.uth.textanalytics.currency.Chunk;
import org.uth.textanalytics.currency.Item;
import org.uth.textanalytics.currency.TokenDictionary;
import org.uth.textanalytics.utils.SentenceParser;
import org.uth.textanalytics.utils.TokenDictionaryIDUtils;

/**
 *
 * @author uther
 */
public class TokenDictionaryTest2
{
  private static String readFile( String filename )
  {
    StringBuilder data = new StringBuilder();
    
    try
    {
      File targetLocation = new File( filename );
      FileInputStream inputStream = new FileInputStream(targetLocation);

      Scanner scanner = new Scanner(inputStream);
      
      //first use a Scanner to get each line
      while( scanner.hasNextLine() )
      {
        String dataComponent = scanner.nextLine();
        
        data.append( dataComponent + " " );
      }
    }
    catch( Exception exc )
    {
      System.out.println( "Exception occured during file read - " + exc.toString());
      return null;
    }
        
    return data.toString();
  }
  
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java TokenDictionaryTest1 file");
      System.exit(0);
    }
    
    String data = TokenDictionaryTest2.readFile(args[0]);
    
    if( data == null )
    {
      System.exit(0);
    }
    
    System.out.println( "Read file " + args[0]);
    
    List<String> sentences = SentenceParser.getSentences( data );
    
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
    
    System.out.println( "(Creating Chunks) Encoded sentences:" );
    
    int count = 1;
    
    Item item = new Item();
    
    for( String sentence : sentences )
    {
      Chunk chunk = new Chunk();
      
      List<String> words = SentenceParser.getWords(sentence);
      
      System.out.print( count + ": " );
      
      for( String word : words )
      {
        System.out.print( dictionary.getID(word.toLowerCase()) + " " );
        
        chunk.add( dictionary.getID(word.toLowerCase()));
      }
      
      System.out.println();
      count++;
      
      item.add(chunk);
    }
    
    // Item analysis/output
    System.out.println( "Item contains " + item.size() + " data chunks." );
    
    for( int chunkLoop = 0; chunkLoop < item.size(); chunkLoop++ )
    {
      Chunk chunk = item.get(chunkLoop);
      
      System.out.println( "Stats for chunk " + ( chunkLoop + 1 ));
      System.out.println( "  Unique ID count: " + chunk.getMembers().size());
      System.out.println( "  Frequencies:" );
      
      for( Integer member : chunk.getMembers() )
      {
        System.out.println( "    " + dictionary.get(member) + ":" + chunk.getFrequencies().get(member));
      }
    }
  }
}
