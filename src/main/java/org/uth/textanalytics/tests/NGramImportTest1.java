package org.uth.textanalytics.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.importer.NGramImporter;

/**
 * NGram Importer test 1 - test over source string with 3 sizes of ngram.
 * @author Ian Lawson
 */
public class NGramImportTest1
{
  public static void main( String[] args )
  {
    if( args.length != 3 )
    {
      System.out.println( "Usage: java NGramImportTest1 'source' cleanse(true|false) retainSpaces(true|false)");
      System.exit(0);
    }
    
    new NGramImportTest1( args[0], (args[1].equalsIgnoreCase("true")), (args[2].equalsIgnoreCase("true")) );
  }
  
  public NGramImportTest1( String source, boolean cleanse, boolean retainSpaces )
  {
    List<Integer> targets = new ArrayList<>();
    
    targets.add(2);
    targets.add(3);
    targets.add(4);
    
    NGramImporter importer = new NGramImporter();
    
    importer.importSource(source, targets, cleanse, retainSpaces);
    Map<Integer,NGrams> results = importer.getNGrams();
    
    for( int size : results.keySet())
    {
      System.out.println("NGrams for size " + size );
      NGrams ngrams = results.get(size);
      
      for( String key : ngrams.getNGrams().keySet())
      {
        System.out.println( "  " + key + ":" + ngrams.getNGrams().get(key) + " Freq:" + ngrams.getFrequency(key));
      }
      
      System.out.println( "" );
    }
    
  }
}
