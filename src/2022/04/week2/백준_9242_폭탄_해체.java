// 11560KB, 76ms

package bj9242;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static final String ZERO = "**** ** ** ****";
	static final String ONE = "  *  *  *  *  *";
	static final String TWO = "***  *****  ***";
	static final String THREE = "***  ****  ****";
	static final String FOUR = "* ** ****  *  *";
	static final String FIVE = "****  ***  ****";
	static final String SIX = "****  **** ****";
	static final String SEVEN = "***  *  *  *  *";
	static final String EIGHT = "**** ***** ****";
	static final String NINE = "**** ****  ****";

	static final int FAIL = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		char[][] grid = new char[5][4 * 8];

		int wordLen = 0;
		for (int y = 0; y < 5; y++) {
			String line = br.readLine();
			wordLen = (line.length() + 1) / 4;
			for (int x = 0; x < line.length(); x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		List<String> list = new ArrayList<>();

		
		for (int wordIdx = 0; wordIdx < wordLen; wordIdx++) {
			StringBuilder tmp = new StringBuilder();
			for (int y = 0; y < 5; y++) {
				for (int dx = 0; dx < 3; dx++) {
					int x = 4 * wordIdx + dx;
					tmp.append(grid[y][x]);
				}
			}
			
			list.add(tmp.toString());
		}

		int num = getNumber(list);
		
		if (num == FAIL) {
			System.out.println("BOOM!!");
		}
		else {
			if (num % 6 == 0) {
				System.out.println("BEER!!");
			}
			else {
				System.out.println("BOOM!!");
			}
		}

	} // end main

	public static int getNumber(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			if (s.equals(ZERO)) {
				sb.append(0);
			} else if (s.equals(ONE)) {
				sb.append(1);
			} else if (s.equals(TWO)) {
				sb.append(2);
			} else if (s.equals(THREE)) {
				sb.append(3);
			} else if (s.equals(FOUR)) {
				sb.append(4);
			} else if (s.equals(FIVE)) {
				sb.append(5);
			} else if (s.equals(SIX)) {
				sb.append(6);
			} else if (s.equals(SEVEN)) {
				sb.append(7);
			} else if (s.equals(EIGHT)) {
				sb.append(8);
			} else if (s.equals(NINE)) {
				sb.append(9);
			} else {
				return FAIL;
			}
		}

		int ret = Integer.parseInt(sb.toString());

		return ret;

	}

}