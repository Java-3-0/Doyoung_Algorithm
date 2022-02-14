// 11832KB, 84ms

package bj9375;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 입력
		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			int N = Integer.parseInt(br.readLine());
			// 의상의 종류 -> 그 종류의 의상 개수로의 매핑 생성
			Map<String, Integer> countType = new HashMap<>();
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				String clothingName = st.nextToken();
				String clothingType = st.nextToken();
				if (countType.containsKey(clothingType)) {
					countType.put(clothingType, countType.get(clothingType) + 1);
				} else {
					countType.put(clothingType, 1);
				}
			} // end for (i)
			
			// 가능한 의상 조합 가지수 계산
			int answer = 1;
			for (int count : countType.values()) {
				answer *= (count + 1);
			}
			answer -= 1; // 알몸인 상태 제외
			
			sb.append(answer).append("\n");
		} // end for (testCase)
		
		System.out.print(sb.toString());
		br.close();
	}

}
