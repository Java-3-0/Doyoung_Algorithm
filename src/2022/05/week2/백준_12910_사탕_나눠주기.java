// 11724KB, 84ms

package bj12910;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());

		int[] counts = new int[50 + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int input = Integer.parseInt(st.nextToken());
			counts[input]++;
		}

		int answer = 0;
		for (int k = 1; k <= N; k++) {
			int product = 1;
			for (int brand = 1; brand <= k; brand++) {
				product *= counts[brand];
			}
			
			answer += product;
		}
		
		System.out.println(answer);

	} // end main
}