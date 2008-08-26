/*
 * Copyright 2006-2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.enunciate.modules.rest;

import junit.framework.TestCase;
import org.codehaus.enunciate.rest.annotations.VerbType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan Heaton
 */
public class TestRESTOperation extends TestCase {

  /**
   * tests the "ping" method as a REST operation.
   */
  public void testPing() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("ping"), null);
    assertEquals(VerbType.read, operation.getVerb());
    assertNull(operation.getProperNounType());
    assertNull(operation.getNounValueType());
    assertTrue(operation.getAdjectiveTypes().isEmpty());
    assertNull(operation.getResultType());
  }

  /**
   * tests the "properNoun" method as a REST operation.
   */
  public void testProperNoun() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("properNoun", String.class), null);
    assertEquals(VerbType.read, operation.getVerb());
    assertNull(operation.getNounValueType());
    assertTrue(operation.getAdjectiveTypes().isEmpty());

    Class properNounType = operation.getProperNounType();
    assertNotNull(properNounType);
    assertEquals(String.class, properNounType);
    assertEquals(RootElementExample.class, operation.getResultType());
  }

  /**
   * tests the "badProperNoun" method as a REST operation.
   */
  public void testBadProperNoun() throws Exception {
    try {
      new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("badProperNoun", String[].class), null);
      fail("shouldn't have allowed a non-simple proper noun type.");
    }
    catch (IllegalStateException e) {

    }
  }

  /**
   * tests the "twoProperNouns" method as a REST operation.
   */
  public void testTwoProperNouns() throws Exception {
    try {
      new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("twoProperNouns", String.class, String.class), null);
      fail("shouldn't have allowed two proper nouns.");
    }
    catch (IllegalStateException e) {

    }
  }

  /**
   * tests the "nounValue" method as a REST operation.
   */
  public void testNounValue() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.update, RESTOperationExamples.class.getMethod("nounValue", RootElementExample.class), null);
    assertEquals(VerbType.update, operation.getVerb());
    assertNull(operation.getProperNounType());
    assertTrue(operation.getAdjectiveTypes().isEmpty());

    Class nounValueType = operation.getNounValueType();
    assertNotNull(nounValueType);
    assertEquals(RootElementExample.class, nounValueType);
    assertEquals(RootElementExample.class, operation.getResultType());
  }

  /**
   * tests the "twoNounValues" method as a REST operation.
   */
  public void testTwoNounValues() throws Exception {
    try {
      new RESTOperation(null, "text/xml", VerbType.update, RESTOperationExamples.class.getMethod("twoNounValues", RootElementExample.class, RootElementExample.class), null);
      fail("shouldn't have allowed two noun values.");
    }
    catch (IllegalStateException e) {

    }
  }

  /**
   * tests the "defaultAdjectives" method as a REST operation.
   */
  public void testDefaultAdjectives() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("defaultAdjectives", String.class, Double.TYPE), new String[]{null, "secondAdjective"});
    assertEquals(VerbType.read, operation.getVerb());
    assertNull(operation.getProperNounType());
    assertNull(operation.getNounValueType());
    assertNull(operation.getResultType());

    Map<String, Class> adjectiveTypes = operation.getAdjectiveTypes();
    assertEquals(2, adjectiveTypes.size());
    assertTrue(adjectiveTypes.containsKey("arg0"));
    assertEquals(String.class, adjectiveTypes.get("arg0"));
    assertTrue(adjectiveTypes.containsKey("secondAdjective"));
    assertEquals(Double.TYPE, adjectiveTypes.get("secondAdjective"));
  }

  /**
   * tests the "customAdjectives" method as a REST operation.
   */
  public void testCustomAdjectives() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("customAdjectives", String.class, Double.TYPE), null);
    assertEquals(VerbType.read, operation.getVerb());
    assertNull(operation.getProperNounType());
    assertNull(operation.getNounValueType());
    assertNull(operation.getResultType());

    Map<String, Class> adjectiveTypes = operation.getAdjectiveTypes();
    assertEquals(2, adjectiveTypes.size());
    assertTrue(adjectiveTypes.containsKey("howdy"));
    assertEquals(String.class, adjectiveTypes.get("howdy"));
    assertTrue(adjectiveTypes.containsKey("doody"));
    assertEquals(Double.TYPE, adjectiveTypes.get("doody"));
  }

  /**
   * tests the "adjectivesAsLists" method as a REST operation.
   */
  public void testAdjectivesAsLists() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("adjectivesAsLists", boolean[].class, Collection.class), null);
    assertEquals(VerbType.read, operation.getVerb());
    assertNull(operation.getProperNounType());
    assertNull(operation.getNounValueType());
    assertNull(operation.getResultType());

    Map<String, Class> adjectiveTypes = operation.getAdjectiveTypes();
    assertEquals(2, adjectiveTypes.size());
    assertTrue(adjectiveTypes.containsKey("bools"));
    assertEquals(boolean[].class, adjectiveTypes.get("bools"));
    assertTrue(adjectiveTypes.containsKey("ints"));
    assertEquals(Integer[].class, adjectiveTypes.get("ints"));
  }

  /**
   * Tests the invoke operation.
   */
  public void testInvoke() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("invokeableOp", RootElementExample.class, String.class, Float.class, Collection.class), null);
    HashMap<String, Object> adjectives = new HashMap<String, Object>();
    adjectives.put("hi", new Float(1234.5));
    adjectives.put("arg1", "adjective1Value");
    adjectives.put("arg3", new Short[] {8, 7, 6});
    RootElementExample ex = new RootElementExample();
    assertSame(ex, operation.invoke(null, new HashMap<String, Object>(), adjectives, ex, new RESTOperationExamples()));
  }

  /**
   * Tests the invoke2 operation.
   */
  public void testInvoke2() throws Exception {
    RESTOperation operation = new RESTOperation(null, "text/xml", VerbType.read, RESTOperationExamples.class.getMethod("invokeableOp2", RootElementExample.class, String.class, Float.class), null);
    HashMap<String, Object> adjectives = new HashMap<String, Object>();
    adjectives.put("hi", new Float(1234.5));
    adjectives.put("ho", new Float(888.777));
    RootElementExample ex = new RootElementExample();
    assertSame(ex, operation.invoke("properNounValue", new HashMap<String, Object>(), adjectives, ex, new RESTOperationExamples()));
    assertNull(operation.invoke(null, new HashMap<String, Object>(), adjectives, ex, new RESTOperationExamples()));
    adjectives.remove("hi");
    Object differentEx = operation.invoke("properNounValue", new HashMap<String, Object>(), adjectives, ex, new RESTOperationExamples());
    assertNotNull(differentEx);
    assertFalse(differentEx == ex);
  }

}
