// 38920KB, 324ms

package bj14425;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Set<String> set = new HashSet<String>();

		// set에 문자열들을 추가
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			set.add(line);
		}

		// set에서 문자열들을 탐색해서 찾은 개수 출력
		int count = 0;
		for (int i = 0; i < M; i++) {
			String line = br.readLine();
			if (set.contains(line)) {
				count++;
			}
		}

		System.out.println(count);
	}
}
