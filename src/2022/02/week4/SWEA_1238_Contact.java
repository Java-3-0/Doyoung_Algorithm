// 21080KB, 116ms

// bfs로 탐색해서 마지막에 탐색하게 되는 노드들을 찾는 문제

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	// 상수 선언
	/** 최대 인원 수 */
	public static final int MAX_NUM = 100;

	// 전역변수 선언
	/** 마지막을 방문한 정점들을 기록할 변수 */
	public static List<Integer> leafNodes = new ArrayList<Integer>();
	/** 인접 행렬 */
	public static boolean[][] adjMatrix = new boolean[MAX_NUM + 1][MAX_NUM + 1];
	/** 각 정점 방문 여부를 나타낼 배열 */
	public static boolean[] isVisited = new boolean[MAX_NUM + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = 10;

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 일단 메모리부터 초기화
			initMemory();

			// 정점 개수, 시작 정점 번호 입력
			st = new StringTokenizer(br.readLine(), " ");
			int inputLen = Integer.parseInt(st.nextToken());
			int startNode = Integer.parseInt(st.nextToken());

			// 그래프 간선 정보 입력받아서 인접 행렬에 저장
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < inputLen / 2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				adjMatrix[from][to] = true;
			}

			// 시작 정점부터 bfs 수행하면서 마지막으로 가게 되는 노드들을 찾음
			bfs(startNode);

			// 마지막으로 가게 되는 노드들 중 번호가 가장 큰 것을 찾음
			int maxLeaf = 0;
			for (int leaf : leafNodes) {
				if (maxLeaf < leaf) {
					maxLeaf = leaf;
				}
			}

			// 형식에 맞게 출력 스트링빌더에 추가
			sb.append("#").append(testCase).append(" ").append(maxLeaf).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	}

	/** 전역변수 메모리 초기화시키는 함수 */
	public static void initMemory() {
		leafNodes.clear();

		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], false);
		}

		Arrays.fill(isVisited, false);
	}

	/** bfs를 수행하며, 새로운 정점을 방문할 때마다 leafNodes를 갱신하는 함수 */
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<Integer>();

		q.offer(start);
		isVisited[start] = true;

		while (!q.isEmpty()) {
			leafNodes.clear();
			
			// leafNodes들을 알아내야 하기 때문에, 반복문 하나로 큐 비울 때까지 계속 돌지 말고, bfs하는 하나의 depth마다 내부적으로 하나의 반복문을 더 돌린다.
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int here = q.poll();
				leafNodes.add(here);

				for (int there = 1; there <= MAX_NUM; there++) {
					if (adjMatrix[here][there] && !isVisited[there]) {
						q.offer(there);
						isVisited[there] = true;
					}
				}
			}
		}
	}
}
