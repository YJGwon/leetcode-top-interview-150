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
            if (!characterCounts.containsKey(c)) {
                return false;
            }
            final int count = characterCounts.get(c) - 1;
            if (count == 0) {
                characterCounts.remove(c);
            } else {
                characterCounts.put(c, count);
            }
        }
        return true;
    }
}
