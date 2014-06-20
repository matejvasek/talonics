package org.uth.textanalytics.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This utility class contains methods for interacting with sentences.
 *
 * Simplistic utils for extracting words from sentences.
 * @author Ian Lawson
 */
public class SentenceParser
{
  // Private constructor, makes the class static
  private SentenceParser()
  {
  }

  /**
   * Sentence extraction - this method reduces the input data into sentences
   * @param input block of text to process
   * @return list of valid sentences for further processing
   */
  public static List<String> getSentences( String input )
  {
    // Simple extraction. Break the text on . and ;
    String[] components = input.split( "[.;?!]+\\s");

    List<String> sentences = Arrays.asList(components);

    return sentences;
  }

  /**
   * Get all the words from a sentence in order.
   * @param input sentence to parse.
   * @return list of identified words as-is from the sentence
   */
  public static List<String> getWords( String input )
  {
    input = input.trim();
    
    // First, replace the unnecessary annotations
    input = input.replaceAll( ",", " " );
    input = input.replaceAll( "[.]", " " );
    input = input.replaceAll( "[;]", " " );
    input = input.replaceAll( "[:]", " " );
    input = input.replaceAll( "'", "" );
    input = input.replaceAll( "\\!", "" );
    input = input.replaceAll( "\\?", "" );
    input = input.replaceAll( "-", " " );
    input = input.replaceAll( "\\s+", " " );
    input = input.replaceAll( "\\(", "");
    input = input.replaceAll( "\\)", "");

    // Now split to get the words
    String[] words = input.split( " " );

    return Arrays.asList(words);
  }
  
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java SentenceParser targetFile" );
      System.exit(0);
    }
    
    // Read the contents of the file into a bufer for processing.
    StringBuilder contents = new StringBuilder();
    
    try
    {
      File targetLocation = new File( args[0] );
      FileInputStream inputStream = new FileInputStream(targetLocation);

      Scanner scanner = new Scanner(inputStream);
      
      //first use a Scanner to get each line
      while( scanner.hasNextLine() )
      {
        String dataComponent = scanner.nextLine();
        
        contents.append( dataComponent );
        contents.append( " " );
      }
      
      scanner.close();
    }
    catch( Exception exc )
    {
      System.out.println( "Unable to process file " + args[0] + " due to " + exc.getMessage());
      System.exit(0);
    }
        
    // Extract the sentences
    List<String> sentences = SentenceParser.getSentences( contents.toString());
    
    System.out.println( "Discovered " + sentences.size() + " sentences." );
    
    int count = 1;
    
    for( String sentence : sentences )
    {
      List<String> words = SentenceParser.getWords(sentence);
      
      System.out.println( "Sentence " + count + " contains " + words.size() + " words." );
      
      for( String word : words )
      {
        System.out.print( word + " " );
      }
      System.out.println( "" );
      
      count++;
    }
  }
}

