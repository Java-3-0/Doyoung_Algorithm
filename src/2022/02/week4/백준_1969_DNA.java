// 12532KB, 92ms

package bj1969;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		String[] dna = new String[N];
		for (int i = 0; i < N; i++) {
			dna[i] = br.readLine();
		}

		int sumDist = 0;
		for (int characterIdx = 0; characterIdx < M; characterIdx++) {
			int[] count = new int[4];
			for (String s : dna) {
				char c = s.charAt(characterIdx);
				count[charToInt(c)]++;
			}
			
			int sum = 0;
			int max = 0;
			int maxIdx = 0;
			for (int idx = 3; idx >= 0; idx--) {
				sum += count[idx];
				if (max <= count[idx]) {
					max = count[idx];
					maxIdx = idx;
				}
			}
			
			sb.append(intToChar(maxIdx));
			sumDist += (sum - max);
		}

		System.out.println(sb.toString());
		System.out.println(sumDist);
		
	}

	public static int charToInt(char c) {
		switch (c) {
		case 'A':
			return 0;
		case 'C':
			return 1;
		case 'G':
			return 2;
		case 'T':
			return 3;
		default:
			return -1;
		}
	}

	public static char intToChar(int i) {
		switch (i) {
		case 0:
			return 'A';
		case 1:
			return 'C';
		case 2:
			return 'G';
		case 3:
			return 'T';
		default:
			return '0';
		}
	}
}