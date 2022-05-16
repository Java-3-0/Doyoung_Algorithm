// 183816KB, 1384ms

package bj1822;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		TreeSet<Integer> set = new TreeSet<>();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// A 집합 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			set.add(Integer.parseInt(st.nextToken()));
		}

		// B 집합 입력받아서 차집합 처리
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			set.remove(Integer.parseInt(st.nextToken()));
		}

		// 출력
		int size = set.size();
		if (size > 0) {
			sb.append(size).append("\n");
			for (int num : set) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
		}
		else {
			sb.append(0).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main
}