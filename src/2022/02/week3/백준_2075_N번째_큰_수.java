// 298048KB, 912ms

package bj2075;

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
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());
		
		// 가장 작은 수가 가장 높은 우선순위를 갖는 큐
		PriorityQueue<Integer> pq = new PriorityQueue<>(N);
		
		// 첫 행을 pq에 담아둠
		st = new StringTokenizer(br.readLine(), " ");
		for (int x = 0; x < N; x++) {
			pq.offer(Integer.parseInt(st.nextToken()));
		}
		
		// 두 번째 행부터 하나 담을 때마다 제일 작은 것 하나 poll
		for (int y = 1; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				pq.offer(Integer.parseInt(st.nextToken()));
				pq.poll();
			}
		}
		
		// pq에 맨 앞에 있는 것이 정답
		System.out.println(pq.peek());
	}
}