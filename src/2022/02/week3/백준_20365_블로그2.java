package bj20365;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		String s = br.readLine();

		// 한 색으로 전부 칠한 후, 다른 색을 서로 연결된 부분마다 한번에 칠한다
		char firstColor = s.charAt(0);
		char prev = 0;
		int count = 1;
		for (char c : s.toCharArray()) {
			if (firstColor != c) {
				if (prev != c) {
					count++;
				}
			}
			
			prev = c;
		}
		
		System.out.println(count);
	}
}