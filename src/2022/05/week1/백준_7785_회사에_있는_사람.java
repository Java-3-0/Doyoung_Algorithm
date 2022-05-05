// 48700KB, 528ms

package bj7785;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());
		
		TreeSet<String> set = new TreeSet<>(Collections.reverseOrder());

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			String name = st.nextToken();
			String logType = st.nextToken();
			
			if (logType.equals("enter")) {
				set.add(name);
			}
			else {
				set.remove(name);
			}
		}
		
		while (!set.isEmpty()) {
			sb.append(set.pollFirst()).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}