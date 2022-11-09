import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int MAX_LEN = 20;
    static final int ALPHABETS = 26;

    static long[] factorials = new long[MAX_LEN + 1];
    static char[] text;
    static int len;
    static int[] alphabetCounts = new int[ALPHABETS];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        precalcFactorials();

        final int TESTS = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= TESTS; tc++) {
            initMemory();

            text = br.readLine().toCharArray();
            len = text.length;
            for (char c : text) {
                alphabetCounts[c - 'A']++;
            }

            long answer = countSmallerAnagrams(0, false);
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static long countSmallerAnagrams (int startIdx, boolean isSmaller) {
        // base case
        if (startIdx == len) {
            return isSmaller ? 1L : 0L;
        }

        int num = text[startIdx] - 'A';

        // 앞부분에서 이미 작은 것이 확정인 경우
        long denominator = 1L;
        int cnt = 0;
        if (isSmaller) {
            for (int i = 0; i < ALPHABETS; i++) {
                if (alphabetCounts[i] > 0) {
                    cnt += alphabetCounts[i];
                    denominator *= factorials[alphabetCounts[i]];
                }
            }

            return factorials[cnt] / denominator;
        }

        // 앞부분까지는 같은 경우
        else {
            long ret = 0L;
            for (int i = 0; i <= num; i++) {
                if (alphabetCounts[i] > 0) {
                    alphabetCounts[i]--;
                    if (i == num) {
                        ret += countSmallerAnagrams(startIdx + 1, false);
                    }
                    else {
                        ret += countSmallerAnagrams(startIdx + 1, true);
                    }
                    alphabetCounts[i]++;
                }
            }

            return ret;
        }
    }

    public static void initMemory() {
        Arrays.fill(alphabetCounts, 0);
    }

    public static void precalcFactorials() {
        factorials[0] = 1;

        for (int i = 1; i <= MAX_LEN; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
    }

}