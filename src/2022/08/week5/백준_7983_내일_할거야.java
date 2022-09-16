// 281120KB, 1808ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final int INF = (int) (1e9 + 1e6);

    static class Homework implements Comparable<Homework> {
        /**
         * 과제를 하기 위해 필요한 시간
         */
        int timeToDo;
        /**
         * 과제를 끝마쳐야 하는 마감 기한
         */
        int deadline;

        public Homework(int timeToDo, int deadline) {
            this.timeToDo = timeToDo;
            this.deadline = deadline;
        }

        /**
         * 마감 기한 내림차순, 같으면 필요 시간 내림차순으로 정렬
         */
        @Override
        public int compareTo(Homework h) {
            if (this.deadline == h.deadline) {
                return -Integer.compare(this.timeToDo, h.timeToDo);
            }

            return -Integer.compare(this.deadline, h.deadline);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Homework> homeworkPriorityQueue = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int timeToDo = Integer.parseInt(st.nextToken());
            int deadline = Integer.parseInt(st.nextToken());

            homeworkPriorityQueue.offer(new Homework(timeToDo, deadline));
        }

        int maxStartDay = INF;
        while (!homeworkPriorityQueue.isEmpty()) {
            Homework hw = homeworkPriorityQueue.poll();

            maxStartDay = Math.min(maxStartDay, hw.deadline) - hw.timeToDo;
        }

        System.out.println(maxStartDay);

    } // end main

}