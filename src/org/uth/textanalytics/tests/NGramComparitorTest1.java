package org.uth.textanalytics.tests;

import java.sql.SQLException;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.utils.DBHandle;
import org.uth.textanalytics.utils.NGramComparitor;

/**
 * Test of the NGramComparitor utility class.
 * This test requires the 'Jane Austen' test database
 * set with the ngrams generated for JA_sas (public domain Sense and
 * Sensibility) and JA_pap (public domain Pride and Prejudice)
 * @author Ian Lawson
 */
public class NGramComparitorTest1
{
  public static void main( String[] args )
  {
    if( args.length != 4 )
    {
      System.err.println( "Usage: java NGramComparitorTest1 connectionString driverClass username password");
      System.exit(0);
    }
    
    new NGramComparitorTest1( args[0], args[1], args[2], args[3] );
  }
  
  public NGramComparitorTest1( String connectionString, String driverClass, String username, String password )
  {
    DBHandle dbHandle = new DBHandle( username, password );
    
    try
    {
      long readStart = System.currentTimeMillis();
      
      dbHandle.connect(connectionString, driverClass);
      
      Map<Integer,NGrams> JA_sas = dbHandle.loadTargetSet("JA_sas");
      Map<Integer,NGrams> JA_pap = dbHandle.loadTargetSet("JA_pap");
      Map<Integer,NGrams> UTH_oe = dbHandle.loadTargetSet("UTH_oe");
      
      dbHandle.disconnect();
      
      long readEnd = System.currentTimeMillis();
      
      System.out.println( "Read JA_sas, JA_pap and UTH_oe datasets read in " + (readEnd - readStart) + "ms." );
      
      // Output basic stats
      System.out.println( "Jane Austen's Sense and Sensibility (bigrams): " + JA_sas.get(2).getNGrams().size() + "/" + JA_sas.get(2).getTotalCount());
      System.out.println( "Jane Austen's Sense and Sensibility (trigrams): " + JA_sas.get(3).getNGrams().size() + "/" + JA_sas.get(3).getTotalCount());
      System.out.println( "Jane Austen's Sense and Sensibility (quadgrams): " + JA_sas.get(4).getNGrams().size() + "/" + JA_sas.get(4).getTotalCount());
      
      System.out.println( "Jane Austen's Pride and Prejudice (bigrams): " + JA_pap.get(2).getNGrams().size() + "/" + JA_pap.get(2).getTotalCount());
      System.out.println( "Jane Austen's Pride and Prejudice (trigrams): " + JA_pap.get(3).getNGrams().size() + "/" + JA_pap.get(3).getTotalCount());
      System.out.println( "Jane Austen's Pride and Prejudice (quadgrams): " + JA_pap.get(4).getNGrams().size() + "/" + JA_pap.get(4).getTotalCount());
      
      System.out.println( "Ian Lawson's The Oxford Elf (bigrams): " + UTH_oe.get(2).getNGrams().size() + "/" + UTH_oe.get(2).getTotalCount());
      System.out.println( "Ian Lawson's The Oxford Elf (trigrams): " + UTH_oe.get(3).getNGrams().size() + "/" + UTH_oe.get(3).getTotalCount());
      System.out.println( "Ian Lawson's The Oxford Elf (quadgrams): " + UTH_oe.get(4).getNGrams().size() + "/" + UTH_oe.get(4).getTotalCount());
      
      // Calculate Simple Similarity
      NGramComparitor bigramComparitorJA = new NGramComparitor( JA_sas.get(new Integer(2)), JA_pap.get(new Integer(2)));
      NGramComparitor trigramComparitorJA = new NGramComparitor( JA_sas.get(new Integer(3)), JA_pap.get(new Integer(3)));
      NGramComparitor quadgramComparitorJA = new NGramComparitor( JA_sas.get(new Integer(4)), JA_pap.get(new Integer(4)));

      NGramComparitor bigramComparitorUTH = new NGramComparitor( JA_sas.get(new Integer(2)), UTH_oe.get(new Integer(2)));
      NGramComparitor trigramComparitorUTH = new NGramComparitor( JA_sas.get(new Integer(3)), UTH_oe.get(new Integer(3)));
      NGramComparitor quadgramComparitorUTH = new NGramComparitor( JA_sas.get(new Integer(4)), UTH_oe.get(new Integer(4)));

      System.out.println( "JAsas/JApap Bigram similarity: " + bigramComparitorJA.tokenContentSimilarity());
      System.out.println( "JAsas/JApap Trigram similarity: " + trigramComparitorJA.tokenContentSimilarity());
      System.out.println( "JAsas/JApap Quadgram similarity: " + quadgramComparitorJA.tokenContentSimilarity());      

      System.out.println( "JAsas/UTHoe Bigram similarity: " + bigramComparitorUTH.tokenContentSimilarity());
      System.out.println( "JAsas/UTHoe Trigram similarity: " + trigramComparitorUTH.tokenContentSimilarity());
      System.out.println( "JAsas/UTHoe Quadgram similarity: " + quadgramComparitorUTH.tokenContentSimilarity());      

      System.out.println( "JAsas/JApap Bigram exact similarity: " + bigramComparitorJA.exactSimilarity());
      System.out.println( "JAsas/JApap Trigram exact similarity: " + trigramComparitorJA.exactSimilarity());
      System.out.println( "JAsas/JApap Quadgram exact similarity: " + quadgramComparitorJA.exactSimilarity());      

      System.out.println( "JAsas/UTHoe Bigram exact similarity: " + bigramComparitorUTH.exactSimilarity());
      System.out.println( "JAsas/UTHoe Trigram exact similarity: " + trigramComparitorUTH.exactSimilarity());
      System.out.println( "JAsas/UTHoe Quadgram exact similarity: " + quadgramComparitorUTH.exactSimilarity());      
    }
    catch( SQLException | ClassNotFoundException exc )
    {
      System.err.println( "Failed to load datasets due to " + exc.toString());
      System.exit(0);
    }
  }
}
