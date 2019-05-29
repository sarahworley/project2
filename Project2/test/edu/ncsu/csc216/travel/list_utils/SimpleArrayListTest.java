/**
 * 
 */
package edu.ncsu.csc216.travel.list_utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for SimpleArrayList
 * 
 * @author sarahworley
 *
 */
public class SimpleArrayListTest {

	private SimpleArrayList<Object> ar;

	String string1 = "happy";
	String string2 = "birth";
	String string3 = "day";
	String string4 = "to";
	String string5 = "you";
	String string6 = "happy birthday dear";
	String string7 = "sarah";
	String string8 = "happy birthday to you";
	String string9 = "happy birthday to you!";

	String string12 = null;

	/**
	 * sets up a new array list each time
	 * 
	 * @throws java.lang.Exception if unable to create
	 */
	@Before
	public void setUp() throws Exception {
		ar = new SimpleArrayList<Object>();
	}

	/**
	 * Tests the constructor
	 */
	@Test
	public void testArrayList() {
		assertTrue(ar.isEmpty());

		assertEquals(0, ar.size());

	}

	/**
	 * test for constructor
	 */
	@Test
	public void testArrayList2() {
		

		SimpleArrayList<Object> l = new SimpleArrayList<Object>(20);
		assertTrue(l.isEmpty());
		assertEquals(0, l.size());

		SimpleArrayList<Object> list = null;
		assertNull(list);
		try {
			list = new SimpleArrayList<Object>(0);
		} catch (IllegalArgumentException e) {
			assertNull(list);
		}
		try {
			list = new SimpleArrayList<Object>(-1);
		} catch (IllegalArgumentException e) {
			assertNull(list);
		}

	}

	/**
	 * Tests invalid add
	 */
	@Test
	public void testAddInvalid() {

		// invalid element
		try {
			ar.add(-5, string1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, ar.size());
		}
		// invalid element
		try {
			ar.add(20, string1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, ar.size());
		}
		// null string
		try {
			ar.add(0, string12);
			fail();
		} catch (NullPointerException e) {
			assertEquals(0, ar.size());
		}

		ar.add(0, string8);
		assertEquals(1, ar.size());
		assertEquals("happy birthday to you", ar.get(0));

		// try to add duplicate
		try {
			ar.add(1, string8);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(1, ar.size());

		}

	}

	/**
	 * tests vaild add
	 */
	@Test
	public void testAddValid() {
		ar.add(0, string1);
		assertEquals(1, ar.size());
		assertEquals("happy", ar.get(0));

		ar.add(1, string2);
		assertEquals(2, ar.size());
		assertEquals("birth", ar.get(1));

		ar.add(2, string3);
		assertEquals(3, ar.size());
		assertEquals("day", ar.get(2));

		ar.add(0, string8);
		assertEquals(4, ar.size());
		assertEquals("happy birthday to you", ar.get(0));

		ar.add(4, string4);
		assertEquals(5, ar.size());
		assertEquals("to", ar.get(4));

		ar.add(5, string5);
		assertEquals(6, ar.size());
		assertEquals("you", ar.get(5));

		ar.add(6, string9);
		assertEquals(7, ar.size());
		assertEquals("happy birthday to you!", ar.get(6));

		ar.add(7, string6);
		assertEquals(8, ar.size());
		assertEquals(7, ar.indexOf(string6));
		assertEquals("happy birthday dear", ar.get(7));

		ar.add(string7);
		assertEquals(9, ar.size());
		assertEquals("sarah", ar.get(8));

		// checks order
		assertEquals("happy birthday to you", ar.get(0));
		assertEquals("happy", ar.get(1));
		assertEquals("birth", ar.get(2));
		assertEquals("day", ar.get(3));
		assertEquals("to", ar.get(4));
		assertEquals("you", ar.get(5));
		assertEquals("happy birthday dear", ar.get(7));
		assertEquals("sarah", ar.get(8));
		assertEquals("happy birthday to you!", ar.get(6));

	}

	/**
	 * tests valid remove
	 */
	@Test
	public void testRemoveValid() {
		assertTrue(ar.isEmpty());
		ar.add(0, string1);
		ar.add(1, string2);
		ar.add(2, string3);
		ar.add(3, string4);
		ar.add(4, string5);
		ar.add(5, string6);
		ar.add(6, string7);
		ar.add(7, string8);
		ar.add(8, string9);
		assertFalse(ar.isEmpty());

		ar.remove(7);
		assertEquals(8, ar.size());
		assertEquals("happy birthday to you!", ar.get(7));

		ar.remove(7);
		assertEquals(7, ar.size());
		assertEquals("sarah", ar.get(6));

	}

	/**
	 * tests invalid remove
	 */
	@Test
	public void testRemoveInvalid() {

		ar.add(0, string1);
		assertEquals(0, ar.indexOf(string1));
		ar.add(1, string2);

		ar.add(2, string3);

		ar.add(3, string4);
		ar.add(4, string5);
		ar.add(5, string6);
		ar.add(6, string7);
		ar.add(7, string8);
		ar.add(8, string9);

		try {
			ar.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(9, ar.size());
		}

		try {
			ar.remove(10);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(9, ar.size());
		}

	}
	
	/**
	 * tests contains
	 */
	@Test
	public void testConatains() {
		ar.add(0, string1);
		assertEquals(0, ar.indexOf(string1));
		ar.add(1, string2);
		ar.add(2, string3);
		ar.add(3, string4);
		
		assertTrue(ar.contains(string4));
		assertTrue(ar.contains(string1));
		assertTrue(ar.contains(string2));
		assertTrue(ar.contains(string3));
		assertEquals(3, ar.indexOf(string4));
		assertEquals(-1, ar.indexOf(string6));
		assertEquals(-1, ar.indexOf(string8));
		assertEquals(-1, ar.indexOf(string7));
	}

	/**
	 * tests get
	 */
	@Test
	public void testGet() {
		ar.add(0, string1);
		try {
			ar.get(10);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(1, ar.size());
		}
		try {
			ar.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(1, ar.size());
		}
		
	}
	/**
	 * tests grow
	 */
	@Test
	public void testGrowArray() {
		ar.add(0, "1");
		ar.add(1, "2");
		ar.add(2, "3");
		ar.add(3, "4");
		ar.add(4, "5");
		ar.add(5, "6");
		ar.add(6, "7");
		ar.add(7, "8");
		ar.add(8, "9");
		ar.add(9, "10");
		ar.add(10, "11");
		ar.add(11, "12");
		assertEquals(12, ar.size()); // size = 12 array will grow
		// grew
		ar.add(12, "13");
		ar.add(13, "14");
		assertEquals(14, ar.size());

	}

}
