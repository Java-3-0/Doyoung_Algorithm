// 11600KB, 80ms

package bj21317;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 20;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int[] smallJumps = new int[MAX_N + 1];
	static int[] largeJumps = new int[MAX_N + 1];
	static int[][] cache = new int[MAX_N + 1][2];
	static int N;
	static int K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();
		
		// 돌 개수 입력
		N = Integer.parseInt(br.readLine());

		// 각 돌에서의 점프 비용들 입력
		for (int i = 1; i <= N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			smallJumps[i] = Integer.parseInt(st.nextToken());
			largeJumps[i] = Integer.parseInt(st.nextToken());
		}
		
		// 매우 큰 점프 비용 입력
		K = Integer.parseInt(br.readLine());
		
		// 정답 계산
		int answer = getMinEnergy(1, 0);

		// 출력
		System.out.println(answer);
		
	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
	
	/** 
	 * N번 돌까지 가는 최소 에너지를 계산해서 리턴
	 * @param startPos : 현재 서 있는 위치
	 * @param usedXL : 매우 큰 점프 사용 여부 (1 : 사용, 0 : 미사용)
	 * @return N번 돌까지 가는 최소 에너지
	 */
	public static int getMinEnergy(int startPos, int usedXL) {
		// base case 1. 도착
		if (startPos == N) {
			return 0;
		}
		
		// base case 2. 범위 초과
		if (startPos > N) {
			return INF;
		}
		
		// 캐시에 계산되어 있는 경우
		if (cache[startPos][usedXL] != CACHE_EMPTY) {
			return cache[startPos][usedXL];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		// 1칸 점프, 2칸 점프, 3칸 점프(아직 안 썼을 때만)를 시도 해본다.
		int ret = smallJumps[startPos] + getMinEnergy(startPos + 1, usedXL);
		ret = Math.min(ret, largeJumps[startPos] + getMinEnergy(startPos + 2, usedXL));
		if (usedXL == 0) {
			ret = Math.min(ret, K + getMinEnergy(startPos + 3, 1));
		}
		
		return cache[startPos][usedXL] = ret;
	}
}