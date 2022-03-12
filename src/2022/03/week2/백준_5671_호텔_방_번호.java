// 13896KB, 116ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_M = 5000;
	static final int DIGITS = 10;
	
	static boolean[] isRepeating = new boolean[MAX_M + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		precalc();
		
		String line = "";
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line, " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int cnt = 0;
			for (int i = N; i <= M; i++) {
				if (!isRepeating[i]) {
					cnt++;
				}
			}
			
			sb.append(cnt).append("\n");
		}
		
		System.out.print(sb.toString());
	}

	public static void precalc() {
		numLoop : for (int num = 0; num <= MAX_M; num++) {
			if (isRepeating(num)) {
				isRepeating[num] = true;
			}
		}
	}
	
	public static boolean isRepeating(int n) {
		boolean[] isUsed = new boolean[DIGITS];
		while (n > 0) {
			int digit = n % 10;
			if (isUsed[digit]) {
				return true;
			}
			isUsed[digit] = true;
			n /= 10;
		}
		
		return false;
	}

}