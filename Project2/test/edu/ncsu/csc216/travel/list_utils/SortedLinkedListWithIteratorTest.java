/**
 * 
 */
package edu.ncsu.csc216.travel.list_utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for SortedLinkedListWithIterator
 * 
 * @author sarahworley
 * 
 *
 */
public class SortedLinkedListWithIteratorTest {

	private SortedLinkedListWithIterator<String> ar;

	String string1 = "a";
	String string2 = "b";
	String string3 = "c";
	String string4 = "d";
	String string5 = "e";
	String string6 = "f";
	String string7 = "g";
	String string8 = "h";
	String string9 = "i";

	String string12 = null;

	/**
	 * sets up a new array list each time
	 * 
	 * @throws java.lang.Exception if unable to create
	 */
	@Before
	public void setUp() throws Exception {
		ar = new SortedLinkedListWithIterator<String>();
	}

	/**
	 * Tests the constructor
	 */
	@Test
	public void testLinkedList() {

		assertEquals(0, ar.size());

	}

	/**
	 * Tests add
	 */
	@Test
	public void testAdd() {
		ar.add(string1);
		ar.add(string2);
		ar.add(string3);
		assertEquals(3, ar.size());
	}
	
	

	/**
	 * Tests add
	 */
	@Test
	public void testAdd2() {
		ar.add(string1);
		ar.remove(0);
		ar.add(string1);
		
		assertEquals(1, ar.size());
	}

	/**
	 * tests sorted add
	 */
	@Test
	public void testAddSorted() {
		ar.add(string1);
		ar.add(string3);
		ar.add(string2);

		assertEquals(string1, ar.get(0));
		assertEquals(string2, ar.get(1));
		assertEquals(string3, ar.get(2));
		try {
			ar.add(string2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(3, ar.size()); 
		
		}
	}

	/**
	 * tests remove
	 */
	@Test
	public void testRemove() {
		ar.add(string1);
		ar.add(string3);
		ar.add(string2);
		ar.add(string4);
		ar.add(string5);
	
		assertEquals(5, ar.size());
		String r1 = ar.remove(3);
	
		assertEquals("d" , r1);
		assertEquals(4, ar.size());
		
		String r2 = ar.remove(0);
		
		assertEquals("a" , r2);
		
		assertEquals(3, ar.size());
		String r3 = ar.remove(2);
		assertEquals("e" , r3);
	

		assertEquals(2, ar.size());
		
		String r4 =  ar.remove(0);
		assertEquals("b" , r4);
		
		String r5 =  ar.remove(0);
		assertEquals("c" , r5);
		
		assertEquals(0, ar.size());
		
		try {
			ar.remove(10);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, ar.size());

		}
		try {
			ar.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, ar.size());

		}
		
		
	}
	/**
	 * tests remove
	 */
	@Test
	public void testRemove2() {
		ar.add(string1);
		ar.add(string2);
		String r1 = ar.remove(1);
		String r2 = ar.remove(0);
		ar.add(string1);
		ar.add(string2);
		String r3 = ar.remove(0);
		String r4 = ar.remove(0);
		assertEquals("b" , r1);
		assertEquals("a" , r2);
		assertEquals("a" , r3);
		assertEquals("b" , r4);
	}

	/**
	 * tests get
	 */
	@Test
	public void testGet() {
		ar.add(string1);
		ar.add(string3);
		ar.add(string2);
		ar.add(string5);
		ar.add(string4);
		ar.add(string7);
		ar.add(string8);

		assertEquals(string1, ar.get(0));
		assertEquals(string2, ar.get(1));
		assertEquals(string3, ar.get(2));

		assertEquals("[a, b, c, d, e, g, h]", ar.toString());
		try {
			ar.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(7, ar.size());
		}
		try {
			ar.get(10);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(7, ar.size());
		}
		try {
			ar.get(7);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(7, ar.size());
		}
	}

	/**
	 * test string
	 */
	@Test
	public void testString() {
		ar.add(string1);
		ar.add(string3);
		ar.add(string2);

		assertEquals("[a, b, c]", ar.toString());
		assertEquals(0, ar.indexOf(string1));
		assertEquals(1, ar.indexOf(string2));
		assertEquals(2, ar.indexOf(string3));
		assertEquals(-1, ar.indexOf(string5));
	}

}
