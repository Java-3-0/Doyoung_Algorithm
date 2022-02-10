// 85856KB, 860ms

package baek1269;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int lenA = Integer.parseInt(st.nextToken());
		int lenB = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");

		Set<Integer> setA = new HashSet<>();
		Set<Integer> setB = new HashSet<>();
		for (int i = 0; i < lenA; i++) {
			setA.add(Integer.parseInt(st.nextToken()));
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < lenB; i++) {
			setB.add(Integer.parseInt(st.nextToken()));
		}

		// 대칭 차집합의 원소 개수 계산
		int answer = countDifference(setA, setB);

		// 출력
		System.out.println(answer);
	}

	/** 대칭 차집합의 원소 수를 리턴 */
	public static int countDifference(Set<Integer> setA, Set<Integer> setB) {
		int ret = 0;

		// B쪽에 없는 A의 원소 수를 셈
		for (int num : setA) {
			if (!setB.contains(num)) {
				ret++;
			}
		}
		// A쪽에 없는 B의 원소 수를 셈
		for (int num : setB) {
			if (!setA.contains(num)) {
				ret++;
			}
		}

		return ret;

	}
}
