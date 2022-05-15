// 24064KB, 248ms

package bj25192;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		Set<String> set = new HashSet<>();

		int answer = 0;
		for (int i = 0; i < N; i++) {
			String text = br.readLine();

			if (text.equals("ENTER")) {
				set.clear();
			} else {
				if (!set.contains(text)) {
					set.add(text);
					answer++;
				}
			}
		}

		System.out.println(answer);

	} // end main
}