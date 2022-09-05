import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int INF = (int) (1e9 + 1e7);
    static final int NO_KEY = -1, MULTIPLE_KEYS = -2;

    static List<Integer> keys = new ArrayList<>();
    static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 초기 상태를 맵에 저장, key 들은 리스트에 저장
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int key = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            map.put(key, value);
            keys.add(key);
        }

        // 편의를 위해 -INF, INF 도 keys 리스트에 추가
        keys.add(INF);
        keys.add(-INF);

        // keys 를 정렬
        Collections.sort(keys);

        // M 개의 쿼리 수행
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int queryType = Integer.parseInt(st.nextToken());

            switch (queryType) {
                case 1:
                    int key1 = Integer.parseInt(st.nextToken());
                    int value1 = Integer.parseInt(st.nextToken());

                    // 맵에 추가
                    map.put(key1, value1);

                    // 키 리스트에도 추가할 위치를 찾아서 추가
                    int idxToInsert = findMinKeyIdxGreaterThanTarget(key1);
                    keys.add(idxToInsert, key1);

                    break;
                case 2:
                    int target2 = Integer.parseInt(st.nextToken());
                    int value2 = Integer.parseInt(st.nextToken());

                    // 이미 있는 키면 바로 맵에서 수정
                    if (map.containsKey(target2)) {
                        map.put(target2, value2);
                    }

                    // 없는 키면, 근접한 키를 찾아서 수정
                    else {
                        int keyIdx2 = findNearestKeyIdx(target2, K);
                        if (keyIdx2 >= 0) {
                            int key2 = keys.get(keyIdx2);
                            map.put(key2, value2);
                        }
                    }

                    break;
                case 3:
                    int target3 = Integer.parseInt(st.nextToken());

                    // 있는 키면 밸류를 그대로 출력
                    if (map.containsKey(target3)) {
                        sb.append(map.get(target3));
                    } else {
                        // 근접한 키를 찾는다.
                        int keyIdx3 = findNearestKeyIdx(target3, K);

                        // 조건에 맞게 출력에 추가
                        if (keyIdx3 >= 0) {
                            int key3 = keys.get(keyIdx3);
                            sb.append(map.get(key3));
                        } else if (keyIdx3 == NO_KEY) {
                            sb.append(-1);
                        } else if (keyIdx3 == MULTIPLE_KEYS) {
                            sb.append("?");
                        }
                    }

                    sb.append("\n");

                    break;
                default:
                    break;
            }
        }

        System.out.print(sb.toString());
    }

    /**
     * target으로부터 가장 가까운 키의 인덱스를 찾는다. 없으면 -1, 두 개 이상이면 -2를 리턴
     */
    public static int findNearestKeyIdx(int target, int K) {
        int rightIdx = findMinKeyIdxGreaterThanTarget(target);

        int leftVal = keys.get(rightIdx - 1);
        int rightVal = keys.get(rightIdx);

        int leftDiff = Math.abs(target - leftVal);
        int rightDiff = Math.abs(target - rightVal);

        // 범위 내 키를 찾을 수 있는 경우
        if (Math.min(leftDiff, rightDiff) <= K) {
            // 양쪽 거리가 같은 경우
            if (leftDiff == rightDiff) {
                return MULTIPLE_KEYS;
            }

            // 왼쪽 수가 더 가까운 경우
            else if (leftDiff < rightDiff) {
                return rightIdx - 1;
            }

            // 오른쪽 수가 더 가까운 경우
            else {
                return rightIdx;
            }
        }

        // 범위 내에 키가 없는 경우
        else {
            return NO_KEY;
        }
    }

    /**
     * target보다 큰 키 중 최솟값의 인덱스를 리턴
     */
    public static int findMinKeyIdxGreaterThanTarget(int target) {
        int size = keys.size();

        int left = 0;
        int right = size - 1;

        while (left < right) {
            int mid = (left + right) / 2;

            if (keys.get(mid) > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return right;
    }

}
