// 16296KB, 148ms

package bj16960;

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

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		List<Integer>[] switches = new ArrayList[N];
		for (int i = 0; i < switches.length;i++) {
			switches[i] = new ArrayList<Integer>();
		}
		
		int[] counts = new int[M + 1];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int connections = Integer.parseInt(st.nextToken());
			for (int c = 0; c < connections; c++) {
				int lamp = Integer.parseInt(st.nextToken());
				switches[i].add(lamp);
				counts[lamp]++;
			}
		}
		
		boolean isPossible = false;
		OUTER: for (List<Integer> list : switches) {
			for (int lamp : list) {
				if (counts[lamp] <= 1) {
					continue OUTER;
				}
			}
			
			isPossible = true;
		}
		
		if (isPossible) {
			System.out.println(1);
		}
		else {
			System.out.println(0);
		}
		
		

	} // end main

	
	
}