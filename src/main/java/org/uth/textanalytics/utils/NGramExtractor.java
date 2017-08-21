package org.uth.textanalytics.utils;

import org.uth.textanalytics.currency.NGrams;

/**
 * Utility for generating a set of n-grams from a textual source.
 * @author Ian Lawson
 */
public class NGramExtractor
{
  private NGramExtractor()
  {
    
  }
  
  /**
   * Extract defined size ngrams. This involves cleaning the text to get only alphanumerics if the cleanse flag indicates, 
   * then stepping sequentially through the string extracting all x length components, and storing
   * incremental counts for the tokens.
   * @param source textual content to process
   * @param size size of n-gram (character size)
   * @param cleanse whether to produce cleansed source before generating
   * @param retainSpaces whether to retain spaces as part of the n-grams
   * @return set of ngrams against counts
   */
  public static NGrams extract( String source, int size, boolean cleanse, boolean retainSpaces )
  {
    NGrams ngrams = new NGrams(size);
    
    // Clean string
    if( cleanse )
    {
      source = TextUtilities.alphaNumeric(source,retainSpaces);
    }
    
    // Linearly process string obtaining ngrams and incrementing counts
    for( int loop = 0; loop < ( source.length() - ( size - 1 )); loop++ )
    {
      String ngram = source.substring(loop, loop + size );
      ngrams.incrementalAdd(ngram);
    }
    
    return ngrams;  
  }
}
