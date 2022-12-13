import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GcdLcmTemplate {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");

			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
		}

		System.out.print(sb.toString());

	} // end main

	public static long getGCD(long a, long b) {
		if (a == 0) {
			return b;
		}

		return getGCD(b % a, a);
	}

	public static long getLCM(long a, long b) {
		long gcd = getGCD(a, b);
		return a / gcd * b;
	}

}