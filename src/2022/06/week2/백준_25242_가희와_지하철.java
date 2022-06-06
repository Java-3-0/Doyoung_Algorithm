// 257316KB, 2328ms

package bj25242;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200000;
	static final int MAX_N = 100000, MAX_Q = 100000;
	static final int FAIL = -1;
	static final int INF = 987654321;

	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static Map<String, Integer> stationNameToNum = new HashMap<>();
	static Map<Integer, Integer> stationNumToPosInLine[] = new HashMap[MAX_N + 1];
	static Set<Integer>[] linesOfStation = new HashSet[MAX_V + 1];

	static Set<Integer> tranferStations = new HashSet<>();
	static int[][] distancesFromTransfer;

	static boolean[] isVisited = new boolean[MAX_V + 1];
	static Queue<Integer> q = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// linesOfStation 메모리 할당
		for (int i = 0; i < linesOfStation.length; i++) {
			linesOfStation[i] = new HashSet<Integer>();
		}

		// stationNumToPosInLine 메모리 할당
		for (int i = 0; i < stationNumToPosInLine.length; i++) {
			stationNumToPosInLine[i] = new HashMap<Integer, Integer>();
		}

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		// 지하철 노선 정보 입력
		int stationCnt = 0;
		for (int lineIdx = 0; lineIdx < N; lineIdx++) {
			st = new StringTokenizer(br.readLine(), " ");
			int len = Integer.parseInt(st.nextToken());

			// 역 이름에 대한 번호 구하고
			int[] seq = new int[len];
			for (int pos = 0; pos < len; pos++) {
				String stationName = st.nextToken();
				if (!stationNameToNum.containsKey(stationName)) {
					stationNameToNum.put(stationName, stationCnt++);
				} else {
					tranferStations.add(stationNameToNum.get(stationName));
				}

				int stationNum = stationNameToNum.get(stationName);
				seq[pos] = stationNum;
				linesOfStation[stationNum].add(lineIdx);
				stationNumToPosInLine[lineIdx].put(stationNum, pos);
				
			} // end for pos

			// 간선 연결
			for (int pos = 1; pos < len; pos++) {
				int prev = seq[pos - 1];
				int now = seq[pos];

				// 양방향으로 처리
				adjList[prev].add(now);
				adjList[now].add(prev);
			}
			
		} // end for lineIdx

		// 각 환승역으로부터의 모든 역까지의 거리를 bfs로 미리 계산
		distancesFromTransfer = new int[tranferStations.size()][stationCnt + 1];
		for (int i = 0; i < distancesFromTransfer.length; i++) {
			Arrays.fill(distancesFromTransfer[i], INF);
		}
		
		int transferIdx = 0;
		for (int start : tranferStations) {
			bfs(start, transferIdx);
			transferIdx++;
		}

		// 쿼리 수행
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine(), " ");
			String startStationName = st.nextToken();
			String endStationName = st.nextToken();

			int start = stationNameToNum.get(startStationName);
			int end = stationNameToNum.get(endStationName);

			int minDist = INF;

			// 같은 노선에 있는 경우, 직접 가는 방법 처리
			for (int lineNum : linesOfStation[start]) {
				if (linesOfStation[end].contains(lineNum)) {
					int pos1 = stationNumToPosInLine[lineNum].get(start);
					int pos2 = stationNumToPosInLine[lineNum].get(end);
					int dist = 2 * Math.abs(pos2 - pos1);
					minDist = Math.min(minDist, dist);
				}
			}

			for (int i = 0; i < tranferStations.size(); i++) {
				int dist1 = distancesFromTransfer[i][start];
				int dist2 = distancesFromTransfer[i][end];
				if (dist1 != INF && dist2 != INF) {
					int dist = dist1 + dist2;
					minDist = Math.min(minDist, dist);
				}
			}

			int answer = minDist == INF ? FAIL : minDist;

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static void bfs(int start, int tranferIdx) {
		// 초기화
		Arrays.fill(isVisited, false);
		q.clear();

		// 시작 정점 처리
		isVisited[start] = true;
		q.offer(start);

		int time = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int now = q.poll();
				distancesFromTransfer[tranferIdx][now] = time;

				for (int next : adjList[now]) {
					if (!isVisited[next]) {
						isVisited[next] = true;
						q.offer(next);
					}
				}
			}

			time += 2;
		}

		return;

	}

}