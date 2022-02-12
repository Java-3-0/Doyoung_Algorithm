// 50516KB, 580ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 100000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine(), " ");
		Set<Integer> numbers = new HashSet<>();

		for (int i = 0; i < N; i++) {
			int num = Integer.parseInt(st.nextToken());
			numbers.add(num);
		}

		int M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			int num = Integer.parseInt(st.nextToken());
			if (numbers.contains(num)) {
				sb.append(1);
			}
			else {
				sb.append(0);
			}
			sb.append("\n");
		}

		System.out.println(sb);
	}
}
