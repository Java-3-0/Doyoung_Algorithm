package bj15270;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 20;

	static int V, E;
	static List<Integer>[] adjList;
	static int[] combi = new int[MAX_V + 1];
	static boolean[] isUsed = new boolean[MAX_V + 1];
	static int maxCnt;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// V, E 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 인접 리스트 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 간선 입력받아서 adjList에 저장
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int f1 = Integer.parseInt(st.nextToken());
			int f2 = Integer.parseInt(st.nextToken());
			adjList[f1].add(f2);
			adjList[f2].add(f1);
		}

		// 모든 조합을 완전탐색하면서, 짝을 지어줄 수 있는 최대 인원 수 갱신
		combination(1, 0);

		// 아직 사람이 남았다면, 한 명은 단독으로 로봇 댄스 가능
		if (maxCnt < V) {
			maxCnt++;
		}

		// 출력
		System.out.println(maxCnt);
	}

	/** 모든 조합을 완전탐색하면서, 짝을 지어줄 수 있는 최대 인원 수 갱신 */
	public static void combination(int startNum, int cnt) {
		if (startNum == V + 1) {
			maxCnt = maxCnt < cnt ? cnt : maxCnt;
			return;
		}

		if (isUsed[startNum]) {
			return;
		}

		isUsed[startNum] = true;

		for (int pairNum : adjList[startNum]) {
			if (isUsed[pairNum]) {
				continue;
			}

			isUsed[pairNum] = true;
			combination(startNum + 1, cnt + 2);
			isUsed[pairNum] = false;
		}

		isUsed[startNum] = false;
		combination(startNum + 1, cnt);
	}

}