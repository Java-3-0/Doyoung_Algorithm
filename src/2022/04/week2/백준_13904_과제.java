// 18456KB, 252ms

package bj13904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int MAX_D = 1000;
	static final int MAX_W = 100;

	static class Homework implements Comparable<Homework> {
		int dueDate;
		int score;

		public Homework(int dueDate, int score) {
			super();
			this.dueDate = dueDate;
			this.score = score;
		}

		/** 마감일 내림차순 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Homework h) {
			return -(this.dueDate - h.dueDate);
		}

		@Override
		public String toString() {
			return "Homework [dueDate=" + dueDate + ", score=" + score + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 모든 과제들이 마감일 내림차순으로 담길 pq
		PriorityQueue<Homework> hwsAll = new PriorityQueue<>();
		// 지금 할 수 있는 과제들이 점수 내림차순으로 담길 pq
		PriorityQueue<Homework> hwsCanDo = new PriorityQueue<>((h1, h2) -> -(h1.score - h2.score));
		
		// 과제 입력받아서 hwsAll에 추가
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int d = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			hwsAll.offer(new Homework(d, w));
		}

		// 마지막날부터 첫날까지, 할 수 있는 과제 중 점수가 가장 높은 과제 선택
		int answer = 0;
		for (int day = MAX_D; day >= 1; day--) {
			while (!hwsAll.isEmpty() && hwsAll.peek().dueDate >= day) {
				Homework hw = hwsAll.poll();
				hwsCanDo.offer(hw);
			}
			
			if (!hwsCanDo.isEmpty()) {
				Homework hwToDo = hwsCanDo.poll();
				answer += hwToDo.score;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

}