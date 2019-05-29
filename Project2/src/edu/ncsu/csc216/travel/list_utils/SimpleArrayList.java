package edu.ncsu.csc216.travel.list_utils;

/**
 * Implements the SimpleList interface with an array data structure.
 * 
 * @author sarahworley
 * @param <E> general
 *
 */
public class SimpleArrayList<E> implements SimpleList<E> {
	/** initial length of the array is 12 */
	private static final int RESIZE = 12;
	/** object array */
	private Object[] list;
	/** size of the list */
	private int size;

	/**
	 * creates an empty SimpleArrayList (e.g., size is zero) with a list of capacity
	 * RESIZE
	 */
	@SuppressWarnings("unchecked")
	public SimpleArrayList() {
		size = 0;
		list = (E[]) new Object[RESIZE];
	}

	/**
	 * Constuctor constructs an empty array list
	 * 
	 * @param x of size x
	 */
	public SimpleArrayList(int x) {
		if (x <= 0) {
			throw new IllegalArgumentException();
		}
		list = new Object[x];

	}

	/**
	 * Returns the number of elements in this list or Integer.MAX_VALUE if the list
	 * has more than Integer.MAX_VALUE elements.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		if (size > Integer.MAX_VALUE) {
			size = Integer.MAX_VALUE;
		}
		return size;
	}

	/**
	 * Does this list contain elements?
	 *
	 * @return true if and only if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Does this list contains the specified element?
	 *
	 * @param e element whose presence in this list is to be tested
	 * @return true if this list contains the specified element e
	 */
	@Override
	public boolean contains(E e) {
		for (int i = 0; i < size(); i++) {
			if (list[i].equals(e)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds the specified element to the end of the list.
	 *
	 * @param e element to be appended to this list
	 * @return true (as specified by 
	 * @throws IllegalArgumentException if the list already contains e
	 */
	@Override
	public boolean add(E e) {
		if (contains(e)) {
			throw new IllegalArgumentException();

		}
		add(size, e);
		return true;
	}

	/**
	 * Inserts the specified element at the specified position in this list. Shifts
	 * any element currently at that position plus all subsequent elements to the
	 * right (adds one to their indexes).
	 *
	 * @param pos index at which the specified element is to be inserted
	 * @param e   element to be inserted
	 * @throws NullPointerException      if the specified element is null and this
	 *                                   list does not permit null elements
	 * @throws IllegalArgumentException  if some property of the specified element
	 *                                   prevents it from being added to this list
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 */
	@Override
	public void add(int pos, E e) {

		// checks for null
		if (e == null) {
			throw new NullPointerException();
		}
		// checks for duplicate
		if (contains(e)) {
			throw new IllegalArgumentException();
		}
		// checks for out of bounds index
		if (pos < 0 || pos > size()) {
			throw new IndexOutOfBoundsException();
		}
		// If at the end
		if (pos == size()) {
			list[pos] = (E) e;
			// Beginning or middle
		} else {
			for (int i = size(); i > pos; i--) {
				list[i] = list[i - 1];
			}
			list[pos] = (E) e;
		}
		size++;

		if (size() == list.length) {
			growArray();
		}
	}

	/**
	 * Removes the element at the specified position in this list, shifting any
	 * subsequent elements to the left (subtracts one from their indexes). Returns
	 * the element that was removed from the list.
	 *
	 * @param index the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 */
	@Override
	public E remove(int index) {
		@SuppressWarnings("unchecked")
		E elementToRemove = (E) list[index];
		// If the index is out of range
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		// If at the end
		if (index == size() - 1) {
			list[index] = null;
			size--;
			return elementToRemove;
			// Beginning or middle
		} else {
			for (int i = index; i < size(); i++) {
				list[i] = list[i + 1];
			}
		}
		list[size() - 1] = null;
		size--;
		return elementToRemove;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param pos index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E get(int pos) {
		if (pos < 0 || pos >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		
		return (E) list[pos];
	}

	/*
	 * gets an index of a particular element
	 * 
	 */
	@Override
	public int indexOf(E e) {
		for (int k = 0; k < size; k++)
	         if (e.equals(list[k])) {
	            return k;
	         }
	      return -1;
	}

	@SuppressWarnings("unchecked")
	private void growArray() {

		E[] temp = (E[]) new Object[list.length + 12];
		for (int i = 0; i < list.length; i++) {
			temp[i] = (E) list[i];
		}
		list = temp;
	}

}
