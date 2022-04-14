// 61420KB, 532ms

package bj19951;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000, MAX_M = 100000;

	static int N, M;
	static int[] seq;
	static int[] deltas;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 메모리 할당
		seq = new int[N + 1];
		deltas = new int[N + 2];
		
		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1, sum = 0; i <= N; i++) {
			int input = Integer.parseInt(st.nextToken());
			sum += input;
			seq[i] = input;
		}
		
		// 조교의 지시 처리
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int delta = Integer.parseInt(st.nextToken());
			
			deltas[from] += delta;
			deltas[to + 1] -= delta;
		}
		
		// 수열 각 칸의 값 계산
		for (int i = 1, sumDelta = 0; i <= N; i++) {
			sumDelta += deltas[i];
			int answer = seq[i] + sumDelta;
			sb.append(answer).append(" ");
		}
		sb.append("\n");
		
		// 출력
		System.out.print(sb.toString());
		
		
	} // end main

}