// 84700KB, 896ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static final int MAX_N = 200000;
    static final int MAX_SIZE = 2000;

    static class Ball implements Comparable<Ball> {
        int idx;
        int color;
        int size;

        public Ball(int idx, int color, int size) {
            this.idx = idx;
            this.color = color;
            this.size = size;
        }

        /**
         * 크기 오름차순, 크기가 같다면 색상 오름차순으로 정렬하기 위한 비교함수
         */
        @Override
        public int compareTo(Ball b) {
            if (this.size == b.size) {
                return this.color - b.color;
            }

            return this.size - b.size;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 공 개수 입력
        final int N = Integer.parseInt(br.readLine());

        // 공 입력받아서 리스트에 담기
        List<Ball> balls = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int color = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());

            balls.add(new Ball(i, color, size));
        }

        // 공 크기 오름차순, 같으면 공 색 오름차순으로 리스트 정렬
        Collections.sort(balls);

        int totalSmallBalls = 0;
        int[] totalSmallBallsOfColors = new int[MAX_N + 1];
        int[] answers = new int[N];

        // 가장 작은 공 처리
        Ball smallestBall = balls.get(0);
        int prevSize = smallestBall.size; // 이전 크기
        int prevColor = smallestBall.color; // 이전 색
        int bufferPrevSizeBalls = smallestBall.size; // 이전 크기를 가진 공의 합
        int bufferPrevSizeAndColorBalls = smallestBall.size; // 이전 크기와 색을 가진 공의 합
        answers[smallestBall.idx] = 0;

        // 나머지 공 처리
        for (int i = 1; i < N; i++) {
            Ball ball = balls.get(i);
            int idx = ball.idx;
            int color = ball.color;
            int size = ball.size;

            // 크기가 더 큰 경우, 두 버퍼 모두 갱신
            if (prevSize < size) {
                totalSmallBallsOfColors[prevColor] += bufferPrevSizeAndColorBalls;
                totalSmallBalls += bufferPrevSizeBalls;

                bufferPrevSizeBalls = size;
                bufferPrevSizeAndColorBalls = size;
            }

            // 크기가 같은 경우
            else if (prevSize == size) {
                // 색깔까지 같은 경우 크기색깔 버퍼에 누적
                if (prevColor == color) {
                    bufferPrevSizeAndColorBalls += size;
                }

                // 색깔은 다른 경우 크기색깔 버퍼 갱신
                else {
                    totalSmallBallsOfColors[prevColor] += bufferPrevSizeAndColorBalls;
                    bufferPrevSizeAndColorBalls = size;
                }

                // 크기 버퍼에 누적
                bufferPrevSizeBalls += size;
            }

            // 정답 계산
            answers[idx] = totalSmallBalls - totalSmallBallsOfColors[color];

            // prev 값 갱신
            prevSize = size;
            prevColor = color;
        }

        // 출력
        for (int num : answers) {
            sb.append(num).append("\n");
        }
        System.out.print(sb.toString());

    } // end main

}