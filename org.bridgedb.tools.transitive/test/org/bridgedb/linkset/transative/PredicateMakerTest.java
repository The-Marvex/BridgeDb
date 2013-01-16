// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright 2006-2009  BridgeDb developers
// Copyright 2012-2013  Christian Y. A. Brenninkmeijer
// Copyright 2012-2013  OpenPhacts
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.bridgedb.linkset.transative;

import java.util.ArrayList;
import org.bridgedb.tools.metadata.constants.OwlConstants;
import org.bridgedb.tools.metadata.constants.SkosConstants;
import org.bridgedb.utils.TestUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.model.URI;

/**
 *
 * @author Christian
 */
public class PredicateMakerTest extends TestUtils{
    
    private static ArrayList<URI> equivelentPredicates;
    private static ArrayList<URI> skosPredicates;
    private static ArrayList<URI> rankedPredicates;
    private static ArrayList<URI> otherPredicates;
    
    public PredicateMakerTest() {
   }
    
    @BeforeClass
    public static void setUpClass() {
        equivelentPredicates = new ArrayList<URI>();
        equivelentPredicates.add(OwlConstants.SAME_AS);
        equivelentPredicates.add(OwlConstants.EQUIVALENT_CLASS);
        skosPredicates = new ArrayList<URI>();
        skosPredicates.add(SkosConstants.EXACT_MATCH);
        skosPredicates.add(SkosConstants.CLOSE_MATCH);
        skosPredicates.add(SkosConstants.MAPPING_RELATION);
        rankedPredicates = new ArrayList<URI>(equivelentPredicates);
        rankedPredicates.addAll(skosPredicates);
        otherPredicates = new ArrayList<URI>();
        otherPredicates.add(SkosConstants.MAPPING_RELATION);
        otherPredicates.add(SkosConstants.RELATED_MATCH);
        otherPredicates.add(SkosConstants.BROAD_MATCH);
        otherPredicates.add(SkosConstants.NARROW_MATCH);
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
     * Test of combine method, of class PredicateMaker.
     */
    @Test
    public void testCombineOrder() throws Exception {
        report("combine Order");
        for (int i = 0; i< rankedPredicates.size(); i++) {
            for (int j = 0; j< rankedPredicates.size(); j++){
                URI result = PredicateMaker.combine(rankedPredicates.get(i), rankedPredicates.get(j));
                if (i < j){
                    assertEquals(rankedPredicates.get(j), result);
                } else {
                     assertEquals(rankedPredicates.get(i), result);
                }
            }
        }
    }

   /**
     * Test of combine method, of class PredicateMaker.
     */
    @Test
    public void testOtherWithEquivellent() throws Exception {
        report("combine Order with equivelent");
        for (int i = 0; i< equivelentPredicates.size(); i++) {
            for (int j = 0; j< otherPredicates.size(); j++){
                URI result = PredicateMaker.combine(equivelentPredicates.get(i), otherPredicates.get(j));
                assertEquals(otherPredicates.get(j), result);
                result = PredicateMaker.combine(otherPredicates.get(j), equivelentPredicates.get(i));
                assertEquals(otherPredicates.get(j), result);
            }
        }
    }
    
   /**
     * Test of combine method, of class PredicateMaker.
     */
    @Test
    public void testOtherWithSkos() throws Exception {
        report("combine Order with skos");
        for (int i = 0; i< skosPredicates.size(); i++) {
            for (int j = 0; j< otherPredicates.size(); j++){
                URI result = PredicateMaker.combine(skosPredicates.get(i), otherPredicates.get(j));
                assertEquals(SkosConstants.MAPPING_RELATION, result);
                result = PredicateMaker.combine(otherPredicates.get(j), skosPredicates.get(i));
                assertEquals(SkosConstants.MAPPING_RELATION, result);
            }
        }
    }

    /**
     * Test of combine method, of class PredicateMaker.
     */
    @Test
    public void testOtherWithOther() throws Exception {
        report("combine Other with Other");
        for (int i = 0; i< otherPredicates.size(); i++) {
            for (int j = 0; j< otherPredicates.size(); j++){
                URI result = PredicateMaker.combine(otherPredicates.get(i), otherPredicates.get(j));
                if (i == j){
                    assertEquals(otherPredicates.get(i), result);                
                } else {
                    assertEquals(SkosConstants.MAPPING_RELATION, result);
                }
            }
        }
    }
}
