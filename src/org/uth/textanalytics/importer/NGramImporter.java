package org.uth.textanalytics.importer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.utils.FileUtils;
import org.uth.textanalytics.utils.NGramExtractor;

/**
 * Importer and utilities for creating n-gram objects from textual input.
 * @author Ian Lawson
 */
public class NGramImporter
{
  private Map<Integer,NGrams> _storage = new HashMap<>();
  
  public NGramImporter()
  {
    
  }
  
  /**
   * Convert source file into set of ngrams based on target list of sizes.
   * @param fileName file to parse
   * @param targetNGrams set of ngrams keyed against sizes
   * @param cleanse whether to cleanse the text down to alphanumeric
   * @param retainSpaces whether to leave the spaces in place
   */
  public void importFile( String fileName, List<Integer> targetNGrams, boolean cleanse, boolean retainSpaces )
  {
    String source = FileUtils.loadString(fileName);
    
    if( source == null )
    {
      System.err.println( "Failed to extract source from file " + fileName );
    }
    else
    {
      this.importSource(source, targetNGrams, cleanse, retainSpaces);
    }
  }
  
  /**
   * Convert source string to set of ngrams defined by sizes provided.
   * @param source source string to convert
   * @param targetNGrams list of integer sizes for ngrams
   * @param cleanse whether to clean the text (reduce to alphanumeric)
   */
  public void importSource( String source, List<Integer> targetNGrams, boolean cleanse, boolean retainSpaces )
  {
    for( int target : targetNGrams )
    {
      NGrams ngrams = NGramExtractor.extract(source, target, cleanse, retainSpaces);
      
      if( ngrams == null )
      {
        System.err.println("Failed to extract ngrams of size " + target );
      }
      else
      {
        if( _storage.containsKey(target))
        {
          _storage.remove(target);
        }
        
        _storage.put(target, ngrams);
      }
    }
  }
  
  /**
   * NGram accessor.
   * @return the currently stored NGrams
   */
  public Map<Integer,NGrams> getNGrams()
  {
    return _storage;
  }
}
