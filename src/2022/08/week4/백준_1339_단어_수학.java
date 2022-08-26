// 11632KB, 80ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            char[] word = br.readLine().toCharArray();

            int len = word.length;

            int val = 1;
            for (int pos = len - 1; pos >= 0; pos--) {
                char c = word[pos];
                map.put(c, val + map.getOrDefault(c, 0));
                val *= 10;
            }
        }

        int size = map.size();
        Integer[] values = map.values().toArray(new Integer[size]);
        Arrays.sort(values, Collections.reverseOrder());

        int pick = 9;
        int answer = 0;
        for (int val : values) {
            answer += ((pick--) * val);
        }

        System.out.println(answer);

    } // end main

}