// 73124KB, 520ms

package bj14676;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N, M, K;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] inDegrees = new int[MAX_N + 1];
	static int[] cntExisting = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// M개의 관계 정보 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int before = Integer.parseInt(st.nextToken());
			int after = Integer.parseInt(st.nextToken());

			adjList[before].add(after);
			inDegrees[after]++;
		}

		// K개의 건설 또는 파괴 처리
		boolean isValid = true;
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int type = Integer.parseInt(st.nextToken());
			int buildingNum = Integer.parseInt(st.nextToken());
			
			// 건설
			if (type == 1) {
				if (inDegrees[buildingNum] == 0) {
					cntExisting[buildingNum]++;
					
					// 새로 지어진 빌딩일 경우, 이 빌딩을 선행 조건으로 가지는 빌딩들의 inDegree를 갱신
					if (cntExisting[buildingNum] == 1) {
						for (int next : adjList[buildingNum]) {
							inDegrees[next]--;
						}
					}
				}
				// 못 짓는 걸 지으면 실패
				else {
					isValid = false;
				}
			}
			
			// 파괴
			else {
				if (cntExisting[buildingNum] > 0) {
					cntExisting[buildingNum]--;
					
					// 1개 남은 게 파괴되어 더 이상 없는 경우, 이 빌딩을 선행 조건으로 가지는 빌딩들의 inDegree를 갱신
					if (cntExisting[buildingNum] == 0) {
						for (int next : adjList[buildingNum]) {
							inDegrees[next]++;
						}
					}
				}
				// 없는 걸 파괴하면 실패
				else {
					isValid = false;
				}
			}
			
		}
        
		// 정답 출력
		String answer = isValid ? "King-God-Emperor" : "Lier!";
		System.out.println(answer);

	} // end main

}