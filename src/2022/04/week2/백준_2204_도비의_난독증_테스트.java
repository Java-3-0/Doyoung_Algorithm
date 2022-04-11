// 11904KB, 88ms

package bj2204;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static class MyString implements Comparable<MyString> {
		String original;
		String lower;

		public MyString(String original, String lower) {
			super();
			this.original = original;
			this.lower = lower;
		}

		@Override
		public int compareTo(MyString s) {
			return this.lower.compareTo(s.lower);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		while (true) {
			int N = Integer.parseInt(br.readLine());
			if (N == 0) {
				break;
			}

			MyString[] words = new MyString[N];

			for (int i = 0; i < N; i++) {
				String input = br.readLine();
				words[i] = new MyString(input, input.toLowerCase());
			}

			Arrays.sort(words);

			sb.append(words[0].original).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}