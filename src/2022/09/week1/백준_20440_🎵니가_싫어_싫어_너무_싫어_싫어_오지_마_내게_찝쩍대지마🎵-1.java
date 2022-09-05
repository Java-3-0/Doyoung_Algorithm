// 380744KB, 1788ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        TreeMap<Integer, Integer> deltaMap = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int inTime = Integer.parseInt(st.nextToken());
            int outTime = Integer.parseInt(st.nextToken());

            deltaMap.put(inTime, 1 + deltaMap.getOrDefault(inTime, 0));
            deltaMap.put(outTime, -1 + deltaMap.getOrDefault(outTime, 0));
        }

        int maxCnt = 0;
        int rangeLeft = 0;
        int rangeRight = 0;

        int cnt = 0;
        boolean isOpen = false;
        for (int key : deltaMap.keySet()) {
            int value = deltaMap.get(key);

            if (value > 0) {
                cnt += value;
                if (maxCnt < cnt) {
                    maxCnt = cnt;
                    rangeLeft = key;
                    isOpen = true;
                }
            } else if (value < 0) {
                if (maxCnt == cnt) {
                    if (isOpen) {
                        rangeRight = key;
                        isOpen = false;
                    }
                }
                cnt += value;
            }
        }

        sb.append(maxCnt).append("\n");
        sb.append(rangeLeft).append(" ").append(rangeRight).append("\n");

        System.out.print(sb.toString());
    }
}
