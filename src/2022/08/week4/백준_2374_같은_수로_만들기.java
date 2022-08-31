// 13136KB, 124ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final Long INF = 987654321098765L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수 개수 입력
        int N = Integer.parseInt(br.readLine());

        // 연속되는 수 제외하고 리스트에 넣기
        List<Long> list = new ArrayList<>();
        long first = Long.parseLong(br.readLine());
        list.add(first);

        long prev = first;
        for (int i = 1; i < N; i++) {
            long now = Long.parseLong(br.readLine());

            if (prev != now) {
                list.add(now);
            }

            prev = now;
        }

        // 리스트에 1개가 남을 때까지 가장 작은 수 제거 반복
        long answer = 0L;
        while (list.size() > 1) {
            // 가장 작은 수 찾기
            Long minVal = INF;
            int minIdx = 0;

            int size = list.size();
            for (int idx = 0; idx < size; idx++) {
                long val = list.get(idx);
                if (val < minVal) {
                    minVal = val;
                    minIdx = idx;
                }
            }

            // 양 옆 중 가까운 수로 만들기
            long addAmount = INF;
            if (minIdx > 0) {
                addAmount = Math.min(addAmount, Math.abs(minVal - list.get(minIdx - 1)));
            }
            if (minIdx < size - 1) {
                addAmount = Math.min(addAmount, Math.abs(minVal - list.get(minIdx + 1)));
            }

            answer += addAmount;

            // 리스트에서 제거
            list.remove(minIdx);
        }

        System.out.println(answer);

    } // end main

}