// 20496KB, 1983ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
 
public class Solution {
    /** 위치를 나타내는 객체 */
    static class Position {
        /** y좌표 */
        int y;
        /** x좌표 */
        int x;
 
        /** y좌표와 x좌표를 입력받아 위치 객체를 생성하는 생성자 */
        public Position(int y, int x) {
            super();
            this.y = y;
            this.x = x;
        }
 
        /** 파라미터로 받은 위치와의 거리를 계산해서 리턴 */
        public int getDistance(Position p) {
            return Math.abs(this.y - p.y) + Math.abs(this.x - p.x);
        }
    }
 
    public static final int MAX_N = 10;
    public static int N;
    public static Position company;
    public static Position home;
    public static Position[] customers = new Position[MAX_N];
    public static boolean[] isSelected = new boolean[MAX_N];
    public static int[] permu = new int[MAX_N];
    public static int minDistance;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
 
        int TESTS = Integer.parseInt(br.readLine());
        for (int testCase = 1; testCase <= TESTS; testCase++) {
            // 전역변수 초기화
            initMemory();
 
            // 고객의 수 입력
            N = Integer.parseInt(br.readLine());
            // 회사 위치, 집 위치, 고객들 위치 입력
            st = new StringTokenizer(br.readLine(), " ");
            company = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            home = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            customers = new Position[N];
            for (int i = 0; i < N; i++) {
                customers[i] = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
 
            // 모든 순열을 완전탐색하면서 minDistance 값 갱신
            permutation(0);
 
            // 스트링빌더에 답을 형식에 맞게 담음
            int answer = minDistance;
            sb.append("#").append(testCase).append(" ").append(answer).append("\n");
 
        } // end for testCase
 
        // 정답 출력
        System.out.print(sb.toString());
    } // end main
 
    /** 전역변수 값을 초기화하는 함수로, 테스트케이스마다 호출해야 한다. */
    public static void initMemory() {
        N = 0;
        company = null;
        home = null;
        Arrays.fill(customers, null);
        Arrays.fill(isSelected, false);
        Arrays.fill(permu, 0);
        minDistance = Integer.MAX_VALUE;
    }
 
    /** 가능한 모든 순열을 완전탐색하면서 minDistance를 갱신한다. */
    public static void permutation(int count) {
        // 순열이 완성된 경우,
        if (count == N) {
            int distance = 0;
            // 회사에서 0번 고객 집으로
            distance += company.getDistance(customers[permu[0]]);
            // 0번 고객 집부터 N-1번 고객 집까지 순서대로
            for (int i = 0; i < N - 1; i++) {
                distance += customers[permu[i]].getDistance(customers[permu[i + 1]]);
            }
            // N-1번 고객 집부터 내 집으로
            distance += customers[permu[N - 1]].getDistance(home);
 
            // minDistance 갱신
            if (distance < minDistance) {
                minDistance = distance;
            }
             
            return;
        }
 
        // 순열이 완성되지 않은 경우
        for (int idx = 0; idx < N; idx++) {
            // 이미 사용되었다면 지나간다.
            if (isSelected[idx]) {
                continue;
            }
 
            // 아직 사용되지 않은 것이면 넣고, 다음 재귀호출을 부르고, 사용 여부를 되돌린다.
            permu[count] = idx;
            isSelected[idx] = true;
            permutation(count + 1);
            isSelected[idx] = false;
        }
 
    }
 
}