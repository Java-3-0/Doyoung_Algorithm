// 24704KB, 332ms

package baek11286;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

class AbsHeap {
	/** 음수가 절대값이 작은 순으로 우선순위를 갖게 담기는 큐 */
	PriorityQueue<Integer> negatives = new PriorityQueue<Integer>(Collections.reverseOrder());
	/** 양수가 절대값이 작은 순으로 우선순위를 갖게 담기는 큐 */
	PriorityQueue<Integer> positives = new PriorityQueue<Integer>();

	/** 힙에 정수 x를 넣는다 */
	void add(int x) {
		if (x >= 0) {
			positives.add(x);
		} else {
			negatives.add(x);
		}
	}

	/** 절대값이 가장 작은 값을 힙에서 제거하고 리턴 */
	int poll() {
		int ret;

		// 음수, 양수 양 쪽이 다 빈 경우
		if (negatives.isEmpty() && positives.isEmpty()) {
			ret = 0;
		}
		// 음수 쪽만 빈 경우
		else if (negatives.isEmpty()) {
			ret = positives.poll();
		}
		// 양수 쪽만 빈 경우
		else if (positives.isEmpty()) {
			ret = negatives.poll();
		}
		// 양쪽 다 수가 존재하는 경우
		else {
			int maxNegative = negatives.peek();
			int minPositive = positives.peek();

			// 음수 쪽의 절댓값이 더 작거나 같은 경우, 음수 쪽에서 poll
			if ((-maxNegative) <= minPositive) {
				ret = negatives.poll();
			}
			// 아닌 경우, 양수 쪽에서 poll
			else {
				ret = positives.poll();
			}
		}

		return ret;
	}
}

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		// 절댓값 힙 생성
		AbsHeap heap = new AbsHeap();

		// 입력을 받아서 0이면 poll, 다른 것이면 add
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			int inputNum = Integer.parseInt(br.readLine());

			if (inputNum == 0) {
				sb.append(heap.poll()).append("\n");
			} else {
				heap.add(inputNum);
			}
		}

		// 결과 출력
		System.out.print(sb.toString());

	}

}
