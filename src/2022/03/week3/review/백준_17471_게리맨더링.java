// 14324KB, 104ms

package bj17471;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 10;
	static final int INF = 987654321;
	static final int FAIL = -1;

	static int V;
	static int[] populations;
	static List<Integer>[] adjList;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 구역 개수 N 입력
		V = Integer.parseInt(br.readLine());

		// 메모리 할당
		populations = new int[V];
		adjList = new ArrayList[V];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 각 구역의 인구 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int v = 0; v < V; v++) {
			populations[v] = Integer.parseInt(st.nextToken());
		}

		// 인접 리스트 입력
		for (int from = 0; from < V; from++) {
			st = new StringTokenizer(br.readLine(), " ");
			int numEdges = Integer.parseInt(st.nextToken());
			for (int e = 0; e < numEdges; e++) {
				int to = Integer.parseInt(st.nextToken()) - 1;
				adjList[from].add(to);
			}
		}

		// 두 선거구의 인구 차이 최솟값 계산
		int answer = solve();
		
		// 출력
		System.out.println(answer);
	}

	/** 선거구를 나누는 모든 방법을 완전탐색하고 두 선거구의 인구 차이의 최소값을 리턴 */
	public static int solve() {
		int minDiff = INF;

		// bitMask : 첫 지역구에 포함된 도시들
		for (int bitMask = 1; bitMask < (1 << V) - 1; bitMask++) {
			// bitMaskRev : 두 번째 지역구에 포함된 도시들
			int bitMaskRev = (~bitMask) & ((1 << V) - 1);

			// 각각의 지역구의 인구수를 계산
			int population1 = countPopulation(bitMask);
			int population2 = countPopulation(bitMaskRev);

			// 한 쪽 지역구라도 서로 연결되어 있지 않은 경우 실패
			if (population1 == FAIL || population2 == FAIL) {
				continue;
			}

			// 인구수 차이 계산
			int diff = Math.abs(population2 - population1);
			
			// 최소 차이 갱신
			minDiff = diff < minDiff ? diff : minDiff;
		}

		// minDiff가 INF라면 가능한 경우가 없었던 것이므로 FAIL을 리턴하고, 아니면 minDiff를 리턴한다
		return minDiff == INF ? FAIL : minDiff;
	}

	/** 비트마스크에 포함된 도시들이 인접해 있다면 그 인구의 합을 리턴, 인접해 있지 않다면 -1을 리턴 */
	public static int countPopulation(int bitMask) {
		int totalPopulation = 0;
		// 방문해야 할 정점 개수
		int needToVisit = Integer.bitCount(bitMask);
		// 시작 정점
		int startIdx = rightmostBitIdx(bitMask);
		// 방문 정점 개수
		int visitCnt = 0;

		// bfs를 위한 큐 설정, isVisited 배열 설정
		Queue<Integer> q = new LinkedList<Integer>();
		boolean[] isVisited = new boolean[V + 1];

		// 시작 정점 처리
		q.offer(startIdx);
		isVisited[startIdx] = true;

		// bfs 수행
		while (!q.isEmpty()) {
			int now = q.poll();
			totalPopulation += populations[now];
			visitCnt++;

			// 아직 방문하지 않은 정점들 중, 간선으로 연결되어 있고 같은 지역구에 있는 정점을 큐에 넣는다.
			for (int next : adjList[now]) {
				if (!isVisited[next] && (bitMask & (1 << next)) != 0) {
					isVisited[next] = true;
					q.offer(next);
				}
			}
		}

		// 같은 지역구에 있는 정점들을 모두 방문하지 못하고 bfs가 끝난 경우 FAIL을 리턴
		if (visitCnt < needToVisit) {
			return FAIL;
		}

		return totalPopulation;
	}

	/** 가장 오른쪽의 1인 비트의 인덱스를 리턴 */
	public static int rightmostBitIdx(int bitMask) {
		for (int i = 0; i < V; i++) {
			if ((bitMask & (1 << i)) != 0) {
				return i;
			}
		}

		return FAIL;
	}
}
