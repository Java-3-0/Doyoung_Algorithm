import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static Map<Integer, Set<Integer>> xToYSet = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine(), " ");
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            Set<Integer> ySet = xToYSet.getOrDefault(x, new HashSet<>());
            ySet.add(y);
            xToYSet.put(x, ySet);
        }

        int answer = 0;
        for (int x1 : xToYSet.keySet()) {
            int x2 = x1 + A;

            Set<Integer> ySet1 = xToYSet.get(x1);
            if (xToYSet.containsKey(x2)) {
                Set<Integer> ySet2 = xToYSet.get(x2);
                for (int y1 : ySet1) {
                    int y2 = y1 + B;
                    if (ySet1.contains(y2) && ySet2.contains(y1) && ySet2.contains(y2)) {
                        answer++;
                    }
                }
            }
        }

        System.out.println(answer);

    } // end main

}