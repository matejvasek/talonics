package org.uth.textanalytics.tests;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.utils.DBHandle;

/**
 * DBHandle flex test 1.
 * @author Ian Lawson
 */
public class DBHandleTest1
{
  public static void main( String[] args )
  {
    if( args.length != 5 )
    {
      System.out.println("Usage: java DBHandleTest1 connectionString driverClassName username password remove(true|false)");
      System.exit(0);
    }
    
    DBHandleTest1 dbHandleTest1 = new DBHandleTest1( args[0],args[1],args[2],args[3],args[4].equalsIgnoreCase("true"));
  }
  
  public DBHandleTest1( String connectionString, String driverClass, String username, String password, boolean remove )
  {
    System.out.println( "Params:");
    System.out.println( "Connection String: " + connectionString );
    System.out.println( "Driver Class:" + driverClass );
    
    // Step 1 - populate an Ngram database
    Map<Integer,NGrams> testInput = new HashMap<>();
    
    // Bi-grams
    NGrams bigrams = new NGrams(2);
    
    bigrams.add("AN", 10);
    bigrams.add("AS", 25);
    bigrams.add("AT",50);
    bigrams.add("AI", 11);
    
    //Tri-grams
    NGrams trigrams = new NGrams(3);
    
    trigrams.add("ABC",12);
    trigrams.add("BCD",20);
    trigrams.add("CDE",50);
    trigrams.add("DEF",100);
    trigrams.add("EFG",13);
    
    // Quad-grams
    NGrams quadgrams = new NGrams(4);
    
    quadgrams.add("RSTU",34);
    quadgrams.add("STUV",45);
    quadgrams.add("TUVW",56);
    quadgrams.add("UVWX",67);
    quadgrams.add("VWXY",78);
    quadgrams.add("WXYZ",89);
    
    testInput.put(2, bigrams);
    testInput.put(3, trigrams);
    testInput.put(4, quadgrams);
    
    DBHandle dbHandle = new DBHandle( username, password );
    
    try
    {
      dbHandle.connect(connectionString, driverClass);
      
      System.out.println("[Test] Pushing ngrams to database.");
      
      long start = System.currentTimeMillis();
      dbHandle.pushTargetSet("test", testInput);
      long end = System.currentTimeMillis();
      
      System.out.println("[Test] Data written in " + (end - start) + "ms.");
      
      //Test 2 - test reads
      System.out.println("[Test] Reading target based set from database.");
      
      start = System.currentTimeMillis();
      Map<Integer,NGrams> output = dbHandle.loadTargetSet("test");
      end = System.currentTimeMillis();
      
      System.out.println("[Test] Read " + output.keySet().size() + " ngram sets in " + (end - start) + "ms.");
      
      if( remove )
      {
        System.out.println("[Test] (Optional) removing target data from database.");
        
        start = System.currentTimeMillis();
        dbHandle.removeTargetSet("test");
        end = System.currentTimeMillis();
        
        System.out.println("Removed datasets in " + (end - start) + "ms.");
      }
    }
    catch( ClassNotFoundException | SQLException exc )
    {
      System.err.println("[Failed] Test failed due to " + exc.toString());
    }
    finally
    {
      try
      {
        dbHandle.disconnect();
      }
      catch( SQLException exc )
      {
        // Ignore, final closure.
      }
    }
  }
}
