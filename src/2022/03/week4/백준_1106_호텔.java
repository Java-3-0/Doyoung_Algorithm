// 11920KB, 80ms

package bj1106;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_C = 1000;
	static final int MAX_N = 100;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int C, N;
	static int[] costs;
	static int[] people;
	static int[] cache = new int[MAX_C + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();
		
		// 목표 고객 수, 전체 도시 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		costs = new int[N];
		people = new int[N];

		// 각 도시의 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			costs[i] = Integer.parseInt(st.nextToken());
			people[i] = Integer.parseInt(st.nextToken());

		}

		// 최소 비용 계산
		int answer = getMinCost(C);
		
		// 출력
		System.out.println(answer);
	
	}
	
	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}
	
	/** n명의 고객을 유치하기 위해 필요한 최소 비용을 리턴 */
	public static int getMinCost(int n) {
		// base case : 필요한 만큼의 고객을 모두 유치한 경우
		if (n <= 0) {
			return 0;
		}
		
		// cache에 이미 계산되어 있는  경우
		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}
		
		// 새로 계산해서 cache에 저장하면서 리턴하는 경우
		int ret = INF;
		for (int i = 0; i < N; i++) {
			int c = costs[i];
			int p = people[i];
			ret = Math.min(ret, c + getMinCost(n - p));
		}
		
		return cache[n] = ret;
	}
	
	

}
