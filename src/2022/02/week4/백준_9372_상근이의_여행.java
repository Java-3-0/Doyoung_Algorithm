// 22876KB, 164ms

package bj9372;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		
		for (int testCase = 0; testCase < TESTS; testCase++) {
			// V, E 입력
			st = new StringTokenizer(br.readLine(), " ");
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			// 각 비행기 정보 입력이 필요없으니까 버린다.
			for (int i = 0; i < E; i++) {
				br.readLine();
			}
			
			// V개의 정점을 연결하려면 V - 1개 간선이 필요하다.
			// 모든 간선의 weight가 1이므로 그냥 V - 1이 답이다.
			int answer = V - 1;
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main
}