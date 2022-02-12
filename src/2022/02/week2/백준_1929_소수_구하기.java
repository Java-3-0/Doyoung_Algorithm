// 18696KB, 188ms

package baek11650;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_NUM = 1000000;
	public static boolean[] isPrime = new boolean[MAX_NUM + 1];
	
	public static void main(String[] args) throws IOException {
//		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		
		setPrimes();
		
		for (int num = M; num <= N; num++) {
			if (isPrime[num]) {
				sb.append(num).append("\n");
			}
		}

		System.out.println(sb.toString());

	}
	
	public static void setPrimes () {
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;
		
		for (int num = 2; num <= MAX_NUM; num++) {
			if (!isPrime[num]) {
				continue;
			}
			
			for (int x = num + num; x <= MAX_NUM; x += num) {
				isPrime[x] = false;
			}
		}
	}
}
