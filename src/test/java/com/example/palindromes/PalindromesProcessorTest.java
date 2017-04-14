package com.example.palindromes;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.palindromes.PalindromesProcessor.PalindromeDescriptor;

public class PalindromesProcessorTest {

	@Before
	public void setup() {
	}

	@Test
	public void test() {
		final PalindromesProcessor palindromesProcessor = new PalindromesProcessor(
				"sqrrqabccbatudefggfedvwhijkllkjihxymnnmzpop");
		final List<PalindromeDescriptor> orderedPalindromes = palindromesProcessor.process();
		Assert.assertNotNull(orderedPalindromes);
		Assert.assertEquals(3, orderedPalindromes.size());
		final PalindromeDescriptor palindromeDescriptor1 = new PalindromeDescriptor("hijkllkjih", 23, 10);
		Assert.assertEquals(palindromeDescriptor1, orderedPalindromes.get(0));
		final PalindromeDescriptor palindromeDescriptor2 = new PalindromeDescriptor("defggfed", 13, 8);
		Assert.assertEquals(palindromeDescriptor2, orderedPalindromes.get(1));
		final PalindromeDescriptor palindromeDescriptor3 = new PalindromeDescriptor("abccba", 5, 6);
		Assert.assertEquals(palindromeDescriptor3, orderedPalindromes.get(2));
	}
}
