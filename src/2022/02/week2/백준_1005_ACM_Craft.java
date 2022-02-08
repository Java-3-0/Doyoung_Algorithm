// 235216KB, 704ms

package baek1005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Building {
	/** 건물 번호 */
	int num;
	/** 건물을 짓는 데 걸리는 시간 */
	int buildTime;
	/** 건물을 짓기 위해 선행 조건으로 완성시켜야 하는 건물들의 리스트 */
	List<Integer> prerequisites = new ArrayList<Integer>();

	/** 건물 번호와 건설 시간을 받아서 건물을 생성하는 생성자 */
	public Building(int num, int buildTime) {
		super();
		this.num = num;
		this.buildTime = buildTime;
	}

	/**
	 * 건물의 선행 건물의 번호를 prerequisites에 추가
	 * 
	 * @param preNum : 선행 건물의 번호
	 */
	public void addPrerequisite(Integer preNum) {
		this.prerequisites.add(preNum);
	}
}

public class Main {
	public static final int MAX_BUILDINGS = 1000;
	public static int[] cache = new int[MAX_BUILDINGS + 1];
	public static Building[] buildings = new Building[MAX_BUILDINGS + 1];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numTests = Integer.parseInt(br.readLine());

		for (int testCase = 0; testCase < numTests; testCase++) {
			// 캐시 초기화
			initCache();

			// 빌딩 수, 규칙 수 입력
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int numBuildings = Integer.parseInt(st.nextToken());
			int numRules = Integer.parseInt(st.nextToken());

			// 입력을 받아 건물을 생성해서 buildings라는 리스트로 만듦
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < numBuildings; i++) {
				int time = Integer.parseInt(st.nextToken());
				buildings[i + 1] = new Building(i + 1, time);
			} // end for (i)

			// 규칙을 입력받아서 각 건물 객체 내부의 prerequisites 리스트에 추가
			for (int i = 0; i < numRules; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				// 먼저 지어야 하는 건물
				int preNum = Integer.parseInt(st.nextToken());
				// pre가 완성된 후 지어야 하는 건물
				int postNum = Integer.parseInt(st.nextToken());
				// 선행 건물 리스트에 추가
				buildings[postNum].addPrerequisite(preNum);
			}

			// 목표 건물 입력
			int targetNum = Integer.parseInt(br.readLine());

			// 목표 건물 건설 완료에 걸리는 최소 시간 계산
			int answer = minTimeToComplete(buildings[targetNum]);
			System.out.println(answer);

		} // end for (testCase)

	}

	/** 캐시 메모리 전체를 -1로 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			cache[i] = -1;
		}
	}

	/** 건물 b를 짓기까지 드는 최소 시간을 리턴 */
	public static int minTimeToComplete(Building b) {
		// 이미 계산한 적이 있는 건물이라면 캐시에 저장된 값을 리턴
		if (cache[b.num] != -1) {
			return cache[b.num];
		}

		// base case : 선행 조건이 없다면, 이 건물의 소요 시간을 캐시에 저장하고 리턴
		if (b.prerequisites.isEmpty()) {
			cache[b.num] = b.buildTime;
			return b.buildTime;
		}

		// 가장 늦게 완성되는 선행 건물의 완성 시간을 재귀 호출로 구함
		int latestPre = 0;
		for (int preNum : b.prerequisites) {
			latestPre = Math.max(latestPre, minTimeToComplete(buildings[preNum]));
		}

		// 가장 늦게 완성된 선행 건물의 완성 시간 + 현재 건물의 건설 시간을 캐시에 저장하고 리턴
		int totalTime = latestPre + b.buildTime;
		cache[b.num] = totalTime;
		return totalTime;
	}
}