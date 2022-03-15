// 20100KB, 312ms

package bj8980;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static class Shipment implements Comparable<Shipment> {
		int from;
		int to;
		int cnt;

		public Shipment(int from, int to, int cnt) {
			super();
			this.from = from;
			this.to = to;
			this.cnt = cnt;
		}

		/** 도착 마을 번호 오름차순, 같다면 시작 마을 번호 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Shipment s) {
			if (this.to == s.to) {
				return this.from - s.from;
			}
			return this.to - s.to;
		}

		@Override
		public String toString() {
			return "[from=" + from + ", to=" + to + ", cnt=" + cnt + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 마을 수, 트럭 용량 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());

		// 배송 요청 개수 입력
		int M = Integer.parseInt(br.readLine());

		// 배송 요청을 도착지 오름차순, 같다면 시작지 오름차순으로 뽑아오는 우선순위 큐
		PriorityQueue<Shipment> shipments = new PriorityQueue<>();

		// 배송 요청 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cnt = Integer.parseInt(st.nextToken());
			shipments.offer(new Shipment(from, to, cnt));
		}

		// 초기 상태
		int[] canLoad = new int[N + 1];
		Arrays.fill(canLoad, C);
		int totalDelivery = 0;

		// 모든 배송 요청들을 처리할 때까지 반복
		while (!shipments.isEmpty()) {
			// 배송 요청
			Shipment in = shipments.poll();

			// 새로 싣을 수 있는 수량 파악
			int cntToLoad = in.cnt;
			for (int i = in.from; i < in.to; i++) {
				cntToLoad = canLoad[i] < cntToLoad ? canLoad[i] : cntToLoad;
			}

			// 싣을 수 있는 수량이 있다면 그만큼 싣는다.
			if (cntToLoad >= 0) {
				for (int pos = in.from; pos < in.to; pos++) {
					canLoad[pos] -= cntToLoad;
				}

				totalDelivery += cntToLoad;
			}
		}

		// 결과 출력
		System.out.println(totalDelivery);
	}

}
