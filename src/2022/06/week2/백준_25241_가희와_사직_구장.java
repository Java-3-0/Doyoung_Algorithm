// 17004KB, 572ms

package bj25241;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static class Status implements Comparable<Status> {
		/** 열차 번호 */
		int trainNum;
		/** 시발역에서 출발한 시각 */
		int startTime;
		/** 현재 시각 */
		int curTime;
		/** 현재 위치 */
		int position;
		/** 하행 여부 */
		boolean isHaHaeng;

		public Status(int trainNum, int startTime, int curTime, int position, boolean isHaHaeng) {
			super();
			this.trainNum = trainNum;
			this.startTime = startTime;
			this.curTime = curTime;
			this.position = position;
			this.isHaHaeng = isHaHaeng;
		}

		@Override
		public String toString() {
			return "Status [trainNum=" + trainNum + ", startTime=" + startTime + ", curTime=" + curTime + ", position="
					+ position + ", isHaHaeng=" + isHaHaeng + "]";
		}

		@Override
		public int compareTo(Status s) {
			if (this.curTime == s.curTime) {
				if (this.startTime == s.startTime) {
					return this.trainNum - s.trainNum;
				}
				return this.startTime - s.startTime;
			}
			return this.curTime - s.curTime;
		}

	}

	static class Result implements Comparable<Result> {
		int trainNum;
		int arrivalTime;

		public Result(int trainNumber, int arrivalTime) {
			super();
			this.trainNum = trainNumber;
			this.arrivalTime = arrivalTime;
		}

		@Override
		public String toString() {
			return "Result [trainNumber=" + trainNum + ", arrivalTime=" + arrivalTime + "]";
		}

		@Override
		public int compareTo(Result r) {
			return this.trainNum - r.trainNum;
		}

	}

	static final int MAX_TIME = 24 * 60;

	/** 0 : 부발 ~ 가남, 1 : 가남 ~ 감곡장호원, 2 : 감곡장호원 ~ 앙성온천, 3 : 앙성온천 ~ 충주 */
	static int[] isUsedUntil = new int[4];
	static int[] timeNeeded = { 7, 7, 8, 10 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 열차 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int C = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());

		PriorityQueue<Status> pq = new PriorityQueue<>();
		// 하행선 입력
		for (int i = 0; i < C; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int trainNum = Integer.parseInt(st.nextToken());

			String timeStr = st.nextToken();
			st = new StringTokenizer(timeStr, ":");
			int hour = Integer.parseInt(st.nextToken());
			int minute = Integer.parseInt(st.nextToken());
			int startTime = 60 * hour + minute;

			pq.offer(new Status(trainNum, startTime, startTime, 0, true));
		}

		// 상행선 입력
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int trainNum = Integer.parseInt(st.nextToken());

			String timeStr = st.nextToken();
			st = new StringTokenizer(timeStr, ":");
			int hour = Integer.parseInt(st.nextToken());
			int minute = Integer.parseInt(st.nextToken());
			int startTime = 60 * hour + minute;

			pq.offer(new Status(trainNum, startTime, startTime, 3, false));
		}

		// 열차 운행 시뮬레이션
		PriorityQueue<Result> results = new PriorityQueue<>();
		while (!pq.isEmpty()) {
			Status now = pq.poll();

			// 종착역에 도착한 경우
			if ((now.isHaHaeng && now.position == 4) || (!now.isHaHaeng && now.position == -1)){
				results.add(new Result(now.trainNum, now.curTime));
				continue;
			}
			
			int rangeToGo = now.position;
			
			// 운행 가능한 경우
			if (isUsedUntil[rangeToGo] <= now.curTime) {
				isUsedUntil[rangeToGo] = now.curTime + timeNeeded[now.position];
				int nextPosition = now.isHaHaeng ? now.position + 1 : now.position - 1;
				int nextTime = now.curTime + timeNeeded[now.position] + 1;
				Status nextStatus = new Status(now.trainNum, now.startTime, nextTime, nextPosition, now.isHaHaeng);
				pq.offer(nextStatus);
			}
			
			// 기다려야 하는 경우
			else {
				now.curTime = isUsedUntil[rangeToGo];
				pq.offer(now);
			}
		}

		// 결과 출력
		while (!results.isEmpty()) {
			int time = results.poll().arrivalTime;
			time -= 1; // 마지막 역에선 1분 대기를 안 해야 하므로 다시 뺀다.
			time %= MAX_TIME;
			int hour = time / 60;
			int minute = time % 60;

			sb.append(String.format("%02d", hour)).append(":").append(String.format("%02d", minute)).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}