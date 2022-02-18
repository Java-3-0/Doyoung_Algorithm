// 381436KB, 1760ms

package bj2776;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			// 수첩 1 입력
			int N = Integer.parseInt(br.readLine());
			Set<Integer> note1 = new HashSet<> (N);
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				note1.add(Integer.parseInt(st.nextToken()));
			}
			
			// 수첩 2 입력받으면서 수첩 1에 있는지 없는지 출력에 추가
			int M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < M; i++) {
				int num = Integer.parseInt(st.nextToken());
				if (note1.contains(num)) {
					sb.append("1\n");
				} else {
					sb.append("0\n");
				}
			}
		}
		
		// 출력
		System.out.print(sb.toString());
	}
}