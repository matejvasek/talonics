package org.uth.textanalytics.tests;

import java.sql.SQLException;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.utils.DBHandle;

/**
 * Use of the DBHandle to load a dataset based on an ID.
 * @author Ian Lawson
 */
public class NGramDBReadTest1
{
  public static void main( String[] args )
  {
    if( args.length != 5 )
    {
      System.err.println( "Usage: java NGramDBReadTest1 connectionString driverClass username password targetID");
      System.exit(0);
    }
    
    new NGramDBReadTest1( args[0], args[1], args[2], args[3], args[4]);
  }
  
  public NGramDBReadTest1( String connectionString, String driverClass, String username, String password, String target )
  {
    DBHandle dbHandle = new DBHandle( username,password );
    
    try
    {
      dbHandle.connect(connectionString, driverClass);
      
      Map<Integer,NGrams> results = dbHandle.loadTargetSet(target);
      
      if( results.isEmpty())
      {
        System.out.println( "No ngram data stored for ID " + target );
      }
      else
      {
        System.out.println( "ID: " + target );
        
        for( int key : results.keySet())
        {
          System.out.println( "  NGrams size " + key + " count (" + results.get(key).getNGrams().size() + ")");
        }
      }
      
      dbHandle.disconnect();
    }
    catch( SQLException | ClassNotFoundException exc )
    {
      System.err.println( "Test failed due to " + exc.toString());
    }
  }
}
