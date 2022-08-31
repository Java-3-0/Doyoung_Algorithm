import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 1000;
    static final int MAX_WEIGHT = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 추 개수 입력
        final int N = Integer.parseInt(br.readLine());

        // 추들의 무게 입력
        int[] weights = new int[N];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }

        // 무게 오름차순으로 정렬
        Arrays.sort(weights);

        // 작은 무게부터 시도하면서 측정 가능한 최대 무게를 갱신
        int sum = 0;
        int maxPossible = 0;

        for (int i = 0; i < N; i++) {
            int weight = weights[i];
            if (weight <= sum + 1) {
                sum += weight;
                maxPossible = sum;
            } else {
                break;
            }
        }

        // 정답 출력
        int answer = maxPossible + 1;
        System.out.println(answer);

    } // end main

}