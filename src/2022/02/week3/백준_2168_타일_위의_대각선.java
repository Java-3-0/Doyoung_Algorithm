// 11516KB, 84ms

package bj2168;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		long x = Long.parseLong(st.nextToken());
		long y = Long.parseLong(st.nextToken());

		long answer = countDiagonals(x, y);

		System.out.println(answer);
	}

	/** 만들어지는 대각선의 개수를 리턴 */
	public static long countDiagonals(long x, long y) {
		// 최대공약수로 가로 세로를 나눈 작은 사각형에서 생기는 대각선의 수 * 최대공약수를 통해서 구한다
		long gcdOfXY = gcd(x, y);
		long netX = x / gcdOfXY;
		long netY = y / gcdOfXY;
		
		return (netX + netY - 1) * gcdOfXY;
	}

	/** 두 수의 최대공약수를 리턴 */
	public static long gcd(long a, long b) {
		if (a == 0L) {
			return b;
		}

		return gcd(b % a, a);
	}
}