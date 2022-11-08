import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 15;
    static final int ALPHABETS = 26;
    static final int FULL_BITMASK = (1 << ALPHABETS) - 1;

    static int N;
    static List<String> wordList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        final int TESTS = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TESTS; tc++) {
            wordList.clear();

            N = Integer.parseInt(br.readLine());

            for (int i = 0; i < N; i++) {
                String word = br.readLine();
                wordList.add(word);
            }

            int answer = solve(0, 0);

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static int solve(int idx, int bitmask) {
        if (idx == N) {
            return bitmask == FULL_BITMASK ? 1 : 0;
        }

        String word = wordList.get(idx);
        int len = word.length();
        int nextBitmask = bitmask;
        for (int i = 0; i < word.length(); i++) {
            int pos = word.charAt(i) - 'a';
            nextBitmask |= (1 << pos);
        }

        return solve(idx + 1, bitmask) + solve(idx + 1, nextBitmask);
    }
}