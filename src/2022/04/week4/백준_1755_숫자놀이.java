package bj1755;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final String[] digitNames = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine" };

	static class MyNumber implements Comparable<MyNumber> {
		/** 수 */
		int num;
		/** 수를 영어로 숫자 한 자리씩 읽었을 때의 문자열 */
		String numStr;

		/** 생성자 */
		public MyNumber(int num) {
			super();
			this.num = num;
			this.numStr = convertNumToString(num);
		}

		/** 수를 영어 문자열 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(MyNumber mn) {
			return this.numStr.compareTo(mn.numStr);
		}

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// M, N 입력
		st = new StringTokenizer(br.readLine(), " ");
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		// M 이상 N 이하의 수를 pq에 담는다
		PriorityQueue<MyNumber> pq = new PriorityQueue<>();
		for (int num = M; num <= N; num++) {
			pq.offer(new MyNumber(num));
		}

		// pq에서 하나씩 꺼내오면서 출력용 스트링빌더에 추가
		int cnt = 0;
		while (!pq.isEmpty()) {
			sb.append(pq.poll().num);
			cnt++;

			// 10개 출력마다 줄바꿈
			if (!pq.isEmpty()) {
				if (cnt % 10 == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	}

	/** 수를 영어로 읽었을 때의 문자열을 구해서 리턴 */
	public static String convertNumToString(int num) {
		StringBuilder sb = new StringBuilder();

		// 첫 자리 숫자 계산
		int first = num / 10;

		// 둘째 자리 숫자 계산
		int second = num % 10;

		// 첫째 자리 숫자가 0이 아니면 스트링빌더에 추가
		if (first != 0) {
			sb.append(digitNames[first]).append(' ');
		}

		// 둘째 자리 숫자를 스트링빌더에 추가
		sb.append(digitNames[second]);

		// 결과 리턴
		return sb.toString();
	}

}