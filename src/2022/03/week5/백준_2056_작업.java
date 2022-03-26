// 78588KB, 692ms

package bj2056;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000;

	static int N;
	static int[] timeRequired = new int[MAX_N + 1];
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] inDegrees = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 작업 수 입력
		N = Integer.parseInt(br.readLine());

		// 각 작업의 정보 입력
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			timeRequired[i] = Integer.parseInt(st.nextToken());
			inDegrees[i] = Integer.parseInt(st.nextToken());
			for (int k = 0; k < inDegrees[i]; k++) {
				int pre = Integer.parseInt(st.nextToken());
				adjList[pre].add(i);
			}
		}
		
		// 정답 계산
		int answer = getTimeToFinishAll();
		
		// 출력
		System.out.println(answer);

	} // end main

	/** 모든 작업을 끝내기까지 필요한 최소 시간을 계산해서 리턴 */
	public static int getTimeToFinishAll() {
		int[] timeToFinish = new int[N + 1];

		// 시작부터 바로 진행할 수 있는 작업들을 큐에 넣는다.
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= N; i++) {
			if (inDegrees[i] == 0) {
				q.offer(i);
			}
		}
		
		while (!q.isEmpty()) {
			// 큐에서 작업을 꺼내서 수행한다.
			int here = q.poll();
			timeToFinish[here] += timeRequired[here];
			
			for (int next : adjList[here]) {
				// 이 작업 이후에 이루어져야 하는 작업들의 시작 시간을 갱신한다.
				timeToFinish[next] = Math.max(timeToFinish[next], timeToFinish[here]);
				
				// inDegree를 하나 줄이고, 0이 되었다면 작업을 진행 가능하니 큐에 넣는다.
				inDegrees[next]--;
				if (inDegrees[next] == 0) {
					q.offer(next);
				}
			}
		}

		// 가장 늦게 끝나는 작업이 끝나는 시간을 리턴
		int ret = 0;
		for (int i = 1; i <= N; i++) {
			ret = Math.max(ret, timeToFinish[i]);
		}
		
		return ret;
	}

}