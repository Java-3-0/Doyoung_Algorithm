// 11584KB, 84ms

package bj1182;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 20;
	public static final int MAX_NUMBER = 100000;
	public static int N, S;
	public static int[] seq = new int[MAX_N];
	public static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		subset(0, 0, 0);
		
		System.out.println(answer);
	}

	public static void subset(int idx, int cnt, int accum) {
		// 부분집합이 완성되었을 때
		if (idx == N) {
			// 크기가 양수인 부분집합이고, 그 합이 S라면 정답을 찾은 것
			if (cnt != 0 && accum == S) {
				answer++;
			}
			
			return;
		}
		
		// 이 원소를 넣는 경우
		subset(idx + 1, cnt + 1, accum + seq[idx]);
		
		// 이 원소를 안 넣는 경우
		subset(idx + 1, cnt, accum);
		
	}
}