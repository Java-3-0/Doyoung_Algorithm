// 24288KB, 352ms

package bj1715;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
	public static final int MAX_N = 100000;
	public static int N;

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		PriorityQueue<Long> pq = new PriorityQueue<>();
		for (int i = 0; i < N; i++) {
			pq.offer(Long.parseLong(br.readLine()));
		}
		br.close();

		// 카드 묶음이 한 개가 될 때까지, 제일 작은 묶음 둘을 합치는 작업을 반복
		long count = 0L;
		while (pq.size() >= 2) {
			long sum = pq.poll() + pq.poll();
			count += sum;
			pq.offer(sum);
		}

		System.out.println(count);
	}

}
