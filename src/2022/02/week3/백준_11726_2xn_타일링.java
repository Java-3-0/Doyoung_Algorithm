// 12948KB, 116ms

package bj11726;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int MAX_N = 1000;
	public static final int MOD = 10007;
	public static int[] cache = new int[MAX_N + 1];
	
	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
		
		// 캐시 초기화
		Arrays.fill(cache, -1);
		
		// 타일링 방법 수 계산
		int answer = numFills(n);
		
		// 출력
		System.out.println(answer);
	}

	/** 2 x n 직사각형을 타일로 채우는 방법의 수를 리턴 */
	public static int numFills (int n) {
		// base case: n = 1, 2
		if (n <= 2) return n;
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[n] != -1) {
			return cache[n];
		}
		
		// 새로 계산하는 경우
		return cache[n] = (numFills(n-1) + numFills(n-2)) % MOD;
	}
}
