// 26456KB, 248ms

package bj20438;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 5000;
	static final int MAX_M = 50000;
	
	static int N, K, Q, M;
	
	static boolean[] isSleeping = new boolean[MAX_N + 2 + 1];
	static boolean[] isChecked = new boolean[MAX_N + 2 + 1];
	static int[] psums = new int[MAX_N + 2 + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, K, Q, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 졸고 있는 학생들 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			int input = Integer.parseInt(st.nextToken());
			isSleeping[input] = true;
		}
		
		// 출석 코드를 받는 학생들 입력받아서 배수를 출석 처리
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Q; i++) {
			int input = Integer.parseInt(st.nextToken());
			if (!isSleeping[input]) {
				for (int num = input; num <= N + 2; num += input) {
					if (!isSleeping[num]) {
						isChecked[num] = true;
					}
				}
			}
		}
		
		// 결석인 학생들의 누적합을 계산
		for (int i = 3, sum = 0; i <= N + 2; i++) {
			if (!isChecked[i]) {
				sum++;
			}
			
			psums[i] = sum;
		}
		
		// M개의 구간에 대해 정답 계산
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int S = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			int answer = psums[E] - psums[S - 1];
			
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
	
}