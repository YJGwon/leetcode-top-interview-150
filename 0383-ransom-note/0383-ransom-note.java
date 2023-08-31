class Solution {

    private static final int NUMBERS_OF_ALPHABETS = 26;

    public boolean canConstruct(String ransomNote, String magazine) {
        final int[] charactercounts = new int[NUMBERS_OF_ALPHABETS];
        for (char c : magazine.toCharArray()) {
            charactercounts[c - 'a']++;
        }

        for (char c : ransomNote.toCharArray()) {
            if (charactercounts[c - 'a'] == 0) {
                return false;
            }
            charactercounts[c - 'a']--;
        }
        return true;
    }
}
