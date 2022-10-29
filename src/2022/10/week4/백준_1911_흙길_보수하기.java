import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Range implements Comparable<Range> {
        int start;
        int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * 시작 위치 오름차순, 끝 위치 내림차순 비교 함수
         */
        @Override
        public int compareTo(Range r) {
            if (this.start == r.start) {
                return -Integer.compare(this.end, r.end);
            }
            return Integer.compare(this.start, r.start);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 물웅덩이 개수, 널빤지 크기 입력
        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        // 물웅덩이들 입력
        List<Range> waterList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            waterList.add(new Range(start, end));
        }

        // 정렬
        Collections.sort(waterList);

        // 앞에서부터 구간을 처리하면서 널빤지 놓기
        int answer = 0;
        int lastFilled = -1;
        for (Range range : waterList) {
            int start = range.start;
            int end = range.end;

            // 아직 채워지지 않은 구간이면
            if (lastFilled < end) {
                // 필요한 널빤지 수 계산
                int left = Math.max(start, lastFilled);
                int right = end - 1;
                int lengthToFill = right - left + 1;
                int woodCnt = lengthToFill % L == 0 ? lengthToFill / L : lengthToFill / L + 1;

                // 채우기
                answer += woodCnt;
                lastFilled = left + woodCnt * L;
            }
        }

        System.out.println(answer);

    } // end main

}