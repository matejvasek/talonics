package org.uth.textanalytics.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.utils.DBHandle;
import org.uth.textanalytics.utils.NGramComparitor;

/**
 * Comparitor test for two pre-loaded ngram sets.
 * @author Ian Lawson
 */
public class ComparitorTest1
{
  public static void main( String[] args )
  {
    if( args.length != 7 )
    {
      System.err.println( "Usage: java ComparitorTest1 connectionString driverClass username password id1 id2 ngramSizes(x:y)");
      System.exit(0);
    }
    
    new ComparitorTest1( args[0], args[1], args[2], args[3], args[4], args[5], args[6] );
  }
  
  public ComparitorTest1( String connectionString, String driverClass, String username, String password, String id1, String id2, String ngramSizes )
  {
    DBHandle dbHandle = new DBHandle( username, password );
    
    try
    {
      long readStart = System.currentTimeMillis();
      
      dbHandle.connect(connectionString, driverClass);
      
      Map<Integer,NGrams> set1 = dbHandle.loadTargetSet(id1);
      Map<Integer,NGrams> set2 = dbHandle.loadTargetSet(id2);
      
      dbHandle.disconnect();
      
      long readEnd = System.currentTimeMillis();
      
      System.out.println( "Read " + id1 + " and " + id2 + " datasets read in " + (readEnd - readStart) + "ms." );
      
      if( set1 == null || set1.size() == 0 )
      {
        System.out.println( "Set 1 (" + id1 + ") is empty.");
        System.exit(0);
      }
      
      if( set2 == null || set2.size() == 0 )
      {
        System.out.println( "Set2 (" + id2 + ") is empty.");
        System.exit(0);
      }

      // Do the comparisons using the required ngramSizes
      String[] sizes = ngramSizes.split("[:]");
      
      // Stats for graphing
      List<String> stats = new ArrayList<>();
      
      for( String size : sizes )
      {
        int ngramSize = 0;
        
        try
        {
          ngramSize = Integer.parseInt(size);
          
          NGrams set1Test = set1.get(ngramSize);
          NGrams set2Test = set2.get(ngramSize);
          
          System.out.println( "Comparing sized sets set1 (" + set1Test.getSize() + ") and set2 (" + set2Test.getSize() + ")");
          
          NGramComparitor comparitor = new NGramComparitor( set1Test, set2Test );
          
          System.out.println( "Token Content similarity: " + comparitor.tokenContentSimilarity());
          System.out.println( "Token Content and Frequency similarity: " + comparitor.exactSimilarity());
          
          stats.add( ngramSize + "," + comparitor.tokenContentSimilarity() + "," + comparitor.exactSimilarity());
        }
        catch( NumberFormatException exc )
        {
          System.err.println( "Size provided (" + size + ") is not a number." );
        }
      }
      
      // Output stats for graphing
      System.out.println( "Statistics for graphing:");
      
      for( String stat : stats )
      {
        System.out.println( stat );
      }
    }
    catch( ClassNotFoundException | SQLException exc )
    {
      System.err.println( "Test failed due to " + exc.toString());
    }
  }
}
