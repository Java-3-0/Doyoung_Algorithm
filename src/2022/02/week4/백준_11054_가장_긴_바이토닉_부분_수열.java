// 12360KB, 120ms

package bj11054;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;

	static int[] cacheLIS = new int[MAX_N + 1];
	static int[] cacheLDS = new int[MAX_N + 1];
	static int[] seq = new int[MAX_N];
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();
		
		// 수열 길이 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// 가장 긴 바이토닉 부분 수열의 길이 계산
		int answer = getMaxBitonic();
		
		// 출력
		System.out.println(answer);

	} // end main
	
	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cacheLIS, CACHE_EMPTY);
		Arrays.fill(cacheLDS, CACHE_EMPTY);
	}

	/** 가장 긴 바이토닉 부분 수열의 길이를 리턴 */
	public static int getMaxBitonic() {
		int ret = 1;
		
		// 모든 위치를 최고점으로 해서
		for (int peakIdx = 0; peakIdx < N; peakIdx++) {
			//  최고점까지의 LIS, 최고점부터의 LDS를 구하고, 최고점 자체는 중복으로 2번 세므로 1을 뺀다.
			int lenBitonic = getMaxLIS(peakIdx) + getMaxLDS(peakIdx) - 1;
			ret = ret < lenBitonic ? lenBitonic : ret;			
		}
		
		return ret;
	}

	/** lastIdx에서 끝나는 longest increasing sequence의 길이를 리턴 */
	public static int getMaxLIS(int lastIdx) {
		// base case : 수열 맨 왼쪽
		if (lastIdx == 0) {
			return 1;
		}
		
		// 캐시에 계산된 값이 있는 경우
		if (cacheLIS[lastIdx] != CACHE_EMPTY) {
			return cacheLIS[lastIdx];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		int ret = 1;
		for (int idx = 0; idx < lastIdx; idx++) {
			if (seq[idx] < seq[lastIdx]) {
				ret = Math.max(ret, 1 + getMaxLIS(idx));
			}
		}
		
		return cacheLIS[lastIdx] = ret;
	}
	
	/** startIdx에서 시작하는 longest decreasing sequence의 길이를 리턴 */
	public static int getMaxLDS(int startIdx) {
		// base case : 수열의 맨 오른쪽
		if (startIdx == N - 1) {
			return 1;
		}
		
		// 캐시에 계산된 값이 있는 경우
		if (cacheLDS[startIdx] != CACHE_EMPTY) {
			return cacheLDS[startIdx];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		int ret = 1;
		for (int idx = startIdx + 1; idx < N; idx++) {
			if (seq[startIdx] > seq[idx]) {
				ret = Math.max(ret, 1 + getMaxLDS(idx));
			}
		}
		
		return cacheLDS[startIdx] = ret;
	}
}