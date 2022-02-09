// 87672KB, 1056ms

package baek4358;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 나무 이름 -> 그 나무의 개수로의 맵핑
		Map<String, Integer> nameMap = new TreeMap<>();
		
		// 모든 나무의 개수 총합
		int countAll = 0;
		
		// 입력을 받아서 각 나무의 개수 업데이트
		String line = "";
		while ((line = br.readLine()) != null) {
			if (nameMap.get(line) == null) {
				nameMap.put(line, 1);
			}
			else {
				nameMap.put(line, nameMap.get(line) + 1);
			}
			countAll++;
		}
		
		// 각 나무의 개수를 가져와서 평균 계산 후 출력에 추가
		StringBuilder sb = new StringBuilder();
		for (String name : nameMap.keySet()) {
			int count = nameMap.get(name);
			double percentage = (double)count / countAll * 100.0;
			sb.append(name).append(" ").append(String.format("%.4f", percentage)).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());
	}
}
