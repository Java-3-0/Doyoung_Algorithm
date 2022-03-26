// 16668KB, 2308ms

package bj13910;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000;
	static final int MAX_M = 100;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, M;
	static int[] cache = new int[MAX_N + 1];
	
	/** 한 번의 요리로 가능한 그릇 수를 담을 set */
	static Set<Integer> set = new HashSet<Integer>();
	/** 그 set의 크기 */
	static int setSize;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();
		
		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 웍 크기들 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] seq = new int[M];
		for (int i = 0; i < M; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// 웍 1개 사용해서 가능한 크기
		for (int num : seq) {
			set.add(num);
		}
		
		// 웍 2개 사용해서 가능한 크기
		for (int i = 0; i < M; i++) {
			for (int j = i + 1; j < M; j++) {
				set.add(seq[i] + seq[j]);
			}
		}
		
		// set 크기 계산
		setSize = set.size();

		// 정답 계산
		int minCooks = getMinCooks(N);
		int answer = minCooks == INF ? -1 : minCooks;
		
		// 출력
		System.out.println(answer);

	} // end main
	
	/** n그릇의 짜장면을 만들기 위해 필요한 최소 요리 횟수를 리턴 */
	public static int getMinCooks(int n) {
		if (n < 0) {
			return INF;
		}
		
		if (n == 0) {
			return 0;
		}
		
		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}
		
		int ret = INF;
		for (int num : set) {
			ret = Math.min(ret, 1 + getMinCooks(n - num));
		}
		
		return cache[n] = ret;
	}

	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

}