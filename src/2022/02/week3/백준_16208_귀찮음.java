package bj16208;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine(), " ");
		BigInteger[] sticks = new BigInteger[N];
		BigInteger sum = new BigInteger("0");
		for (int i = 0; i < N; i++) {
			sticks[i] = new BigInteger(st.nextToken());
			sum = sum.add(sticks[i]);
		}
		
		BigInteger answer = new BigInteger("0");
		for (BigInteger b : sticks) {
			sum = sum.subtract(b);
			answer = answer.add(sum.multiply(b));
		}
		
		System.out.println(answer.toString());
	}
}