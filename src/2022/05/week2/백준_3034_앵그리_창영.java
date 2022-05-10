// 11516KB, 72ms

package bj3034;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());

		int maxPossible = (int) Math.floor(Math.sqrt(W * W + H * H));
		
		for (int i = 0; i < N ; i++) {
			int k = Integer.parseInt(br.readLine());
			if (k <= maxPossible) {
				sb.append("DA");
			}
			else {
				sb.append("NE");
			}
			sb.append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
}