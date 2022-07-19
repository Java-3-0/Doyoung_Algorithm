// 30176KB, 356ms

package boj15708;

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

		st = new StringTokenizer(br.readLine(), " ");
		long N = Long.parseLong(st.nextToken());
		long T = Long.parseLong(st.nextToken());
		long P = Long.parseLong(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		
		// 내림차순 꺼내올 큐
		PriorityQueue<Long> pq = new PriorityQueue<Long>(Collections.reverseOrder());
		
		long sumOfWorkTime = 0L;
		int answer = 0;
		OUTER: for (long pos = 0L; pos < N; pos++) {
			// 일하는 데 걸리는 시간 입력받아서 전체 일의 시간 계산
			long workTime = Long.parseLong(st.nextToken());
			sumOfWorkTime += workTime;
			pq.offer(workTime);
			
			// 이동하는 데 걸리는 시간 계산
			long moveTime = P * pos;
			
			// 불가능하면 오래 걸리는 일부터 하나씩 제거
			while (T < sumOfWorkTime + moveTime) {
				if (pq.isEmpty()) {
					break OUTER;
				}
				
				long out = pq.poll();
				sumOfWorkTime -= out;
			}
			
			// 남은 일들은 가능
			answer = Math.max(answer, pq.size());
		}
		
		System.out.println(answer);

	} // end main

}