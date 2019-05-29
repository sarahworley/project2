package edu.ncsu.csc216.travel.list_utils;

import java.util.NoSuchElementException;

/**
 * Implements the SortedList interface with a data structure of linked Nodes.
 * 
 * @author sarahworley
 * @param <E> extends e
 */
public class SortedLinkedListWithIterator<E extends Comparable<E>> implements SortedList<E> {

	/** front of list */
	private Node<E> head;
	/** size of the list */
	private int size;
	@SuppressWarnings("unused")
	private Node<E> back;

	/**
	 * constructor
	 */
	public SortedLinkedListWithIterator() {
		this.head = null;
		this.size = 0;

		this.back = head;
		back = head;

	}

	/**
	 * Returns the number of elements in this list. If this list contains more than
	 * Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {

		return size;
	}

	/**
	 * Returns true if this list contains no elements.
	 *
	 * @return true if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {

		return head == null;
	}

	/**
	 * Returns true if this list contains the specified element. More formally,
	 * returns true if and only if this list contains at least one element a such
	 * that (o==null ? a==null : o.equals(a)).
	 *
	 * @param e element whose presence in this list is to be tested
	 * @return true if this list contains the specified element
	 */
	@Override
	public boolean contains(E e) {
		//Node<E> current = head;
		if (size == 0 || e == null) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (e.compareTo(get(i)) == 0) {

				return true;
			} 
		}
		return false;
	}

	/**
	 * Adds the specified element to list in sorted order
	 *
	 * @param e element to be appended to this list
	 * @return true (as specified by
	 * @throws NullPointerException     if e is null
	 * @throws IllegalArgumentException if list already contains e
	 */
	
	@Override
	public boolean add(E e) {
		
		if (e == null) {
			throw new NullPointerException();
		}
		if (contains(e)) {
			throw new IllegalArgumentException();
		}
		if (head == null) {
			
			head = new Node<E>(e, null);
			back = head;
			size++;
			return true;
		}
		
		Node<E> node = head;
		if (e.compareTo(node.data) <= -1) {
			Node<E> oldHead = head;
			head = new Node<E>(e, oldHead);
			size++;
			return true;
		}

		while (node.next != null && e.compareTo(node.next.data) >= 1) {
			node = node.next;

		}
		node.next = new Node<E>(e, node.next);
		size++;
		if (node.next.next == null) {
			back = node.next;
		}
		return true;

	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		Node<E> p = head;
		for (int k = 0; k < index; k++) {
			if (p == null)
				return null;
			p = p.next;
		}
		if (p == null)
			return null;
		return p.data;
	}

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left (subtracts one from their indices). Returns
	 * the element that was removed from the list.
	 *
	 * @param index the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of range (
	 */
	@Override
	public E remove(int index) {
		
		
		if (head == null) {
			throw new IndexOutOfBoundsException();
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> current = head;
		Node<E> previous = null;
		while (index > 0) {
			previous = current;
			current = current.next;
			index--;
			// return previous.data;
		}

		if (current == head) {
			head = head.next;

		} else {
			previous.next = current.next;

		}
		
		size--;
		return current.data;

	}

	/**
	 * Returns the index of the first occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element. More formally, returns
	 * the lowest index i such that  or
	 * -1 if there is no such index.
	 *
	 * @param e element to search for
	 * @return the index of the first occurrence of the specified element in this
	 *         list, or -1 if this list does not contain the element
	 */
	@Override
	public int indexOf(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			if (e.equals(get(i))) {
				return i;
			}
			current = current.next;
		}
		return -1;

	}

	/**
	 * An iterator
	 * 
	 * @return update
	 */
	public SimpleListIterator<E> iterator() {
		return new Cursor();
	}

	/*
	 * Returns a string representation
	 * 
	 * @return string
	 */
	@Override
	public String toString() {

		SimpleListIterator<E> cursor = iterator();
		String str = "[";
		while (cursor.hasNext()) {
			str += cursor.next().toString();
			if (cursor.hasNext()) {
				str += ", ";
			}
		}
		return str + "]";
	}

	/**
	 * Inner class cursor
	 * 
	 * @author sarahworley
	 * @param<E> genreal
	 */
	public class Cursor implements SimpleListIterator<E> {
		private Node<E> traveler = head;

		/* hasNext method
		 * (non-Javadoc)
		 * @see edu.ncsu.csc216.travel.list_utils.SimpleListIterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return traveler != null;
		}

		/* next method
		 * (non-Javadoc)
		 * @see edu.ncsu.csc216.travel.list_utils.SimpleListIterator#next()
		 */
		@Override
		public E next() {
			if (traveler == null) {
				throw new NoSuchElementException();
			}
			E data = (E) traveler.data;
			traveler = traveler.next;
			return data;
		}

	}

	/**
	 * Inner class for SortedLinkedListWithIterator
	 * 
	 * @author sarahworley
	 * @param <E> general
	 */
	private static class Node<E extends Comparable<E>> {
		/** the data in the node */
		private E data;
		/** the next node in the list */
		private Node<E> next;

		// private E value;

		/**
		 * constructor
		 * 
		 * @param data
		 * @param next
		 */
		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}

}
