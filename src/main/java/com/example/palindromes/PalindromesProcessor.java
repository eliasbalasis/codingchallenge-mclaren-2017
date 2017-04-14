package com.example.palindromes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Detects palindromes in input {@link String}
 * 
 * @author eliasbalasis
 *
 */
public class PalindromesProcessor {

	private String input;

	/**
	 * A palindrome instance
	 * 
	 * @author eliasbalasis
	 *
	 */
	public static final class PalindromeDescriptor {
		/**
		 * The palindrome text
		 */
		private String text;
		/**
		 * The palindrome index
		 */
		private int index;
		/**
		 * The palindrome length
		 */
		private int length;

		public PalindromeDescriptor(final String text, final int index, final int length) {
			this.text = text;
			this.index = index;
			this.length = length;
		}

		public String getText() {
			return text;
		}

		public int getIndex() {
			return index;
		}

		public int getLength() {
			return length;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof PalindromeDescriptor) {
				final PalindromeDescriptor other = (PalindromeDescriptor) obj;
				final EqualsBuilder builder = new EqualsBuilder();
				builder.append(getText(), other.getText());
				builder.append(getIndex(), other.getIndex());
				builder.append(getLength(), other.getLength());
				final boolean equals = builder.isEquals();
				return equals;
			}
			return false;
		}
	}

	/**
	 * Orders palindromes in descending order of length
	 * 
	 * @author eliasbalasis
	 *
	 */
	private class PalindromeDescriptorComparator implements Comparator<PalindromeDescriptor> {
		@Override
		public int compare(final PalindromeDescriptor o1, final PalindromeDescriptor o2) {
			return Integer.compare(o2.length, o1.length);
		}
	}

	private Map<String, PalindromeDescriptor> palindromes = new LinkedHashMap<>();

	/**
	 * Constructor
	 * 
	 * @param input
	 *            The input to check for plaindromes
	 */
	public PalindromesProcessor(final String input) {
		this.input = input;
	}

	/**
	 * A test program which detects palindromes in user's input
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		// accept user input
		final Scanner inputScanner = new Scanner(System.in);
		System.out.print("Enter your input: ");
		final String input = inputScanner.nextLine();
		inputScanner.close();
		// call palindromes processor
		final PalindromesProcessor palindromesProcessor = new PalindromesProcessor(input);
		final List<PalindromeDescriptor> orderedPalindromes = palindromesProcessor.process();
		// output discovered palindromes in user's input
		for (PalindromeDescriptor palindromeDescriptor : orderedPalindromes) {
			System.out.println( //
					"Text : " + palindromeDescriptor.text + //
							", Index: " + palindromeDescriptor.index + //
							", Length: " + palindromeDescriptor.length //
			);
		}
	}

	/**
	 * Check the input for palindromes
	 * 
	 * @return The discovered palindromes in descending order of length
	 */
	public List<PalindromeDescriptor> process() {
		String text;
		int index = 0;
		int length = 0;
		boolean palindrome;
		while (index + length < input.length()) {
			length++;
			if (length < 2) {
				// a palindrome must be at least 2 chars
				continue;
			}
			// get sample
			text = input.substring(index, index + length);
			// check
			palindrome = isPalindrom(text.toCharArray());
			if (palindrome) {
				// found a palindrome, keep track of it
				registerPalindrom(text, index, length);
			}
			if (index + length == input.length()) {
				// input exhausted, restart from next index
				length = 0;
				index++;
			}
		}
		final List<PalindromeDescriptor> palindromes = orderPalindromes();
		if (palindromes.size() <= 3) {
			return palindromes;
		}
		return palindromes.subList(0, 3);
	}

	/**
	 * Keep track of unique palindromes discovered
	 * 
	 * @param text
	 *            The palindrome text
	 * @param index
	 *            The palindrom index
	 * @param length
	 *            The palindrom length
	 */
	private void registerPalindrom(final String text, final int index, final int length) {
		PalindromeDescriptor descriptor = palindromes.get(text);
		if (descriptor != null) {
			// keep only unique palindromes
			palindromes.remove(text);
			return;
		}
		// register unique palindrome
		descriptor = new PalindromeDescriptor(text, index, length);
		palindromes.put(text, descriptor);
	}

	/**
	 * Get discovered palindromes in descending order of length
	 * 
	 * @return The discovered palindromes in descending order of length
	 */
	private List<PalindromeDescriptor> orderPalindromes() {
		final Set<PalindromeDescriptor> orderedPalindromes = new TreeSet<>(new PalindromeDescriptorComparator());
		orderedPalindromes.addAll(palindromes.values());
		return new ArrayList<>(orderedPalindromes);
	}

	// simple and efficient palindrom algorithm
	// no recursion, no expensive or memory intensive operations
	private boolean isPalindrom(char[] word) {
		int i1 = 0;
		int i2 = word.length - 1;
		while (i2 > i1) {
			if (word[i1] != word[i2]) {
				return false;
			}
			++i1;
			--i2;
		}
		return true;
	}

}
