package pg92335;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int answer = solution(N, K);
		
		System.out.println(answer);

	} // end main

	/** n을 k진수로 변환했을 때 조건에 맞는 소수의 개수를 리턴 */
	public static int solution(int n, int k) {
		// n을 k진수로 변환
		String s = nToBaseK(n, k);
		
		// 0을 기준으로 토크나이즈
		StringTokenizer st = new StringTokenizer(s, "0");
		
		// 토큰들 중 소수인 것의 개수 카운트
		int answer = 0;
		while (st.hasMoreTokens()) {
			long num = Long.parseLong(st.nextToken());
			if (isPrime(num)) {
				answer++;
			}
		}
		
		return answer;
	}

	/** n을 K진수로 표현한 문자열을 리턴 */
	public static String nToBaseK(int n, int k) {
		StringBuilder sb = new StringBuilder();
		while (n > 0) {
			sb.append(n % k);
			n /= k;
		}

		return sb.reverse().toString();
	}
	
	/** num이 소수이면 true, 아니면 false를 리턴 */
	public static boolean isPrime(long num) {
		// 0, 1은 소수가 아니다
		if (num == 0 || num == 1) {
			return false;
		}
		
		// 2 이상의 수의 소수 판정
		long sqrt = (long) Math.sqrt(num);
		for (long i = 2L; i <= sqrt; i++) {
			if (num % i == 0L) {
				return false;
			}
		}
		
		return true;
	}
}