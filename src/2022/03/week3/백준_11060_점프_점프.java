// 12365KB, 88ms

package bj11060;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	
	static int N;
	static int[] maze = new int[MAX_N + 1];
	static int[] cache = new int[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();
		
		// 미로 칸 개수 입력
		N = Integer.parseInt(br.readLine());
				
		// 미로 정보 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			maze[i] = Integer.parseInt(st.nextToken());
		}
		
		// 최소 점프 수 계산
		int answer = getMinJumps(0);
		
		// 출력
		if (answer == INF) {
			System.out.println("-1");
		}
		else {
			System.out.println(answer);
		}
		
	}
	
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	/** here위치부터 (N - 1) 위치까지 가는 최소 점프 수 */
	public static int getMinJumps (int here) {
		// base case 1 : N - 1을 지나친 경우
		if (here > N - 1) {
			return INF;
		}
		// base case 2 : 도착한 경우
		else if (here == N - 1) {
			return 0;
		}
		
		// 캐시에 이미 저장되어 있는 경우
		if (cache[here] != CACHE_EMPTY) {
			return cache[here];
		}
		
		// 새로 계산해서 캐시에 저장하는 경우
		int ret = INF;
		int maxMove = maze[here];
		for (int move = 1; move <= maxMove; move++) {
			ret = Math.min(ret, 1 + getMinJumps(here + move));
		}
		
		return cache[here] = ret;
	}
	
}
