// 11472KB, 80ms

package bj2847;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		
		int[] scores = new int[N];
		for (int i = 0; i < N; i++) {
			scores[i] = Integer.parseInt(br.readLine());
		}
	
		// 뒤에서부터 탐색하면서 레벨이 잘못된 것을 낮춤
		int count = 0;
		int lastIdx = N - 1;
		for (int idx = lastIdx - 1; idx >= 0; idx--) {
			int score = scores[idx];
			int nextScore = scores[idx + 1];
			
			if (nextScore <= score) {
				int diff = score - nextScore;
				scores[idx] = nextScore - 1;
				count += (diff + 1);
			}
		}
		
		// 감소 횟수 출력
		System.out.println(count);

	}
}