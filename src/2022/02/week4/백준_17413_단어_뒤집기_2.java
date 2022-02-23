// 15580KB, 136ms

package bj17413;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String input = br.readLine();
		int len = input.length();

		StringBuilder sbResult = new StringBuilder();
		StringBuilder sbTmp = new StringBuilder();
		
		boolean tag = false;
		for (int i = 0; i < len; i++) {
			char c = input.charAt(i);

			switch (c) {
			case ' ':
				if (!tag) {
					sbResult.append(sbTmp.reverse().toString());
					sbResult.append(c);
					sbTmp.setLength(0);
				}
				else {
					sbTmp.append(c);
				}
				
				break;
			case '<':
				sbResult.append(sbTmp.reverse().toString());
				sbResult.append(c);
				sbTmp.setLength(0);
				
				tag = true;
				break;
			case '>':
				if (tag) {
					sbResult.append(sbTmp.toString());
					sbResult.append(c);
					sbTmp.setLength(0);
				}
				
				tag = false;

				break;
			default:
				sbTmp.append(c);
			}

		}

		// 마지막에 남은 것 추가
		sbResult.append(sbTmp.reverse().toString());
		
		String answer = sbResult.toString();
		
		System.out.println(answer);
		
	} // end main
}