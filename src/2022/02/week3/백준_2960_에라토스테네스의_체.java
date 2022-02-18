// 11536KB, 76ms

package bj2960;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int answer = getKthErase(N, K);

		System.out.println(answer);
	}

	public static int getKthErase(int N, int K) {
		// 초기 상태
		boolean[] isErased = new boolean[N + 1];
		Arrays.fill(isErased, false);

		int lastErased = 0;

		for (int p = 2; p <= N; p++) {
			if (isErased[p]) {
				continue;
			}
			
			for (int num = p; num <= N; num += p) {
				if (isErased[num]) {
					continue;
				}
				
				isErased[num] = true;
				lastErased = num;
				K--;
				
				if (K == 0) return lastErased;
			}
		}
		
		return 0;
	}
}