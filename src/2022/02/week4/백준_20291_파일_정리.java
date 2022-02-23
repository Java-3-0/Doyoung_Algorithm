// 80080KB, 600ms

package bj20291;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());

		Map<String, Integer> map = new TreeMap<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), ".");
			String garbage = st.nextToken();
			String extension = st.nextToken();

			if (map.containsKey(extension)) {
				map.put(extension, map.get(extension) + 1);
			}
			else {
				map.put(extension, 1);
			}
		}
		
		for (String key : map.keySet()) {
			int value = map.get(key);
			sb.append(key).append(" ").append(value).append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
}