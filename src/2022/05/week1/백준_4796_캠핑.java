// 12000KB, 92ms

package bj4796;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int tc = 1;
		while (true) {
			st = new StringTokenizer(br.readLine(), " ");
			int L = Integer.parseInt(st.nextToken());
			int P = Integer.parseInt(st.nextToken());
			int V = Integer.parseInt(st.nextToken());
			
			if (L == 0 && P == 0 && V == 0) {
				break;
			}
			
			int answer = solve(L, P, V);
			
			sb.append("Case ").append(tc).append(": ").append(answer).append("\n");
			tc++;
		}
		
		System.out.print(sb.toString());
		
	} // end main

	public static int solve (int L, int P, int V) {
		int ret = V / P * L + Math.min(L, V % P);
		
		return ret;
	}
	
}