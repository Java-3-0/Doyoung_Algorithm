// 11588KB, 76ms

package bj1543;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		char[] text = br.readLine().toCharArray();
		char[] pattern = br.readLine().toCharArray();
		
		List<Integer> found = kmpSearch(text, pattern);

		int answer = found.size();
		
		System.out.println(answer);
	}

	public static int[] getPrefixTable(char[] arr) {
		int len = arr.length;
		int[] p = new int[len];
		p[0] = 0;
		
		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && arr[i] != arr[j]) {
				j = p[j - 1];
			}
			if (arr[i] == arr[j]) {
				p[i] = ++j;
			}
			else {
				p[i] = 0;
			}
		}
		
		return p;
	}
	
	public static List<Integer> kmpSearch(char[] text, char[] pattern) {
		int[] p = getPrefixTable(pattern);
		List<Integer> ret = new ArrayList<>();
		
		int lenT = text.length;
		int lenP = pattern.length;
		
		for (int i = 0, j = 0; i < lenT; i++) {
			while (j > 0 && text[i] != pattern[j]) {
				j = p[j - 1];
			}
			
			if (text[i] == pattern[j]) {
				if (j == lenP - 1) {
					ret.add(i - j + 1);
					// 겹치지 않게 찾아야 하므로 j를 0으로 세팅
					j = 0;
				}
				else {
					j++;
				}
			}
		}
		
		return ret;
	}
}