// 88504KB, 796ms

package baek5430;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

class DequeEmptyException extends Exception {
	private static final long serialVersionUID = -2284720971332312338L;
}

class MyDeque {
	private Deque<Integer> deque = new LinkedList<>();
	private boolean isReverse = false;

	public MyDeque(Deque<Integer> deque) {
		super();
		this.deque = deque;
	}

	/** 뒤집는 함수 : deque 자체는 그대로 두고, 뒤집혔다는 뜻으로 isReverse라는 boolean 값만 변경 */
	public void functionR() {
		isReverse = !isReverse;
	}

	/**
	 * 버리는 함수 : 뒤집혀 있으면 뒤에서, 뒤집혀 있지 않으면 앞에서 제거
	 * 
	 * @throws DequeEmptyException
	 */
	public void functionD() throws DequeEmptyException {
		if (deque.isEmpty()) {
			throw new DequeEmptyException();
		}

		if (isReverse) {
			deque.removeLast();
		} else {
			deque.removeFirst();
		}
	}

	/** deque를 String으로 리턴하는 함수 : 뒤집혀 있지 않으면 그대로, 뒤집혀 있다면 뒤집어서 리턴 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// deque를 array로 변환
		int size = deque.size();
		Integer[] arr = deque.toArray(new Integer[size]);

		// 대괄호 열기
		sb.append("[");

		// 뒤집혀 있으면 역순으로, 뒤집혀 있지 않으면 원래 순서대로 sb에 append
		if (isReverse) {
			for (int i = size - 1; i >= 0; i--) {
				sb.append(arr[i]).append(",");
			}
		} else {
			for (int i = 0; i < size; i++) {
				sb.append(arr[i]).append(",");
			}
		}

		// 마지막 콤마 제거
		if(sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		// 대괄호 닫기
		sb.append("]");

		return sb.toString();
	}
}

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 테스트 케이스 개수 입력
		int T = Integer.parseInt(br.readLine());

		testLoop: for (int test_case = 0; test_case < T; test_case++) {
			// sb 초기화
			sb.setLength(0);

			// 함수 입력
			String functions = br.readLine();

			// deque의 길이 입력
			int dequeLength = Integer.parseInt(br.readLine());

			// deque 입력
			Deque<Integer> deque = new LinkedList<>();
			StringTokenizer st = new StringTokenizer(br.readLine(), ",|[|]");
			for (int i = 0; i < dequeLength; i++) {
				deque.addLast(Integer.parseInt(st.nextToken()));
			}

			// 입력받은 deque를 이용하여 MyDeque 클래스의 객체 생성
			MyDeque myDeque = new MyDeque(deque);

			// 함수 수행
			for (int i = 0; i < functions.length(); i++) {
				char command = functions.charAt(i);
				switch (command) {
				// R 함수 수행
				case 'R':
					myDeque.functionR();
					break;
				// D 함수 수행, 실패 시 error 출력 후 다음 테스트 케이스로 진행
				case 'D':
					try {
						myDeque.functionD();
					} catch (DequeEmptyException e) {
						System.out.println("error");
						continue testLoop;
					}
					break;
				default:
				}
			} // end for(i)

			// 수행 완료 시 myDeque의 상태 출력
			System.out.println(myDeque.toString());
		} // end test_case
	} // end main
}
