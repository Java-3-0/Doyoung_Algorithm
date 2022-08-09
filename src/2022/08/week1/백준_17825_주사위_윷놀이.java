import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int[] move10 = {10, 113, 116, 119, 125, 130};
    static final int[] move20 = {20, 122, 124, 125, 130, 135};
    static final int[] move30 = {30, 128, 127, 126, 125, 130};

    // 주사위 정보
    static int[] dices = new int[10];
    // 시작 칸은 0번 칸, 도착 칸은 42번 칸, 중앙에 있는 칸들은 적혀있는 수에 100을 더한 번호를 가지는 칸
    static final int START = 0, FINISH = 42;
    static int[] nextPos = new int[FINISH + 100 + 1];
    // 말 4개 각각의 위치
    static int[] horsePositions = new int[4];
    // 중복순열
    static int[] permu = new int[10];
    // 정답
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 맵 이동 정보 만들기
        initNextPos();

        // 주사위에서 나온 수 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < 10; i++) {
            dices[i] = Integer.parseInt(st.nextToken());
        }

        // 완전탐색
        permutation(0);

        // 정답 출력
        System.out.println(answer);

    } // end main

    /**
     * 4^10 중복순열 완전탐색
     */
    public static void permutation(int startIdx) {
        // 순열이 완성된 경우
        if (startIdx == 10) {
            // 모든 말은 출발지에서 시작
            Arrays.fill(horsePositions, START);

            // 주사위에 나온대로 이동해서 점수 합산
            int sumScore = 0;
            for (int i = 0; i < 10; i++) {
                int horseIdx = permu[i];
                int curPos = horsePositions[horseIdx];
                int moveAmount = dices[i];

                // 이미 도착 칸에 있는 말은 이동할 수 없다
                if (curPos == FINISH) {
                    return;
                }

                // 이동할 칸 계산
                int nextPos = getNextPos(curPos, moveAmount);

                // 다른 말이 있는 칸으로는 이동할 수 없다 (도착 칸 제외)
                for (int otherHorseIdx = 0; otherHorseIdx < 4; otherHorseIdx++) {
                    if (otherHorseIdx != horseIdx && horsePositions[otherHorseIdx] == nextPos && nextPos != FINISH) {
                        return;
                    }
                }

                // 말의 위치와 점수 갱신
                horsePositions[horseIdx] = getNextPos(curPos, dices[i]);
                sumScore += getScore(horsePositions[horseIdx]);
            }

            // 정답 갱신
            answer = Math.max(answer, sumScore);
            return;
        }

        // 순열이 완성되지 않은 경우 만들어가기
        for (int pick = 0; pick < 4; pick++) {
            permu[startIdx] = pick;
            permutation(startIdx + 1);
        }
    }

    /**
     * nextPos 배열 초기화
     */
    public static void initNextPos() {
        // 일반
        for (int i = 0; i <= 40; i += 2) {
            nextPos[i] = i + 2;
        }

        // 도착
        nextPos[FINISH] = FINISH;

        // 중앙 왼쪽
        nextPos[113] = 116;
        nextPos[116] = 119;
        nextPos[119] = 125;

        // 중앙 아래쪽
        nextPos[122] = 124;
        nextPos[124] = 125;

        // 중앙 오른쪽
        nextPos[128] = 127;
        nextPos[127] = 126;
        nextPos[126] = 125;

        // 중앙 위쪽
        nextPos[125] = 130;
        nextPos[130] = 135;
        nextPos[135] = 40;
    }

    /**
     * pos에서 moveAmount만큼 이동한 칸을 리턴
     */
    public static int getNextPos(int pos, int moveAmount) {
        if (pos == 10) {
            return move10[moveAmount];
        } else if (pos == 20) {
            return move20[moveAmount];
        } else if (pos == 30) {
            return move30[moveAmount];
        } else {
            int cur = pos;
            for (int i = 0; i < moveAmount; i++) {
                cur = nextPos[cur];
            }
            return cur;
        }
    }

    /**
     * pos 칸의 점수를 리턴
     */
    public static int getScore(int pos) {
        if (pos == START || pos == FINISH) {
            return 0;
        } else {
            return pos % 100;
        }
    }


}