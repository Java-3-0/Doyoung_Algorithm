// 438864KB, 2104ms

package bj7662;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	static class DualPriorityQueue {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

		/** 정수를 큐에 삽입한다 */
		public void insert(int commandNum) {
			if (this.map.containsKey(commandNum)) {
				this.map.put(commandNum, this.map.get(commandNum) + 1);
			} else {
				this.map.put(commandNum, 1);
			}
		}

		/** 파라미터로 받은 값이 +1인지 -1인지에 따라 큐에서 최대값 또는 최소값을 삭제한다 */
		public void delete(int commandNum) {
			// 큐가 비어 있는데 들어온 D 연산은 무시
			if (map.isEmpty()) {
				return;
			}

			// 입력에 따라 최대값 또는 최소값을 삭제
			switch (commandNum) {
			case 1:
				deleteLargest();
				break;
			case -1:
				deleteSmallest();
				break;
			default:
				break;
			}
		}

		/** 파라미터로 들어온 수를 큐에서 없앤다 */
		public void removeNumber(int numToRemove) {
			int count = this.map.get(numToRemove);
			// 하나밖에 없는 원소를 삭제하면 키 값 자체를 없앰
			if (count == 1) {
				this.map.remove(numToRemove);
			}
			// 여러 개 있는 원소를 삭제하면 그 키 값에 해당하는 카운트만 줄임
			else {
				this.map.put(numToRemove, count - 1);
			}
		}

		/** 최소값을 삭제 */
		public void deleteSmallest() {
			int numToRemove = peekSmallest();
			removeNumber(numToRemove);
		}

		/** 최대값을 삭제 */
		public void deleteLargest() {
			int numToRemove = peekLargest();
			removeNumber(numToRemove);
		}

		/** 모든 값을 삭제해서 큐를 비움 */
		public void clear() {
			this.map.clear();
		}

		public boolean isEmpty() {
			return this.map.isEmpty();
		}

		public int peekSmallest() {
			return this.map.firstKey();
		}

		public int peekLargest() {
			return this.map.lastKey();
		}
	}

	public static DualPriorityQueue dpq = new DualPriorityQueue();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		TESTS: for (int testCase = 1; testCase <= T; testCase++) {
			dpq.clear();

			int k = Integer.parseInt(br.readLine());
			for (int i = 0; i < k; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				char commandType = st.nextToken().charAt(0);
				int commandNum = Integer.parseInt(st.nextToken());
				handleCommand(commandType, commandNum);
			}

			if (dpq.isEmpty()) {
				sb.append("EMPTY").append("\n");
				continue TESTS;
			} else {
				sb.append(dpq.peekLargest()).append(" ");
				sb.append(dpq.peekSmallest()).append("\n");
			}
		} // end for testCase

		System.out.print(sb.toString());

		return;
	}

	public static void handleCommand(char commandType, int commandNum) {
		switch (commandType) {
		case 'I':
			dpq.insert(commandNum);
			break;
		case 'D':
			dpq.delete(commandNum);
			break;
		default:
			break;
		}

		return;
	}
}