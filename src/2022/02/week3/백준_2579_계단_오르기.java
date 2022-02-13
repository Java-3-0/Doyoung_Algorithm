// 11720KB, 80ms

package bj2579;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 300;
	public static int N;
	public static int[] scores;
	
	// cache[here][prevOneStep] : 현재 계단 위치와 이전에 한 칸 이동했는지 여부가 주어졌을 때, 앞으로 가능한 최대 점수
	public static int[][] cache = new int[MAX_N + 1][3];
	
	
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		scores = new int[N + 1];
		scores[0] = 0;
		for (int i = 1; i <= N; i++) {
			scores[i] = Integer.parseInt(br.readLine());
		}
		
		// 캐시 -1로 초기화
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], -1);
		}
		
		// 최대 점수 계산
		int answer = getMaxScore(0, 0);
		
		// 출력
		System.out.println(answer);
	}
	
	/** 현재 위치와, 이전에 1칸 이동했는지 여부가 주어졌을 때, 앞으로 가능한 최대 점수를 리턴 */
	public static int getMaxScore(int here, int prevOneStep) {
		// base case : 마지막 칸 도착
		if (here == N) return scores[N];
		
		// base case : 마지막 칸 지나침
		if (here > N) {
			return Integer.MIN_VALUE;
		}
		
		// 캐시에 계산된 값이 있는 경우
		if (cache[here][prevOneStep] != -1) return cache[here][prevOneStep];
		
		// 이외의 경우
		
		// 첫 계단의 경우, 한 칸 이동했어도 이전 칸이 계단이 아니므로, 한 칸 이동하지 않은 걸로 친다.
		if (here == 1) {
			prevOneStep = 0;
		}
		
		// 2칸 가는 경우
		int ret = scores[here] + getMaxScore(here + 2, 0);
		// 1칸 갈 수 있고, 1칸 가는 경우
		if (prevOneStep != 1) {
			int tmp = scores[here] + getMaxScore(here + 1, 1);
			if (ret < tmp) {
				ret = tmp;
			}
		}
		
		return cache[here][prevOneStep] = ret;
	}
}