// 11576KB, 72ms

package bj9613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			// 입력
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			long[] arr = new long[N];
			for (int i = 0; i < N; i++) {
				arr[i] = Long.parseLong(st.nextToken());
			}
			
			// gcd 합 계산
			long sum = 0;
			for (int i = 0; i < N; i++) {
				for (int j = i + 1; j < N; j++) {
					sum += gcd(arr[i], arr[j]);
				}
			}
			
			// 출력에 append
			sb.append(sum).append("\n");
		}
	
		System.out.print(sb.toString());
	}

	/** a와 b의 최대공약수를 리턴 */
	public static long gcd(long a, long b) {
		if (a == 0) {
			return b;
		}

		return gcd(b % a, a);
	}

}


