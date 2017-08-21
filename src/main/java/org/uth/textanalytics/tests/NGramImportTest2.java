package org.uth.textanalytics.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.importer.NGramImporter;

/**
 * File based NGram test.
 * @author Ian Lawson
 */
public class NGramImportTest2
{
  public static void main( String[] args )
  {
    if( args.length != 3 )
    {
      System.err.println( "Usage: java NGramImportTest2 fileName cleanse(true|false) retainSpaces(true|false)" );
      System.exit(0);
    }
    
    new NGramImportTest2( args[0],(args[1].equalsIgnoreCase("true")),(args[2].equalsIgnoreCase("true")) );
  }
  
  public NGramImportTest2( String fileName, boolean cleanse, boolean retainSpaces )
  {
    NGramImporter importer = new NGramImporter();
    
    List<Integer> targets = new ArrayList<>();
    
    targets.add(2);
    targets.add(3);
    targets.add(4);
    
    importer.importFile(fileName, targets, cleanse, retainSpaces);
    
    Map<Integer,NGrams> ngrams = importer.getNGrams();
    
    if( ngrams.size() == 0 )
    {
      System.err.println( "Test failed, no ngrams extracted from " + fileName );
    }
    else
    {
      System.out.println( "Bi-grams (size 2) found: " + ngrams.get(2).getNGrams().size());
      
      //Comment
      NGrams bigrams = ngrams.get(2);
      
      for( String key : bigrams.getNGrams().keySet())
      {
        System.out.println( "  " + key + ":" + bigrams.getNGrams().get(key) + " " + bigrams.getFrequencyPercentage(key) + "%" );
      }
      
      System.out.println( "Tri-grams (size 3) found: " + ngrams.get(3).getNGrams().size());
      
      NGrams trigrams = ngrams.get(3);
      
      for( String key : trigrams.getNGrams().keySet())
      {
        System.out.println( "  " + key + ":" + trigrams.getNGrams().get(key) + " " + trigrams.getFrequencyPercentage(key) + "%" );
      }
    }
  }
}
