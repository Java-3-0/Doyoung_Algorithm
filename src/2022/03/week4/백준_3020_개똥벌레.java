// 28564KB, 232ms

package bj3020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200000;
	static final int MAX_H = 500000;
	
	static int N, H;
	static int[] delta;
	static int[] counts = new int[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, H 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		// 장애물 크기 입력받아서 높이가 변할 때의 장애물 수 변화량 계산
		delta = new int[H + 1];
		for (int i = 0; i < N / 2; i++) {
			int h1 = Integer.parseInt(br.readLine());
			int h2 = Integer.parseInt(br.readLine());
			
			delta[h1 + 1]--;
			delta[H + 1 - h2]++;
		}
		
		// 첫 칸에서 초기 장애물 수
		int obstacles = N / 2;
		
		// 모든 높이를 완전탐색
		for (int i = 1; i <= H; i++) {
			// 장애물 변화량에 따라 갱신
			obstacles += delta[i];
			
			counts[obstacles]++;
		}
		
		// 정답 출력
		for (int i = 0; i <= N; i++) {
			if (counts[i] != 0) {
				System.out.println(i + " " + counts[i]);
				return;
			}
		}
		
	} // end main

}