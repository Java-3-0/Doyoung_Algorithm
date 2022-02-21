// 16952KB, 488ms

package bj2422;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200, MAX_M = 10000;
	static int N, M;
	static boolean[][] cannotPutTogether = new boolean[MAX_N + 1][MAX_N + 1];
	static boolean[] isSelected = new boolean[MAX_N + 1];
	static int answer = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 섞어먹으면 안 되는 조합 관계 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int ice1 = Integer.parseInt(st.nextToken());
			int ice2 = Integer.parseInt(st.nextToken());
			
			cannotPutTogether[ice1][ice2] = true;
			cannotPutTogether[ice2][ice1] = true;	
		}
		
		// 가능한 방법의 수 계산
		combination(1, 0);
		
		// 출력
		System.out.println(answer);
	}
	
	public static void combination(int hereIdx, int cnt) {
		// base case 1: 조합이 완성된 경우, 가능한 조합인지 파악해서 answer를 증가시킨다.
		if (cnt == 3) {
			for (int i = 1; i <= N; i++) {
				for (int j = i + 1; j <= N; j++) {
					// 들어간 두 재료 중 충돌 나는 게 있으면 실패니까 그냥 리턴
					if (isSelected[i] && isSelected[j] && cannotPutTogether[i][j]) {
						return;
					}
					
				}
			}
			
			// 충돌 나는 재료가 없어서 루프를 끝까지 수행했다면 정답 하나 증가
			answer++;
			return;
		}
		
		// base case 2: 조합을 완성하지 못하고 배열 끝까지 갔다면 실패
		if (hereIdx == N + 1) {
			return;
		}
		
		isSelected[hereIdx] = true;
		combination(hereIdx + 1, cnt + 1);
		isSelected[hereIdx] = false;
		combination(hereIdx + 1, cnt);
		
		return;
	}
}