// 120572KB, 1272ms

package bj1202;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300000, MAX_K = 300000;

	static class Jewel implements Comparable<Jewel> {
		int weight;
		long value;

		public Jewel(int weight, long value) {
			super();
			this.weight = weight;
			this.value = value;
		}

		/** 무게 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Jewel j) {
			return this.weight - j.weight;
		}

		@Override
		public String toString() {
			return "Jewel [weight=" + weight + ", value=" + value + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 보석 정보 입력
		PriorityQueue<Jewel> pqWeight = new PriorityQueue<>(cmpWeightAsc);
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int weight = Integer.parseInt(st.nextToken());
			long value = Long.parseLong(st.nextToken());
			pqWeight.offer(new Jewel(weight, value));
		}

		// 가방 정보 입력
		int[] bags = new int[K];
		for (int i = 0; i < K; i++) {
			bags[i] = Integer.parseInt(br.readLine());
		}

		// 무게 오름차순 정렬
		Arrays.sort(bags);

		// 가치 내림차순으로 꺼내오기 위한 pq
		PriorityQueue<Jewel> pqValue = new PriorityQueue<>(cmpValueDesc);

		// 모든 가방에 대해 수행
		long answer = 0L;
		for (int b = 0; b < K; b++) {
			// 이 가방의 한도보다 무게가 작은 보석들을 모두 pqValue에 넣는다.
			while (!pqWeight.isEmpty() && pqWeight.peek().weight <= bags[b]) {
				pqValue.offer(pqWeight.poll());
			}

			// pqValue에서 가장 가치가 높은 보석을 이 가방에 담는다.
			if (!pqValue.isEmpty()) {
				Jewel picked = pqValue.poll();
				answer += picked.value;
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

	/** 무게 오름차순 정렬을 위한 비교함수 */
	public static Comparator<Jewel> cmpWeightAsc = new Comparator<Jewel>() {
		@Override
		public int compare(Jewel j1, Jewel j2) {
			return j1.weight - j2.weight;
		}
	};

	/** 가치 내림차순 정렬을 위한 비교함수 */
	public static Comparator<Jewel> cmpValueDesc = new Comparator<Jewel>() {
		@Override
		public int compare(Jewel j1, Jewel j2) {
			if (j1.value < j2.value) {
				return 1;
			} else if (j1.value == j2.value) {
				return 0;
			} else {
				return -1;
			}
		}
	};
}