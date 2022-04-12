// 41896KB, 460ms

package bj10025;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static class Bucket implements Comparable<Bucket> {
		int ice;
		int position;

		public Bucket(int ice, int position) {
			super();
			this.ice = ice;
			this.position = position;

		}

		@Override
		public int compareTo(Bucket b) {
			return this.position - b.position;
		}

		@Override
		public String toString() {
			return "Bucket [ice=" + ice + ", position=" + position + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 양동이들 입력
		Bucket[] buckets = new Bucket[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int ice = Integer.parseInt(st.nextToken());
			int position = Integer.parseInt(st.nextToken());
			buckets[i] = new Bucket(ice, position);
		}

		// 양동이들 위치 오름차순으로 정렬
		Arrays.sort(buckets);

		// 투포인터
		int leftIdx = 0;
		int rightIdx = 0;
		int maxSum = buckets[0].ice;
		int sum = buckets[0].ice;

		while (rightIdx < N) {
			int diff = buckets[rightIdx].position - buckets[leftIdx].position;

			if (diff <= 2 * K) {
				maxSum = maxSum < sum ? sum : maxSum;

				if (rightIdx == N - 1) {
					break;
				}

				sum += buckets[++rightIdx].ice;
			} else {
				sum -= buckets[leftIdx++].ice;
			}
		}

		// 출력
		System.out.println(maxSum);

	} // end main

}