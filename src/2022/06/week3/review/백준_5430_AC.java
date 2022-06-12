// 89284KB, 620ms

package bj5430;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

	/** Deque가 비어 있는데 원소를 제거하려 할 경우 던질 예외 */
	static class EmptyDequeException extends Exception {
		private static final long serialVersionUID = -5245336989296033052L;
	}

	/** D함수와 R함수를 처리하는 연산을 구현한 덱 클래스 */
	static class MyDeque {
		private Deque<Integer> deque;
		private boolean isReversed;

		public MyDeque(Deque<Integer> deque) {
			super();
			this.deque = deque;
			this.isReversed = false;
		}

		/** 뒤집기 */
		public void functionR() {
			isReversed = !isReversed;
		}

		/** 버리기 */
		public void functionD() throws EmptyDequeException {
			// 이미 비어 있는데 제거하려 하면 예외를 던진다
			if (deque.isEmpty()) {
				throw new EmptyDequeException();
			}

			// 뒤집혀 있으면 뒤에서, 뒤집혀 있지 않으면 앞에서 제거
			if (isReversed) {
				deque.pollLast();
			} else {
				deque.pollFirst();
			}
		}

		/** deque를 String으로 리턴 */
		@Override
		public String toString() {
			StringBuilder sbTmp = new StringBuilder();
			// deque를 array로 변환
			int size = deque.size();
			Integer[] arr = deque.toArray(new Integer[size]);

			// 대괄호 열기
			sbTmp.append("[");

			// 뒤집혀 있으면 역순으로, 뒤집혀 있지 않으면 원래 순서대로 sb에 append
			if (isReversed) {
				for (int i = size - 1; i >= 0; i--) {
					sbTmp.append(arr[i]).append(",");
				}
			} else {
				for (int i = 0; i < size; i++) {
					sbTmp.append(arr[i]).append(",");
				}
			}

			// 마지막 콤마 제거
			if (sbTmp.charAt(sbTmp.length() - 1) == ',') {
				sbTmp.setLength(sbTmp.length() - 1);
			}

			// 대괄호 닫기
			sbTmp.append("]");

			return sbTmp.toString();
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 테스트 케이스 개수 입력
		final int TESTS = Integer.parseInt(br.readLine());

		// 테스트 케이스 수만큼 반복
		testLoop: for (int tc = 0; tc < TESTS; tc++) {
			// 함수 입력
			char[] commands = br.readLine().toCharArray();

			// deque의 길이 입력
			int dequeLen = Integer.parseInt(br.readLine());

			// deque 입력
			Deque<Integer> deque = new ArrayDeque<>();
			StringTokenizer st = new StringTokenizer(br.readLine(), ",[]");
			for (int i = 0; i < dequeLen; i++) {
				deque.addLast(Integer.parseInt(st.nextToken()));
			}

			// 입력받은 deque를 이용하여 MyDeque 클래스의 객체 생성
			MyDeque myDeque = new MyDeque(deque);

			// 함수 수행
			for (char command : commands) {
				switch (command) {
				// R 함수 수행
				case 'R':
					myDeque.functionR();
					break;
				// D 함수 수행하고, 실패 시 error
				case 'D':
					try {
						myDeque.functionD();
					} catch (EmptyDequeException e) {
						sb.append("error").append("\n");
						continue testLoop;
					}
					break;
				default:
					break;
				}
			} // end for(i)

			// 수행 완료 시 myDeque를 출력 스트링빌더에 추가
			sb.append(myDeque.toString()).append("\n");

		} // end for(tc)

		System.out.print(sb.toString());

	} // end main
}