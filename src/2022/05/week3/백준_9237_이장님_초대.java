// 27308KB, 336ms

package bj9237;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 수열 크기 입력
		int N = Integer.parseInt(br.readLine());

		// 수열 입력
		int[] seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// 수열 오름차순 정렬
		Arrays.sort(seq);
		
		// 정답 계산
		int day = 1;
		int answer = 0;
		for (int i = N - 1; i >= 0; i--) {
			answer = Math.max(answer, seq[i] + day);
			day++;
		}

		answer++;
		
		// 출력
		System.out.println(answer);
		
	} // end main
}