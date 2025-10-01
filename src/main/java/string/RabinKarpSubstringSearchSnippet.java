/*
 * MIT License
 *
 * Copyright (c) 2017-2024 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package string;

/**
 * RabinKarpSubstringSearchSnippet.
 */
public class RabinKarpSubstringSearchSnippet {

  private static final int PRIME = 1_000_000_007; // A prime number used as modulus for hashing

  /**
   * Implements the Rabin-Karp algorithm to find the index of a substring.
   *
   * @param text The text in which the substring is to be searched.
   * @param pattern The substring pattern to search for.
   * @return The index of the first occurrence, or -1 if the pattern is not found.
   */
  public static int rabinKarpSearch(String text, String pattern) {
    if (pattern == null || pattern.length() == 0) {
      return 0; // Trivial case: empty pattern
    }
    if (text == null || text.length() < pattern.length()) {
      return -1; // Pattern cannot be found
    }

    int m = pattern.length();
    int n = text.length();
    long patternHash = createHash(pattern, m);
    long textHash = createHash(text, m);

    for (int i = 0; i <= n - m; i++) {
      if (patternHash == textHash && checkEqual(text, i, i + m - 1, pattern, 0, m - 1)) {
        return i; // Match found
      }
      if (i < n - m) {
        textHash = recalculateHash(text, i, i + m, textHash, m);
      }
    }
    return -1; // No match found
  }

  /**
   * Creates a hash for the given substring.
   *
   * @param str The string to hash.
   * @param end The length of substring considered.
   * @return The hash value.
   */
  private static long createHash(String str, int end) {
    long hash = 0;
    for (int i = 0; i < end; i++) {
      hash += str.charAt(i) * Math.pow(PRIME, i);
    }
    return hash;
  }

  /**
   * Recalculates hash by sliding the window by one character.
   *
   * @param str The original string.
   * @param oldIndex The index of the outgoing character.
   * @param newIndex The index of the incoming character.
   * @param oldHash The previous hash value.
   * @param patternLen The length of the pattern.
   * @return The recalculated hash.
   */
  private static long recalculateHash(
        String str, 
        int oldIndex, 
        int newIndex, 
        long oldHash, 
        int patternLen) {
    long newHash = oldHash - str.charAt(oldIndex);
    newHash /= PRIME;
    newHash += str.charAt(newIndex) * Math.pow(PRIME, patternLen - 1);
    return newHash;
  }

  /**
   * Checks if two substrings are equal.
   *
   * @param str1 The first string.
   * @param start1 Start index in str1.
   * @param end1 End index in str1.
   * @param str2 The second string.
   * @param start2 Start index in str2.
   * @param end2 End index in str2.
   * @return True if substrings are equal, otherwise false.
   */
  private static boolean checkEqual(
        String str1, 
        int start1, 
        int end1, 
        String str2, 
        int start2, 
        int end2) {
    if (end1 - start1 != end2 - start2) {
      return false;
    }
    while (start1 <= end1 && start2 <= end2) {
      if (str1.charAt(start1) != str2.charAt(start2)) {
        return false;
      }
      start1++;
      start2++;
    }
    return true;
  }
}
