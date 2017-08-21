package org.uth.textanalytics.tests;

import org.uth.textanalytics.currency.NGrams;

/**
 * Test 1 for frequency counts using the ngram object.
 * @author Ian Lawson
 */
public class NGramTest1
{
  public static void main( String[] args )
  {
    NGrams ngrams = new NGrams(1);
    
    ngrams.add("a", 10);
    ngrams.add("b", 40);
    ngrams.add("c", 50);
    
    System.out.println( "Frequency for 'a':" + ngrams.getFrequency("a"));
    System.out.println( "Frequency for 'b':" + ngrams.getFrequency("b"));
    System.out.println( "Frequency for 'c':" + ngrams.getFrequency("c"));
    System.out.println( "Total count:" + ngrams.getTotalCount());
  }
  
}
