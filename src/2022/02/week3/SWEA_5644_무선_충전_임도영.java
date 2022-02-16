import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/** 사람을 나타내는 객체 */
class Person {
	/** x좌표 */
	int x;
	/** x좌표 */
	int y;

	/** x좌표와 y좌표를 파라미터로 받아서 사람 객체를 생성하는 생성자 */
	public Person(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/** 이동 타입을 받아서 그 방향으로 사람을 이동시킴 */
	public void move(int moveType) {
		switch (moveType) {
		case 1:
			this.y -= 1;
			break;
		case 2:
			this.x += 1;
			break;
		case 3:
			this.y += 1;
			break;
		case 4:
			this.x -= 1;
			break;
		default:
			break;
		}
	}

}

/** 충전기를 나타내는 객체 */
class Charger implements Comparable<Charger> {
	/** x좌표 */
	int x;
	/** y좌표 */
	int y;
	/** 충전 범위 */
	int range;
	/** 충전 파워 */
	int power;

	/** x좌표, y좌표, 범위, 파워를 입력받아서 충전기 객체를 생성하는 생성자 */
	public Charger(int x, int y, int range, int power) {
		super();
		this.x = x;
		this.y = y;
		this.range = range;
		this.power = power;
	}

	/** (y, x) 지점에서 이 충전기에 연결 가능하면 true, 아니면 false 리턴 */
	public boolean canBeConnectedAt(int y, int x) {
		int distY = (this.y <= y) ? y - this.y : this.y - y;
		int distX = (this.x <= x) ? x - this.x : this.x - x;

		if (distX + distY <= this.range) {
			return true;
		}
		return false;
	}

	/** 충전기들을 파워의 오름차순으로 정렬하기 위한 비교함수 */
	@Override
	public int compareTo(Charger c) {
		return this.power - c.power;
	}
}

public class Solution {
	public static final int GRID_SIZE = 10;
	/** 지도 상 각 위치에서의 최선의 charger 번호. 없으면 -1 */
	public static int[][] bestCharger = new int[GRID_SIZE + 1][GRID_SIZE + 1];
	/** 지도 상 각 위치에서의 alternative한 charger 번호. 없으면 -1 */
	public static int[][] subCharger = new int[GRID_SIZE + 1][GRID_SIZE + 1];
	/** 충전기들의 배열 */
	public static Charger[] chargers;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 전역변수 초기화
			for (int i = 0; i < GRID_SIZE + 1; i++) {
				Arrays.fill(bestCharger[i], -1);
				Arrays.fill(subCharger[i], -1);
			}

			// 사용자 수, 충전기 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			int numMoves = Integer.parseInt(st.nextToken());
			int numChargers = Integer.parseInt(st.nextToken());

			// A 사용자의 이동 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			int[] moveA = new int[numMoves];
			for (int i = 0; i < numMoves; i++) {
				moveA[i] = Integer.parseInt(st.nextToken());
			}

			// B 사용자의 이동 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			int[] moveB = new int[numMoves];
			for (int i = 0; i < numMoves; i++) {
				moveB[i] = Integer.parseInt(st.nextToken());
			}

			// 각 충전기의 정보 입력
			chargers = new Charger[numChargers];
			for (int i = 0; i < numChargers; i++) {
				st = new StringTokenizer(br.readLine(), " ");

				Charger c = new Charger(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
						Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

				chargers[i] = c;
			}

			// 충전기들을 파워의 오름차순으로 정렬
			Arrays.sort(chargers);

			// 맵의 각 지점에서의 최적 충전기와, 차선책 충전기를 계산
			for (int i = 0; i < numChargers; i++) {
				Charger c = chargers[i];
				for (int y = 1; y < GRID_SIZE + 1; y++) {
					for (int x = 1; x < GRID_SIZE + 1; x++) {
						// 이 위치에서 충전기에 access 할 수 있다면
						if (c.canBeConnectedAt(y, x)) {
							// 기존 bestCharger가 subCharger가 된다.
							subCharger[y][x] = bestCharger[y][x];
							// c가 bestChager가 된다.
							bestCharger[y][x] = i;
						}
					}
				}
			}

			// 각 사람의 첫 위치에서 사람 객체 생성
			Person personA = new Person(1, 1);
			Person personB = new Person(GRID_SIZE, GRID_SIZE);

			// 충전량 계산
			int sum = 0;
			// 초기 위치에서 충전
			sum += maxCharge(personA, personB);

			// 이동하면서 충전
			for (int i = 0; i < numMoves; i++) {
				personA.move(moveA[i]);
				personB.move(moveB[i]);
				sum += maxCharge(personA, personB);
			}

			// 끝까지 진행한 후 정답을 스트링빌더에 담는다
			sb.append("#").append(testCase).append(" ").append(sum).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	}

	/** 두 사람의 위치가 주어졌을 때 그 때 가능한 충전량의 최대치를 구해서 리턴 */
	public static int maxCharge(Person p1, Person p2) {
		int best1 = bestCharger[p1.y][p1.x];
		int sub1 = subCharger[p1.y][p1.x];
		int best2 = bestCharger[p2.y][p2.x];
		int sub2 = subCharger[p2.y][p2.x];

		int bestPower1 = getPower(best1);
		int subPower1 = getPower(sub1);
		int bestPower2 = getPower(best2);
		int subPower2 = getPower(sub2);

		int maxSubPower = (subPower1 <= subPower2) ? subPower2 : subPower1;

		// 서로 최적인 게 다르면, 각자 최적인 걸 쓰고
		if (best1 != best2) {
			return bestPower1 + bestPower2;
			// 서로 최적인 게 같으면, 한 쪽이 서브를 쓴다. (서브 충전기의 파워가 더 큰 쪽이 양보)
		} else {
			return bestPower1 + maxSubPower;
		}
	}

	/** idx번 충전기의 출력을 리턴. 존재하지 않는 충전기이면 -1을 리턴 */
	public static int getPower(int idx) {
		if (idx < 0)
			return 0;

		return chargers[idx].power;
	}
}
