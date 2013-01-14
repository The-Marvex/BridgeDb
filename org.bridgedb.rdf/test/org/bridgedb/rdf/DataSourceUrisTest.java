/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bridgedb.rdf;

import org.bridgedb.DataSource;
import org.bridgedb.utils.BridgeDBException;
import org.bridgedb.utils.TestUtils;
import static org.hamcrest.number.OrderingComparison.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 *
 * @author Christian
 */
public class DataSourceUrisTest extends TestUtils{
    
    public DataSourceUrisTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   /**
     * Test of getResourceId method, of class DataSourceUris.
     */
    @Test
    public void testGetResourceId() {
        report("getResourceId");
        DataSource dataSource = DataSource.getByFullName("DataSourceUrisTest_testGetResourceId");
        URI expResult = new URIImpl("http://openphacts.cs.man.ac.uk:9090//ontology/DataSource.owl#DataSource_DataSourceUrisTest_testGetResourceId");
        URI result = DataSourceUris.getResourceId(dataSource);
        assertEquals(expResult, result);
    }

    /**
     * Test of byDataSource method, of class DataSourcePlus.
     */
    @Test
    public void testByDataSource() throws BridgeDBException {
        report("byDataSource");
        DataSource dataSource = DataSource.getByFullName("DataSourceUrisTest_testByDataSource");
        DataSourceUris expResult = DataSourceUris.byDataSource(dataSource);
        DataSourceUris result = DataSourceUris.byDataSource(dataSource);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setUriParent method, of class DataSourcePlus.
     */
    @Test
    public void testSetUriParent() throws BridgeDBException {
        report("setUriParent");
        DataSource original = DataSource.getByFullName("DataSourceUrisTest_testSetUriParent1");
        DataSource parent = DataSource.getByFullName("DataSourceUrisTest_testSetUriParent2");
        DataSourceUris instance = DataSourceUris.byDataSource(original);;
        instance.setUriParent(parent);
        DataSourceUris result = instance.getUriParent();
        assertEquals(parent, result.getDataSource());
    }

    /**
     * Test of setUriParent method, of class DataSourcePlus.
     */
    @Test (expected = Exception.class)  
    public void testSetUriParentToNull() throws Exception {
        report("setUriParentToNull");
        DataSource original = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentToSelf");
        DataSourceUris instance = DataSourceUris.byDataSource(original);
        DataSource ds = null;
        instance.setUriParent(ds);
    }

    /**
     * Test of setUriParent method, of class DataSourcePlus.
     */
    @Test 
    public void testSetUriParentToSelf() throws Exception {
        report("setUriParentToSelf");
        DataSource original = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentToSelf");
        DataSourceUris instance = DataSourceUris.byDataSource(original);;
        instance.setUriParent(original);
        DataSourceUris parent = instance.getUriParent();
        assertNull(parent);
    }

    /**
     * Test of setUriParent method, of class DataSourcePlus.
     */
    @Test (expected = BridgeDBException.class)
    public void testSetUriParentChange() throws BridgeDBException {
        report("setUriParentChange");
        DataSource dataSource1 = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentChange1");
        DataSource dataSource2 = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentChange2");
        DataSource dataSource3 = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentChange3");
        DataSourceUris instance = DataSourceUris.byDataSource(dataSource1);
        instance.setUriParent(dataSource2);
        instance.setUriParent(dataSource3);
    }

    /**
     * Test of setUriParent method, of class DataSourcePlus.
     */
    @Test (expected = BridgeDBException.class)
    public void testSetUriParentCircular() throws BridgeDBException {
        report("setUriParentCircular");
        DataSource dataSource1 = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentCircular1");
        DataSource dataSource2 = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentCircular2");
        DataSourceUris instance1 = DataSourceUris.byDataSource(dataSource1);
        instance1.setUriParent(dataSource2);
        DataSourceUris instance2 = DataSourceUris.byDataSource(dataSource2);
        instance2.setUriParent(dataSource1);
    }

    /**
     * Test of setUriParent method, of class DataSourcePlus.
     */
    @Test 
    public void testSetUriParentGrandParent() throws BridgeDBException {
        report("setUriParentGrandParent");
        DataSource dataSourceChild = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentGrandParent1");
        DataSource dataSourceParent = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentGrandParent2");
        DataSource dataSourceGrandParent = DataSource.getByFullName("DataSourceUrisTest_testSetUriParentGrandParent3");
        DataSourceUris instanceChild = DataSourceUris.byDataSource(dataSourceChild);
        instanceChild.setUriParent(dataSourceParent);
        DataSourceUris instanceParent = DataSourceUris.byDataSource(dataSourceParent);
        instanceParent.setUriParent(dataSourceGrandParent);
        DataSourceUris result = instanceChild.getUriParent();
        assertEquals(dataSourceGrandParent, result.getDataSource());
    }
    
    @Test
    public void testCompare() throws BridgeDBException{
        report("compare");
        DataSource dataSource1 = 
                DataSource.register("DataSourceUrisTest_testCompare1", "DataSourceUrisTest_testCompare1").asDataSource();
        DataSource dataSource2 = 
                DataSource.register("DataSourceUrisTest_testCompare2", "DataSourceUrisTest_testCompare2").asDataSource();
        DataSource dataSource3 = DataSource.getByFullName("DataSourceUrisTest_testCompare3");
        DataSource dataSource4 = DataSource.getBySystemCode("DataSourceUrisTest_testCompare4");
        DataSource dataSource5 = DataSource.getBySystemCode("DataSourceUrisTest_testCompare5");
        DataSourceUris dataSourceUris1 = DataSourceUris.byDataSource(dataSource1);
        DataSourceUris dataSourceUris1a = DataSourceUris.byDataSource(dataSource1);
        DataSourceUris dataSourceUris2 = DataSourceUris.byDataSource(dataSource2);
        DataSourceUris dataSourceUris3 = DataSourceUris.byDataSource(dataSource3);
        DataSourceUris dataSourceUris4 = DataSourceUris.byDataSource(dataSource4);
        DataSourceUris dataSourceUris5 = DataSourceUris.byDataSource(dataSource5);
        assertEquals(0, dataSourceUris1.compareTo(dataSourceUris1a));
        assertThat(dataSourceUris1.compareTo(dataSourceUris2), lessThan(0));
        assertThat(dataSourceUris2.compareTo(dataSourceUris1), greaterThan(0));
        assertThat(dataSourceUris1.compareTo(dataSourceUris3), lessThan(0));
        assertThat(dataSourceUris3.compareTo(dataSourceUris1), greaterThan(0));
        assertThat(dataSourceUris4.compareTo(dataSourceUris1), lessThan(0));
        assertThat(dataSourceUris1.compareTo(dataSourceUris4), greaterThan(0));
        assertThat(dataSourceUris5.compareTo(dataSourceUris1), lessThan(0));
        assertThat(dataSourceUris1.compareTo(dataSourceUris5), greaterThan(0));
        assertThat(dataSourceUris2.compareTo(dataSourceUris3), lessThan(0));
        assertThat(dataSourceUris3.compareTo(dataSourceUris2), greaterThan(0));
        assertThat(dataSourceUris4.compareTo(dataSourceUris2), lessThan(0));
        assertThat(dataSourceUris2.compareTo(dataSourceUris4), greaterThan(0));
        assertThat(dataSourceUris5.compareTo(dataSourceUris2), lessThan(0));
        assertThat(dataSourceUris2.compareTo(dataSourceUris5), greaterThan(0));
        assertThat(dataSourceUris4.compareTo(dataSourceUris3), lessThan(0));
        assertThat(dataSourceUris3.compareTo(dataSourceUris4), greaterThan(0));
        assertThat(dataSourceUris5.compareTo(dataSourceUris3), lessThan(0));
        assertThat(dataSourceUris3.compareTo(dataSourceUris5), greaterThan(0));
        assertThat(dataSourceUris4.compareTo(dataSourceUris5), lessThan(0));
        assertThat(dataSourceUris5.compareTo(dataSourceUris4), greaterThan(0));
    }
}
