package org.uth.textanalytics.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.uth.textanalytics.importer.NGramToDBImporter;

/**
 * Test of the DB NGram importer utility.
 * @author Ian Lawson
 */
public class DBImporterTest1
{
  public static void main( String[] args )
  {
    if( args.length != 8 )
    {
      System.err.println( "Usage: java DBImporterTest1 connectionString driverName username password storageID source cleanse(true|false) retainSpaces(true|false)");
      System.exit(0);
    }
    
    new DBImporterTest1( args[0], args[1], args[2], args[3], args[4], args[5], (args[6].equalsIgnoreCase("true")), (args[7].equalsIgnoreCase("true")) );
  }
  
  public DBImporterTest1( String connectionString, String driverClass, String username, String password, String ID, String source, boolean cleanse, boolean retainSpaces )
  {
    NGramToDBImporter importer = new NGramToDBImporter( connectionString, driverClass, username, password );
    
    List<Integer> targets = new ArrayList<>();
    
    targets.add(2);
    targets.add(3);
    targets.add(4);
    
    try
    {
      long start = System.currentTimeMillis();
      importer.importSource(source, targets, ID, cleanse, retainSpaces );
      long end = System.currentTimeMillis();
      
      System.out.println( "Attempted to write n-grams to database under ID " + ID + " in " + ( end - start ) + "ms." );
    }
    catch( SQLException | ClassNotFoundException exc )
    {
      System.err.println( "Failed to extract and write to database due to " + exc.toString());
    }      
  }
}
