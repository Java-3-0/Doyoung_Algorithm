// 12452KB, 192ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 2000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N;
	static int[] soldiers = new int[MAX_N + 1];
	static int[] cache = new int[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		initCache();
		
		N = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine(), " ");
		soldiers[0] = INF;
		for (int i = 1; i <= N; i++) {
			soldiers[i] = Integer.parseInt(st.nextToken());
		}
		
		int maxLenOfLDS = LDS(0) - 1;
		int answer = N - maxLenOfLDS;
		System.out.println(answer);
		
	} // end main
		
	public static int LDS(int startIdx) {
		if (startIdx == N) {
			return 1;
		}
		
		if (cache[startIdx] != CACHE_EMPTY) {
			return cache[startIdx];
		}
		
		int ret = 1;
		for (int nextIdx = startIdx + 1; nextIdx <= N; nextIdx++) {
			if (soldiers[startIdx] > soldiers[nextIdx]) {
				ret = Math.max(ret, 1 + LDS(nextIdx));
			}
		}
		
		return cache[startIdx] = ret;
	}
	
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

}