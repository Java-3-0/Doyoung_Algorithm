// 14332KB, 124ms

package baek1302;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		// 책마다 팔린 횟수 저장
		Map<String, Integer> map = new TreeMap<>();
		for (int i = 0; i < N; i++) {
			String bookName = br.readLine();
			if (map.containsKey(bookName)) {
				map.put(bookName, map.get(bookName) + 1);
			} else {
				map.put(bookName, 1);
			}
		}

		// 가장 많이 팔린 책 계산
		int maxCount = 0;
		String answer = "";
		for (String bookName : map.keySet()) {
			int count = map.get(bookName);
			if (maxCount < count) {
				answer = bookName;
				maxCount = map.get(bookName);
			}
		}

		// 출력
		System.out.println(answer);
	}
}
