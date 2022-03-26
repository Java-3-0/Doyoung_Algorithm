// 65300KB, 636ms

package bj21318;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	
	static int N, Q;
	static int[] seq = new int[MAX_N + 1];
	static int[] mistakes = new int[MAX_N + 1];
	static int[] psums = new int[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());
		
		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// mistakes 계산
		for (int i = 1; i < N; i++) {
			mistakes[i] = seq[i] > seq[i + 1] ? 1 : 0;
		}
		
		// mistakes의 psums 계산
		for (int i = 1, sum = 0; i < N; i++) {
			sum += mistakes[i];
			psums[i] = sum;
		}
		
		// 질문 개수 입력
		Q = Integer.parseInt(br.readLine());
		
		// 질문 입력받아서 처리
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			int answer = psums[end - 1] - psums[start - 1];
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());
		
	} // end main
	
}