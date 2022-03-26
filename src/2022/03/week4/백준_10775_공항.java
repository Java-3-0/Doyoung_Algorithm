// 21612KB, 220ms

package bj10775;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static class DisjointSet {
		int[] parent;

		/** 크기를 입력받아서 상호 배타적 집합을 만들고, 모든 원소의 루트를 자기 자신으로 초기화 */
		public DisjointSet(int size) {
			super();
			this.parent = new int[size + 1];
			for (int i = 0; i < parent.length; i++) {
				parent[i] = i;
			}
		}

		public int find(int a) {
			int pa = parent[a];

			if (pa == a) {
				return a;
			}

			return parent[a] = find(pa);
		}

		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			if (aRoot == bRoot) {
				return false;
			}

			parent[bRoot] = aRoot;
			return true;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int G = Integer.parseInt(br.readLine());
		int P = Integer.parseInt(br.readLine());

		DisjointSet ds = new DisjointSet(G);
		
		int cnt = 0;
		for (int i = 0; i < P; i++) {
			int Gi = Integer.parseInt(br.readLine());
			
			int place = ds.find(Gi);
			if (place == 0) {
				break;
			}
			
			ds.union(place - 1, place);
			cnt++;
		}
		
		System.out.println(cnt);

	} // end main

}