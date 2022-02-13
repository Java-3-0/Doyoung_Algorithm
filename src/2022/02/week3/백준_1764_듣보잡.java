// 27836KB, 428ms

package bj1764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	public static Set<String> notHeard = new TreeSet<>();
	public static Set<String> notSeen = new TreeSet<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 듣도 못한 사람 추가
		for (int i = 0; i < N; i++) {
			String name = br.readLine();
			notHeard.add(name);
		}

		// 보도 못한 사람 추가
		for (int i = 0; i < M; i++) {
			String name = br.readLine();
			notSeen.add(name);
		}

		// 듣보잡 출력
		int count = 0;
		for (String name : notHeard) {
			if (notSeen.contains(name)) {
				count++;
				sb.append(name).append("\n");
			}
		}
		System.out.println(count);
		System.out.print(sb.toString());
	}

}
