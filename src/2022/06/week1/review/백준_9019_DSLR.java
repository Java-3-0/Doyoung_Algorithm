// 297504KB, 5584ms

package bj9019;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MOD = 10000;

	static class Status {
		int num;
		String operations;

		public Status(int num, String operations) {
			super();
			this.num = num;
			this.operations = operations;
		}
		
		public Status[] getNextStatus() {
			Status[] ret = new Status[4];
			ret[0] = funD();
			ret[1] = funS();
			ret[2] = funL();
			ret[3] = funR();

			return ret;
		}

		public Status funD() {
			int nextNum = (2 * this.num) % MOD;
			String nextOp = this.operations + "D";
			
			return new Status(nextNum, nextOp);
		}
		
		public Status funS() {
			int nextNum = (this.num == 0) ? 9999 : this.num - 1;
			String nextOp = this.operations + "S";
			
			return new Status(nextNum, nextOp);
		}
		
		public Status funL() {
			int firstDigit = this.num / 1000;
			int nextNum = (this.num * 10 + firstDigit) % MOD;
			String nextOp = this.operations + "L";
			
			return new Status(nextNum, nextOp);
		}

		public Status funR() {
			int lastDigit = this.num % 10;
			int nextNum = (lastDigit * 1000 + (this.num / 10)) % MOD;
			String nextOp = this.operations + "R";
			
			return new Status(nextNum, nextOp);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int finish = Integer.parseInt(st.nextToken());
			String answer = getOptimalOperations(start, finish);
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());
	} // end main

	/** bfs를 통해 최적의 연산 과정을 구해서 리턴 */
	public static String getOptimalOperations(int start, int finish) {
		boolean[] isVisited = new boolean[MOD];
		Queue<Status> q = new LinkedList<>();
		
		// 초기 상태 처리
		isVisited[start] = true;
		q.offer(new Status(start, ""));

		// bfs 수행
		while (!q.isEmpty()) {
			Status here = q.poll();
			if (here.num == finish) {
				return here.operations;
			}

			Status[] next = here.getNextStatus();
			for (int i = 0; i < next.length; i++) {
				if (!isVisited[next[i].num]) {
					isVisited[next[i].num]= true; 
					q.offer(next[i]);
				}
			}
		}

		return "";
	}

}