package org.bridgedb.mysql;

import org.bridgedb.IDMapperException;
import org.bridgedb.sql.SQLAccess;
import org.bridgedb.sql.TestSqlFactory;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * 
 * @author Christian
 */
@Ignore //done by URLIstener
public class LinkListenerTest extends org.bridgedb.linkset.LinkListenerTest {
    
    @BeforeClass
    public static void setupIDMapper() throws IDMapperException{
        SQLAccess sqlAccess = TestSqlFactory.createTestSQLAccess();
        listener = new MySQLMapper(true, sqlAccess);
        System.out.println("setup");
    }
            
}