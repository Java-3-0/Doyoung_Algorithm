// 11948KB, 80ms

package bj1359;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K;
	static int[] combi;
	static Set<Integer> target = new HashSet<>();
	
	static int jackpotCount = 0;
	static int totalCount = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// 조합을 위한 메모리 할당
		combi = new int[M];
		
		// 1 ~ M까지의 수가 당첨 수들인 것으로 설정
		for (int i = 1; i <= M; i++) {
			target.add(i);
		}
		
		// 모든 조합 완전탐색
		combination(1, 0);
		
		// 당첨 확률 계산
		double answer = (double) jackpotCount / (double) totalCount;
		
		// 출력
		System.out.printf("%.12f\n", answer);
	}
	
	/** 모든 조합을 완전탐색하면서 모든 경우의 수와 잭팟 경우의 수를 센다 */
	public static void combination(int startNum, int cnt) {
		// 조합이 완성된 경우
		if (cnt == M) {
			totalCount++;
			
			if (isJackpot()) {
				jackpotCount++;
			}

			return;
		}
		
		// 조합이 완성되지 못하고 끝까지 간 경우
		if (startNum == N + 1) {			
			return;
		}
		
		// 덜 진행된 경우, 조합 만들기 시도
		combi[cnt] = startNum;
		combination(startNum + 1, cnt + 1);
		
		combination(startNum + 1, cnt);
	}
	
	/** 현재 combi가 당첨인지 여부를 리턴 */
	public static boolean isJackpot() {
		int cnt = 0;
		for (int i = 0; i < M; i++) {
			if (target.contains(combi[i])) {
				cnt++;
			}
		}
		
		if (cnt >= K) {
			return true;
		}
		
		return false;
	}
}