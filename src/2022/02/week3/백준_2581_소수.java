package bj2581;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 10000;
	public static boolean[] isPrime = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int M = Integer.parseInt(br.readLine());
		int N = Integer.parseInt(br.readLine());

		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;

		for (int num = 2; num <= MAX_N; num++) {
			if (!isPrime[num]) {
				continue;
			}

			for (int x = num + num; x <= MAX_N; x += num) {
				isPrime[x] = false;
			}
		}

		int minPrime = -1;
		int sum = 0;
		for (int x = N; x >= M; x--) {
			if (isPrime[x]) {
				minPrime = x;
				sum += x;
			}
		}

		if (minPrime == -1) {
			System.out.println(minPrime);
		} else {
			System.out.println(sum);
			System.out.println(minPrime);
		}
	}

}
