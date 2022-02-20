// 29884KB, 368ms

package bj14247;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 한 번 벤 나무를 또 벨 거였으면, 그 나무를 애초에 안 베고 뒀다가 나중에 한 번만 베는 것이 나음.
 * 따라서 모든 나무를 1번씩 베긴 하는 것이 최적
 * 자라는 속도가 느린 것부터 먼저 벤다 
 */

public class Main {
	static class Tree implements Comparable<Tree> {
		/** 트리의 초기 높이 */
		int height;
		/** 트리가 하루에 자라는 높이 */
		int growth;

		/** 초기 높이와 하루에 자라는 높이를 받아서 나무 객체 를 생성하는 생성자 */
		public Tree(int height, int growth) {
			super();
			this.height = height;
			this.growth = growth;
		}

		/** 자라는 높이 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Tree t) {
			return this.growth - t.growth;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		int N = Integer.parseInt(br.readLine());
		int[] heights = new int[N];
		int[] growths = new int[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			heights[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			growths[i] = Integer.parseInt(st.nextToken());
		}

		// 입력 받은 내용으로 트리 객체의 배열을 생성
		Tree[] trees = new Tree[N];
		for (int i = 0; i < N; i++) {
			trees[i] = new Tree(heights[i], growths[i]);
		}
		
		// 자라는 속도 오름차순으로
		Arrays.sort(trees);
		
		long sum = 0L;
		for (int day = 0; day < N; day++) {
			Tree treeToCut = trees[day];
			sum += (long) treeToCut.height + (long) treeToCut.growth * (long) day;
		}
		
		System.out.println(sum);
	}
}