// 11836KB, 88ms

package bj5534;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 오래된 간판 수
		int N = Integer.parseInt(br.readLine());

		// 편의점 이름
		String target = br.readLine();

		int answer = 0;
		for (int i = 0; i < N; i++) {
			String old = br.readLine();
			if (isPossible(old, target)) {
				answer++;
			}
		}

		System.out.println(answer);

	} // end main

	public static boolean isPossible(String old, String target) {
		int lenTarget = target.length();
		int lenOld = old.length();

		for (int start = 0; start < lenOld; start++) {
			DISTLOOP: for (int dist = 0; start + (lenTarget - 1) * dist < lenOld; dist++) {
				int cnt = 0;
				for (int i = 0; i < lenTarget; i++) {
					int idx = start + dist * i;
					if (old.charAt(idx) != target.charAt(i)) {
						continue DISTLOOP;
					} else {
						cnt++;
					}
				}

				if (cnt == lenTarget) {
					return true;
				}

			}

		}

		return false;
	}

}