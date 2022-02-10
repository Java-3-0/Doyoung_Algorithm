// 32192KB, 480ms

package beak10546;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		Map<String, Integer> map = new TreeMap<>();
		// 이름을 입력받아, 그 이름이 명단에 등장하는 횟수를 증가시킴
		for (int i = 0; i < N; i++) {
			String name = br.readLine();
			if (map.containsKey(name)) {
				map.put(name, map.get(name) + 1);
			}
			else {
				map.put(name, 1);
			}
		}
		
		// 이름을 입력받아, 그 이름이 명단에 등장하는 횟수를 감소시킴
		for (int i = 0; i < N-1; i++) {
			String name = br.readLine();
			map.put(name, map.get(name) - 1);
		}
		
		// 완주자를 다 명단에서 제거한 이후에 아직 명단에 등장 횟수가 1인 사람이 미완주자
		for (String s : map.keySet()) {
			if (map.get(s) == 1) {
				System.out.println(s);
				return;
			}
		}
	}
}
