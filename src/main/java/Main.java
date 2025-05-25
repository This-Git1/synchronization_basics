import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String rout = generateRoute("RLRFR", 100);

                int count = (int) rout
                        .chars()
                        .filter(c -> c == 'R')
                        .count();

                synchronized (sizeToFreq) {
                    sizeToFreq.merge(count, 1, Integer::sum);
                }
                System.out.println("Количество R = " + count);
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Анализ частот
        int mostFreq = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFreq = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)%n", mostFreq, maxCount);
        System.out.println("Другие размеры:");

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != mostFreq) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }

    }



    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
