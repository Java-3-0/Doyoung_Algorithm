// 26272KB, 340ms

package baek1927;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			int answer = 0;
			int inputNum = Integer.parseInt(br.readLine());
			
			// 입력이 0이면 힙에서 pop해서 출력
			if (inputNum == 0) {
				if (!minHeap.isEmpty()) {
					answer = minHeap.poll();
				}
				sb.append(answer).append("\n");
			}
			// 입력이 0이 아니면 힙에 넣음
			else {
				minHeap.offer(inputNum);
			}
		}
		
		System.out.print(sb.toString());
	}
	
}
