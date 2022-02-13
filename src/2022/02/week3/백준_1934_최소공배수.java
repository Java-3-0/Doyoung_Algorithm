package bj1934;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 0; t < T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			sb.append(lcm(A, B)).append("\n");
		}
		
		System.out.print(sb.toString());
	}
	
	public static int gcd (int A, int B) {
		// 앞의 수가 뒤의 수보다 큰 상태로 유지
		if (A < B) {
			return gcd(B, A);
		}
		
		if (B == 0) return A;
		
		return gcd(B, A % B);
	}
	
	public static int lcm (int A, int B) {
		return A * B / gcd(A, B);
	}

}