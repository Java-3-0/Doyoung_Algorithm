// 17640KB, 296ms

package bj2109;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_DAY = 10000;

	static class Lecture implements Comparable<Lecture> {
		int pay;
		int deadline;

		public Lecture(int pay, int deadline) {
			super();
			this.pay = pay;
			this.deadline = deadline;

		}

		/** 페이 내림차순, 같으면 데드라인 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Lecture l) {
			if (this.pay == l.pay) {
				return this.deadline - l.deadline;
			}
			return -(this.pay - l.pay);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 수업 개수 입력
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Lecture> pq = new PriorityQueue<>();

		// 수업들을 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int pay = Integer.parseInt(st.nextToken());
			int deadline = Integer.parseInt(st.nextToken());
			pq.offer(new Lecture(pay, deadline));
		}

		// 각 날짜마다 수업이 잡혀 있는지 여부를 저장할 배열
		boolean[] isScheduled = new boolean[MAX_DAY + 1];

		// 가장 비용이 높은 수업부터 처리
		int answer = 0;
		while (!pq.isEmpty()) {
			Lecture l = pq.poll();
			for (int day = l.deadline; day >= 1; day--) {
				if (!isScheduled[day]) {
					isScheduled[day] = true;
					answer += l.pay;
					break;
				}
			}
		}

		// 정답 출력
		System.out.println(answer);

	}

}
