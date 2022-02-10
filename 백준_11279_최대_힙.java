// 26556KB, 332ms

package baek11279;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			int answer = 0;
			int inputNum = Integer.parseInt(br.readLine());
			
			// 입력이 0이면 힙에서 pop해서 출력
			if (inputNum == 0) {
				if (!maxHeap.isEmpty()) {
					answer = maxHeap.poll();
				}
				sb.append(answer).append("\n");
			}
			// 입력이 0이 아니면 힙에 넣음
			else {
				maxHeap.offer(inputNum);
			}
		}
		
		System.out.print(sb.toString());
	}
	
}
