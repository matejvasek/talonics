package org.uth.textanalytics.importer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.uth.textanalytics.currency.NGrams;
import org.uth.textanalytics.utils.DBHandle;

/**
 * Importer that draws information from either a string or a file and writes them to
 * an NGram database. <b>Note</b> this importer does not natively support URL access
 * as it is intended the fetching of data from a URL be a pre-process to allow for 
 * flexible methods of obtaining the source.
 * @author Ian Lawson
 */
public class NGramToDBImporter
{
  private final NGramImporter _importer = new NGramImporter();
  private DBHandle _dbHandle = null;
  private String _connectionString = null;
  private String _driverClass = null;
  
  /**
   * Default constructor.
   * @param connectionString connection string for database
   * @param driverClass driver class to preload
   * @param username username for database
   * @param password password for database
   */
  public NGramToDBImporter( String connectionString, String driverClass, String username, String password)
  {
    _connectionString = connectionString;
    _driverClass = driverClass;
    
    _dbHandle = new DBHandle( username, password );
  }

  /**
   * Import source method. This imports the target n-grams sizes and stores them in the database under
   * the provided target ID.
   * @param source source to generate ngrams from
   * @param targets list of sizes of ngram to generate
   * @param ID ID to store the ngrams against
   * @param cleanse whether to cleanse the string down to alphanumeric
   * @param retainSpaces whether to keep spaces as part of the n-grams
   * @throws SQLException if a database exception occurs during process
   * @throws ClassNotFoundException if the driver class can't be found
   */
  public void importSource( String source, List<Integer> targets, String ID, boolean cleanse, boolean retainSpaces ) throws SQLException, ClassNotFoundException
  {
    // Get the n-grams using the importer
    _importer.importSource(source, targets, cleanse, retainSpaces);
    
    // Now write the n-grams to the DB using the ID provided
    _dbHandle.connect(_connectionString, _driverClass);
    
    Map<Integer,NGrams> ngrams = _importer.getNGrams();
    
    if( !( ngrams.isEmpty()))
    {
      _dbHandle.pushTargetSet(ID, ngrams);
    }
    
    _dbHandle.disconnect();
  }
  
  /**
   * Import the contents of a file into a set of n-grams and store them in the database.
   * @param filename file name to import
   * @param targets list of sizes of n-gram to create
   * @param ID ID to store these ngrams against in the database
   * @param cleanse whether to cleanse the string down to alphanumeric
   * @param retainSpaces whether to retain spaces as part of the n-grams
   * @throws SQLException if an error occurs writing to the database
   * @throws ClassNotFoundException if the database driver class cannot be found
   */
  public void importFile( String filename, List<Integer> targets, String ID, boolean cleanse, boolean retainSpaces ) throws SQLException, ClassNotFoundException
  {
    // Get the n-grams using the importer
    _importer.importFile(filename, targets, cleanse, retainSpaces);
    
    // Now write the n-grams to the DB using the ID provided
    _dbHandle.connect(_connectionString, _driverClass);
    
    Map<Integer,NGrams> ngrams = _importer.getNGrams();
    
    if( !( ngrams.isEmpty()))
    {
      _dbHandle.pushTargetSet(ID, ngrams);
    }
    
    _dbHandle.disconnect();    
  }
  
  /**
   * Utility main class for fire and forget usage.
   * @param args arguments for the F&F usage
   */
  public static void main( String[] args )
  {
    if( args.length != 9 && args.length != 10 )
    {
      System.err.println( "Usage: java NGramToDBImporter connectionString driverClass username password ID sizes(x:y) cleanse(true|false) retainSpaces(true|false) source");
      System.err.println( "Usage: java NGramToDBImporter connectionString driverClass username password ID sizes(x:y) cleanse(true|false) retainSpaces(true|false) filename 'FILE'");
      
      System.exit(0);
    }
    
    NGramToDBImporter importer = new NGramToDBImporter( args[0], args[1], args[2], args[3] );
    
    String target = args[4];
    boolean cleanse = ( args[6].equalsIgnoreCase( "true" ));
    boolean retainSpaces = ( args[7].equalsIgnoreCase( "true" ));
    String source = args[8];
    
    String[] sizes = args[5].split("[:]");
    
    List<Integer> targetSizes = new ArrayList<>();
    
    for( String size : sizes )
    {
      targetSizes.add( Integer.parseInt(size));
    }
    
    if( args.length == 9 )
    {
      // Source import
      try
      {
        importer.importSource(source, targetSizes, target, cleanse, retainSpaces );
      }
      catch( SQLException | ClassNotFoundException exc )
      {
        System.err.println( "Exception occured during import of source " + exc.toString());
      }
    }
    
    if( args.length == 10 )
    {
      // File import
      try
      {
        importer.importFile(args[8], targetSizes, target, cleanse, retainSpaces );
      }
      catch( SQLException | ClassNotFoundException exc )
      {
        System.err.println( "Exception occured during import of filename " + args[7] + " " + exc.toString());
      }
    }
  }
}
