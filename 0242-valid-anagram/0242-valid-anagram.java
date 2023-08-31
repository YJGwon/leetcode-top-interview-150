class Solution {

    private static final int NUMBERS_OF_ALPHABETS = 26;

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        final int[] characterCounts = new int[NUMBERS_OF_ALPHABETS];
        for (char c : s.toCharArray()) {
            characterCounts[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            if (characterCounts[c - 'a'] == 0) {
                return false;
            }
            characterCounts[c - 'a']--;
        }
        return true;
    }
}