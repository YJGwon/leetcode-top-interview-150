import java.util.Map;
import java.util.HashMap;

class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        final Map<Character, Integer> characterCounts = new HashMap<>();
        for (char c : magazine.toCharArray()) {
            final int prevCount = characterCounts.getOrDefault(c, 0);
            characterCounts.put(c, prevCount + 1);
        }

        for (char c : ransomNote.toCharArray()) {
            final int count = characterCounts.getOrDefault(c, 0);
            if (count == 0) {
                return false;
            }
            characterCounts.put(c, count - 1);
        }
        return true;
    }
}
