// 11676KB, 108ms

package bj14395;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_NUM = (int) 1e9;
	static final int SQRT_MAX_NUM = (int) Math.sqrt(MAX_NUM);
	static final int HALF_MAX_NUM = MAX_NUM / 2;

	static class Status {
		int num;
		char oper;

		public Status(int num, char oper) {
			super();
			this.num = num;
			this.oper = oper;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		int s = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());

		String answer = bfs(s, t);

		System.out.println(answer);

	} // end main

	private static String bfs(int s, int t) {
		if (s == t) {
			return "0";
		}

		Queue<Status> q = new ArrayDeque<>();
		Set<Integer> visited = new HashSet<>();
		Map<Integer, Status> lastStatus = new HashMap<>();

		visited.add(s);
		q.offer(new Status(s, '0'));

		while (!q.isEmpty()) {
			Status now = q.poll();
			int num = now.num;

			if (num == t) {
				StringBuilder sb = new StringBuilder();

				while (true) {
					if (now.num == s) {
						break;
					}

					sb.append(now.oper);
					now = lastStatus.get(now.num);
				}

				return sb.reverse().toString();
			}

			if (num <= SQRT_MAX_NUM) {
				int nextMult = num * num;
				if (!visited.contains(nextMult)) {
					visited.add(nextMult);
					lastStatus.put(nextMult, now);
					q.offer(new Status(nextMult, '*'));
				}
			}

			if (num <= HALF_MAX_NUM) {
				int nextAdd = num + num;
				if (!visited.contains(nextAdd)) {
					visited.add(nextAdd);
					lastStatus.put(nextAdd, now);
					q.offer(new Status(nextAdd, '+'));
				}
			}

			if (!visited.contains(1)) {
				visited.add(1);
				lastStatus.put(1, now);
				q.offer(new Status(1, '/'));
			}
		}

		return "-1";

	}

}