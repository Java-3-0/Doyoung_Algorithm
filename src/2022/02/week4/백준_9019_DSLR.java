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
		// 현재 레지스터에 있는 수
		int num;
		// 이 수가 만들어지기까지 거쳐온 명령어들
		String operations;

		// 현재 수와 현재까지의 명령어 순서를 받아서 상태 객체를 생성하는 생성자
		public Status(int num, String operations) {
			super();
			this.num = num;
			this.operations = operations;
		}
		
		// 계산기에서 한 번의 연산을 한 후 가능한 상태들을 배열 형태로 리턴
		public Status[] nextStatus() {
			Status[] ret = new Status[4];
			ret[0] = funD();
			ret[1] = funS();
			ret[2] = funL();
			ret[3] = funR();

			return ret;
		}

		// 계산기의 D 연산을 한 후의 결과를 리턴
		public Status funD() {
			int nextNum = (2 * this.num) % MOD;
			String nextOp = this.operations + "D";
			
			return new Status(nextNum, nextOp);
		}
		
		// 계산기의 S 연산을 한 후의 결과를 리턴
		public Status funS() {
			int nextNum = (this.num == 0) ? 9999 : this.num - 1;
			String nextOp = this.operations + "S";
			
			return new Status(nextNum, nextOp);
		}
		
		// 계산기의 L 연산을 한 후의 결과를 리턴
		public Status funL() {
			int firstDigit = this.num / 1000;
			int nextNum = (this.num * 10 + firstDigit) % MOD;
			String nextOp = this.operations + "L";
			
			return new Status(nextNum, nextOp);
		}

		// 계산기의 R 연산을 한 후의 결과를 리턴
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

		// 테스트케이스 수 입력
		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 시작 수, 목표 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int finish = Integer.parseInt(st.nextToken());
			
			// 최소 연산 수로 start를 finish로 도달하는 명령어 순서를 구한다.
			String answer = getMinOperations(start, finish);
			
			// 구한 답을 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	} // end main

	/** start를 finish로 변환하는 최단 명령어 순서를 리턴 */
	public static String getMinOperations(int start, int finish) {
		boolean[] isVisited = new boolean[MOD];
		Queue<Status> q = new LinkedList<>();
		
		// 첫 상태를 큐에 넣는다.
		isVisited[start] = true;
		q.offer(new Status(start, ""));

		while (!q.isEmpty()) {
			Status here = q.poll();
			// 도달했다면 끝낸다.
			if (here.num == finish) {
				return here.operations;
			}

			// 다음 상태들을 큐에 넣는다.
			Status[] there = here.nextStatus();
			for (int i = 0; i < there.length; i++) {
				if (!isVisited[there[i].num]) {
					isVisited[there[i].num]= true; 
					q.offer(there[i]);
				}
			}
			
		}

		return "";
	}

}