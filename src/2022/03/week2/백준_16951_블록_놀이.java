// 12072KB, 104ms

package bj16951;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, K;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		int minCnt = Integer.MAX_VALUE;
		for (int startHeight = 1; startHeight <= 1000; startHeight++) {
			int cnt = 0;
			for (int i = 0; i < N; i++) {
				int height = startHeight + K * i;
				if (height != seq[i]) {
					cnt++;
				}
			}
			minCnt = cnt < minCnt ? cnt : minCnt;
		}

		System.out.println(minCnt);
	}

}