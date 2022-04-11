package bj11478;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String S = br.readLine();
		Set<String> set = new HashSet<>();

		int len = S.length();
		for (int i = 0; i < len; i++) {
			for (int j = i; j < len; j++) {
				String sub = S.substring(i, j + 1);
				set.add(sub);
			}
		}
		
		int answer = set.size();
		System.out.println(answer);

	} // end main

}