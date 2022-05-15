// 11916KB, 88ms

package bj25185;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	static final String REST = ":)";
	static final String WORK = ":(";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 카드 입력
			String[] cards = new String[4];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < 4; i++) {
				cards[i] = st.nextToken();
			}

			// 카드 정렬
			Arrays.sort(cards);

			// 휴식 여부 파악해서 출력 스트링빌더에 추가
			if (isRestDay(cards)) {
				sb.append(REST);
			} else {
				sb.append(WORK);
			}
			sb.append("\n");

		}

		System.out.print(sb.toString());

	} // end main

	public static boolean isRestDay(String[] cards) {
		// 조건 만족 여부 파악해서 리턴
		if (satisfiesCondition1(cards)) {
			return true;
		}
		if (satisfiesCondition2(cards)) {
			return true;
		}
		if (satisfiesCondition3(cards)) {
			return true;
		}

		return false;
	}

	/** 같은 알파벳에 스트레이트 3개인 경우가 존재하는지 여부를 리턴 */
	public static boolean satisfiesCondition1(String[] cards) {
		Set<Integer> setM = new TreeSet<>();
		Set<Integer> setP = new TreeSet<>();
		Set<Integer> setS = new TreeSet<>();

		for (String card : cards) {
			int num = card.charAt(0) - '0';
			char alphabet = card.charAt(1);

			if (alphabet == 'm') {
				setM.add(num);
			} else if (alphabet == 'p') {
				setP.add(num);
			} else if (alphabet == 's') {
				setS.add(num);
			}
		}

		if (hasThreeConsecutives(setM)) {
			return true;
		}

		if (hasThreeConsecutives(setP)) {
			return true;
		}

		if (hasThreeConsecutives(setS)) {
			return true;
		}

		return false;
	}

	public static boolean hasThreeConsecutives(Set<Integer> set) {
		for (int num : set) {
			if (set.contains(num - 1) && set.contains(num + 1)) {
				return true;
			}
		}

		return false;
	}

	/** 알파벳과 숫자가 모두 같은 세 장의 존재 여부를 리턴 */
	public static boolean satisfiesCondition2(String[] cards) {
		if (cards[0].equals(cards[1]) && cards[1].equals(cards[2])) {
			return true;
		}

		if (cards[1].equals(cards[2]) && cards[2].equals(cards[3])) {
			return true;
		}

		return false;
	}

	/** 두 개씩 짝지어서 짝끼리 같은 카드인지 여부를 리턴 */
	public static boolean satisfiesCondition3(String[] cards) {
		if (cards[0].equals(cards[1]) && cards[2].equals(cards[3])) {
			return true;
		}

		return false;
	}
}