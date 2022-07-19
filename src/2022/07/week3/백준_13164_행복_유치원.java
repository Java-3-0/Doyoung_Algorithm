// 66008KB, 576ms

package boj13164;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		long[] seq = new long[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 인접한 것끼리 차이들을 내림차순 pq에 넣기
		PriorityQueue<Long> pq = new PriorityQueue<Long>(N - 1, Collections.reverseOrder());
		for (int i = 1; i < N; i++) {
			long diff = seq[i] - seq[i - 1];
			pq.offer(diff);
		}

		// 차이가 큰 것부터 제거
		int cut = K - 1;
		for (int i = 0; i < cut; i++) {
			pq.poll();
		}

		// 남은 것들 합산
		long answer = 0L;
		while (!pq.isEmpty()) {
			answer += pq.poll();
		}

		// 출력
		System.out.println(answer);

	} // end main

}