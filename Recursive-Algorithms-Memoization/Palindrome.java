/**
 * Efficient recursive palindrome checker without using substrings.
 * Uses index-based recursion to avoid creating new strings each time.
 Time complexity: O(n)
 - one comparison per character pair
 - no substring creation :)
Space complexity: O(n) due to recursion depth
Edge cases:
 - if empty string ("") then true
 - if one character then true
 - if null then false
*/

class Palindrome
{

    /**
     * Main palindrome check method.
     * @param s input string
     * @return true if palindrome, false otherwise
     */
    public static boolean isPalindrome(String s)
    {
    if (s == null) return false; // handle null safely
        return isPalindromeHelper(s, 0, s.length() - 1);
    }
        
    /**
     * Recursive helper that compares characters from both ends inward.
     * @param s input string
     * @param left left index
     * @param right right index
     * @return true if substring between left and right is palindrome
     */
    private static boolean isPalindromeHelper(String s, int left, int right) {
        // Base case: pointers have crossed or are equal - all chars matched
        if (left >= right){
            return true;
        } 

        // If mismatch found, it's not a palindrome
        if (s.charAt(left) != s.charAt(right)){
            return false;
        }

        // Move inward
        return isPalindromeHelper(s, left + 1, right - 1);
    }

    public static void main(String [] args)
    {
    String s = args[0].toLowerCase();
    boolean res = isPalindrome(s); 
    if (res){
        System.out.println(s + " is a palindrome");
    }
    else
        System.out.println(s + " is not a palindrome");
    }
}

