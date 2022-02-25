// 13372KB, 632ms

package bj6064;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 입력
			st = new StringTokenizer(br.readLine(), " ");
			int M = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			// 연도 계산
			int answer = getYear(M, N, x, y);
			
			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	/** M, N, x, y를 입력받아 카잉 달력이 나타내는 연도를 리턴 */
	public static int getYear(int M, int N, int x, int y) {
		int count = x;
		// 달력 첫 숫자를 맞춰 놓고, 두 번째 숫자를 M씩 키워본다.
		for (int second = x; second < x + lcm(M, N); second += M) {
			if ((second % N == 0 ? N : second % N) == y) {
				return count;
			}
			
			count += M;
		}
		
		return -1;
	}
	
	public static int gcd (int A, int B) {
		if (A == 0) return B;
		
		return gcd(B % A, A);
	}
	
	public static int lcm (int A, int B) {
		return A * B / gcd(A, B);
	}

}