// 12088KB, 104ms

package bj5883;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int[] seq = new int[N];
		Set<Integer> set = new HashSet<>();

		for (int i = 0; i < N; i++) {
			int input = Integer.parseInt(br.readLine());
			seq[i] = input;
			set.add(input);
		}

		int maxCnt = 1;
		for (int numToExclude : set) {
			int cnt = 1;
			int prev = seq[0];
			for (int i = 1; i < N; i++) {
				int now = seq[i];
				if (now == prev) {
					cnt++;
					maxCnt = maxCnt < cnt ? cnt : maxCnt;
				}

				else {
					if (now != numToExclude) {
						cnt = 1;
						prev = now;
					}
				}
			}
		}

		System.out.println(maxCnt);
	}

}