// 12736KB, 156ms

package bj17626;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final int MAX_N = 50000;
	public static final int SQRT_MAX_N = 223;
	public static int N;
	public static int[] squares = new int[SQRT_MAX_N + 1];
	public static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		N = Integer.parseInt(br.readLine());

		// MAX_N이하의 제곱수들 모두 계산
		generateSquares();

		// 정답 출력
		System.out.println(minNumOfSquares(N));
	}

	/** x를 만들기 위해 필요한 제곱수 개수의 최소값을 리턴 */
	public static int minNumOfSquares(int x) {
		// 캐시에 계산된 경우
		if (cache[x] != 0)
			return cache[x];

		// 새로 계산하는 경우
		int ret = (x / 4) + (x % 4);
		
		for (int num = 3; num < squares.length; num++) {
			// (x - 제곱수)로 재귀 호출하면서 가장 최소를 찾는다
			int diff = x - squares[num];
			
			if (diff == 0) {
				ret = 1;
				break;
			}
			else if (diff > 0) {
				int tmp = 1 + minNumOfSquares(diff);
				if (tmp < ret) {
					ret = tmp;
				}
			} else {
				break;
			}
		}

		return cache[x] = ret;
	}

	/** 가능한 제곱수를 모두 생성 */
	public static void generateSquares() {
		// SQRT_MAX_N 이하의 수를 제곱해 보면서 담는다
		for (int num = 0; num <= SQRT_MAX_N; num++) {
			squares[num] = num * num;
		}
	}
}
