// 11632KB, 88ms

package bj9656;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int LOSE = 0, WIN = 1;
	
	static int N;
	static int[] cache = new int[MAX_N + 1];
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 캐시 초기화
		initCache();
		
		// N 입력
		N = Integer.parseInt(br.readLine());
		
		// 승리 가능 여부에 따라 출력
		if (canWin(N) == WIN) {
			System.out.println("SK");
		}
		else {
			System.out.println("CY");
		}
	}
	
	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}
	
	/** 돌 개수가 stones개 남았을 때, 선턴이 승리할 수 있는지 여부를 리턴 */
	public static int canWin(int stones) {
		// base case : 1개 남았으면 진다
		if (stones == 1) {
			return LOSE;
		}
		
		// 캐시에 계산되어 있는 경우 그대로 리턴
		if (cache[stones] != CACHE_EMPTY) {
			return cache[stones];
		}
		
		// 새로 계산해서 캐시에 담아 리턴
		int ret = CACHE_EMPTY;
		if (stones >= 4) {
			if (canWin(stones - 1) == WIN && canWin(stones - 3) == WIN) {
				ret = LOSE;
			}
			else {
				ret = WIN;
			}
		}
		
		else {
			if (canWin(stones - 1) == WIN) {
				ret = LOSE;
			}
			else {
				ret = WIN;
			}
		}
		
		return cache[stones] = ret;
	}

}
