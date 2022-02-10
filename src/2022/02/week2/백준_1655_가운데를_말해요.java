// 30772KB, 412ms

package baek1655;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

/** 중간값이 root에 오는 tree */
class MedianHeap {
	/** 작은 쪽 절반을 담을 우선순위 큐 */
	PriorityQueue<Integer> smalls = new PriorityQueue<Integer>(Collections.reverseOrder());
	/** 큰 쪽 절반을 담을 우선순위 큐 */
	PriorityQueue<Integer> bigs = new PriorityQueue<Integer>();

	/** 힙에 정수를 추가 */
	void offer(int x) {
		if (!bigs.isEmpty() && x >= bigs.peek()) {
			bigs.offer(x);
		} else {
			smalls.offer(x);
		}

		balance();
	}

	/** 힙의 root에 있는 중간값을 리턴 */
	int peek() {
		return smalls.peek();
	}

	/** smalls가 bigs보다 하나 많거나 같도록 밸런스를 조정 */
	void balance() {
		while (!bigs.isEmpty() && smalls.size() < bigs.size()) {
			smalls.offer(bigs.poll());
		}

		while (!smalls.isEmpty() && smalls.size() >= bigs.size() + 2) {
			bigs.offer(smalls.poll());
		}
	}
}

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		// 중간값 힙 생성
		MedianHeap medHeap = new MedianHeap();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			int num = Integer.parseInt(br.readLine());
			// 힙에 수 추가
			medHeap.offer(num);
			// 중간값을 peek, 출력에 추가
			sb.append(medHeap.peek()).append("\n");
		}
		
		System.out.print(sb.toString());
	}
}
