// 21456KB, 324ms

package baek2346;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Balloon {
	/** 풍선의 번호 */
	int num;
	/** 풍선에 적혀 있는 수로, 이동할 칸수를 의미 */
	int written;

	public Balloon(int num, int written) {
		super();
		this.num = num;
		this.written = written;
	}
}

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();

		// 풍선들을 입력받아서 deque에 추가
		Deque<Balloon> deque = new ArrayDeque<Balloon>();
		for (int i = 0; i < N; i++) {
			deque.addLast(new Balloon(i + 1, sc.nextInt()));
		}

		// 터트리는 순서 계산
		Integer[] order = getPopOrder(deque);

		// 출력
		StringBuilder sb = new StringBuilder();
		for (int balloonNum : order) {
			sb.append(balloonNum).append(" ");
		}
		sb.append("\n");

		System.out.print(sb.toString());
	}

	public static Integer[] getPopOrder(Deque<Balloon> deque) {
		Integer[] ret = new Integer[deque.size()];
		int idx = 0;

		while (true) {
			// 풍선 하나를 터트림
			Balloon blowUp = deque.pollFirst();
			ret[idx++] = blowUp.num;
			int move = blowUp.written;

			// 다 터트렸다면 루프 종료
			if (deque.isEmpty()) {
				break;
			}

			// 양수인 경우 (move - 1)개 스킵
			if (move >= 0) {
				for (int i = 0; i < move - 1; i++) {
					deque.addLast(deque.pollFirst());
				}
			}
			// 음수인 경우 (-move)개 스킵
			else {
				for (int i = 0; i < (-move); i++) {
					deque.addFirst(deque.pollLast());
				}
			}
		}

		return ret;
	}
}