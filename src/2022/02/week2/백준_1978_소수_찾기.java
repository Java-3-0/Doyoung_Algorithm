// 11608KB, 84ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_NUM = 1000;
	
	public static void main(String[] args) throws IOException {
//		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		
		boolean[] isPrime = new boolean[MAX_NUM + 1];
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
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int count = 0;
		for (int i = 0; i < N; i++) {
			int input = Integer.parseInt(st.nextToken());
			if (isPrime[input]) {
				count++;
			}
		}
		
		System.out.println(count);
	}
}
