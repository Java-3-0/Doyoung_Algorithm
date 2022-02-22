// 11600KB, 88ms

package tmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		Map<Integer, Integer> cows = new HashMap<>();

		int count = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int cowNum = Integer.parseInt(st.nextToken());
			int roadSide = Integer.parseInt(st.nextToken());

			// 처음 보는 소인 경우 현재 위치를 맵에 저장
			if (!cows.containsKey(cowNum)) {
				cows.put(cowNum, roadSide);
			}

			// 이미 본 적이 있는 소인 경우
			else {
				// 이전과 같은 곳에 있다면 그냥 둔다
				if (cows.get(cowNum) == roadSide) {
					continue;
				} 
				// 이전과 다른 곳에 있다면, 위치를 갱신하고, 수를 하나 센다.
				else {
					count++;
					cows.put(cowNum, roadSide);
				}
			}
		}

		System.out.println(count);

	} // end main
}
