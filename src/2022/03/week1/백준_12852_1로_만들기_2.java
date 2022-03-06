// 59444KB, 348ms

package bj12852;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 1000000;
	public static int[] cache = new int[MAX_N + 1];
	public static int[] nextNum = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 캐시 초기화
		Arrays.fill(cache, -1);
		Arrays.fill(nextNum, -1);
		
		// 입력
		int N = Integer.parseInt(br.readLine());
		br.close();
		
		// 정답 계산
		int answer = minOperations(N);
		
		// 출력
		sb.append(answer).append("\n");
		
		int x = N;
		while (x != 1) {
			sb.append(x).append(" ");
			x = nextNum[x];
		}
		sb.append(x).append("\n");

		System.out.print(sb.toString());
	}

	public static int minOperations(int x) {
		// base case
		if (x == 1)
			return 0;

		// 캐시에 계산한 적 있는 경우
		if (cache[x] != -1)
			return cache[x];

		// 새로 계산하는 경우
		int ret = 1 + minOperations(x - 1);
		nextNum[x] = x - 1;
		
		if (x % 3 == 0 && 1 + minOperations(x / 3) < ret) {
			ret = 1 + minOperations(x / 3);
			nextNum[x] = x / 3;
		}
		if (x % 2 == 0 && 1 + minOperations(x / 2) < ret) {
			ret = 1 + minOperations(x / 2);
			nextNum[x] = x / 2;
		}
		return cache[x] = ret;
	}

}