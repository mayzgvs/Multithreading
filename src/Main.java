//Задача 1.
//import java.util.concurrent.locks.ReentrantLock;
//
//public class CounterExample {
//    private static int counter = 0;
//    private static final ReentrantLock lock = new ReentrantLock();
//
//    public static void main(String[] args) {
//        Thread[] threads = new Thread[5];
//
//        for (int i = 0; i < 5; i++) {
//            threads[i] = new Thread(new CounterTask());
//            threads[i].start();
//        }
//
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Final Counter Value: " + counter);
//    }
//
//    static class CounterTask implements Runnable {
//        @Override
//        public void run() {
//            for (int i = 0; i < 1000; i++) {
//                lock.lock();
//                try {
//                    counter++;
//                } finally {
//                    lock.unlock();
//                }
//            }
//        }
//    }
//}
//
//
//Задача 2: Генерация последовательности чисел
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class NumberGenerator {
//    private static final List<Integer> numbers = new CopyOnWriteArrayList<>();
//
//    public static void main(String[] args) {
//        Thread[] threads = new Thread[10];
//
//        for (int i = 0; i < 10; i++) {
//            threads[i] = new Thread(new NumberAddingTask());
//            threads[i].start();
//        }
//
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Final Numbers List: " + numbers);
//    }
//
//    static class NumberAddingTask implements Runnable {
//        @Override
//        public void run() {
//            for (int i = 1; i <= 100; i++) {
//                numbers.add(i);
//            }
//        }
//    }
//}
//
//
//Задача 3: Распределение задач с использованием пула потоков
//        import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class TaskDistribution {
//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//        for (int i = 1; i <= 20; i++) {
//            final int taskNumber = i;
//            executorService.submit(() -> {
//                System.out.println("Thread: " + Thread.currentThread().getName() + ", Task Number: " + taskNumber);
//            });
//        }
//
//        executorService.shutdown();
//    }
//}
//
//
//Задача 4: Симуляция работы банка
//import java.util.concurrent.locks.ReentrantLock;
//
//class BankAccount {
//    private double balance;
//    private final ReentrantLock lock = new ReentrantLock();
//
//    public BankAccount(double initialBalance) {
//        this.balance = initialBalance;
//    }
//
//    public void transfer(BankAccount target, double amount) {
//        if (this == target) return;
//
//        // Locking the accounts in a consistent order to avoid deadlock
//        BankAccount firstLock = this.hashCode() < target.hashCode() ? this : target;
//        BankAccount secondLock = this.hashCode() < target.hashCode() ? target : this;
//
//        firstLock.lock.lock();
//        secondLock.lock.lock();
//        try {
//            if (balance >= amount) {
//                balance -= amount;
//                target.balance += amount;
//                System.out.println("Transferred " + amount + " from " + this + " to " + target);
//            } else {
//                System.out.println("Insufficient funds for transfer from " + this);
//            }
//        } finally {
//            secondLock.lock.unlock();
//            firstLock.lock.unlock();
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Account{" +
//                "balance=" + balance +
//                '}';
//    }
//}
//
//public class BankSimulation {
//    public static void main(String[] args) {
//        BankAccount account1 = new BankAccount(1000);
//        BankAccount account2 = new BankAccount(500);
//
//        Thread t1 = new Thread(() -> account1.transfer(account2, 200));
//        Thread t2 = new Thread(() -> account2.transfer(account1, 300));
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Final Balances: ");
//        System.out.println(account1);
//        System.out.println(account2);
//    }
//}
//
//
//Задача 5: Барьер синхронизации
//import java.util.concurrent.CyclicBarrier;
//
//public class BarrierExample {
//    private static final int NUMBER_OF_THREADS = 5;
//    private static final CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_THREADS, () -> System.out.println("All tasks completed. Proceeding to next phase."));
//
//    public static void main(String[] args) {
//        Thread[] threads = new Thread[NUMBER_OF_THREADS];
//
//        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
//            threads[i] = new Thread(new Task());
//            threads[i].start();
//        }
//
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    static class Task implements Runnable {
//        @Override
//        public void run() {
//            try {
//                System.out.println(Thread.currentThread().getName() + " is performing its task.");
//                // Simulating some work with sleep
//                Thread.sleep((int) (Math.random() * 1000));
//                barrier.await(); // Wait for other threads to reach the barrier
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
//
//Задача 6: Ограниченный доступ к ресурсу
//import java.util.concurrent.Semaphore;
//
//public class LimitedAccessResource {
//    private static final Semaphore semaphore = new Semaphore(2); // Ограничение на 2 потока
//
//    public static void main(String[] args) {
//        for (int i = 1; i <= 5; i++) {
//            new Thread(new ResourceUser(i)).start();
//        }
//    }
//
//    static class ResourceUser implements Runnable {
//        private final int userId;
//
//        public ResourceUser(int userId) {
//            this.userId = userId;
//        }
//
//        @Override
//        public void run() {
//            try {
//                semaphore.acquire(); // Запрашиваем доступ к ресурсу
//                System.out.println("User " + userId + " is using the resource.");
//                Thread.sleep(2000); // Имитируем использование ресурса
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                System.out.println("User " + userId + " has released the resource.");
//                semaphore.release(); // Освобождаем ресурс
//            }
//        }
//    }
//}
//
//
//Задача 7: Обработка результатов задач
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class FactorialCalculator {
//    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        Future<Long>[] futures = new Future[10];
//
//        for (int i = 0; i < 10; i++) {
//            final int number = i + 1;
//            futures[i] = executor.submit(new Callable<Long>() {
//                @Override
//                public Long call() {
//                    return factorial(number);
//                }
//            });
//        }
//
//        for (int i = 0; i < futures.length; i++) {
//            try {
//                System.out.println("Factorial of " + (i + 1) + " is: " + futures[i].get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        executor.shutdown();
//    }
//
//    private static long factorial(int n) {
//        if (n == 0) return 1;
//        return n * factorial(n - 1);
//    }
//}
//
//
//Задача 8: Симуляция производственной линии
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//
//public class ProductionLineSimulation {
//    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
//
//    public static void main(String[] args) {
//        Thread producer = new Thread(new Producer());
//        Thread consumer = new Thread(new Consumer());
//
//        producer.start();
//        consumer.start();
//    }
//
//    static class Producer implements Runnable {
//        @Override
//        public void run() {
//            try {
//                for (int i = 1; i <= 10; i++) {
//                    String item = "Item " + i;
//                    queue.put(item); // Добавляем элемент в очередь
//                    System.out.println("Produced: " + item);
//                    Thread.sleep(500); // Имитируем время производства
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    static class Consumer implements Runnable {
//        @Override
//        public void run() {
//            try {
//                for (int i = 1; i <= 10; i++) {
//                    String item = queue.take(); // Извлекаем элемент из очереди
//                    System.out.println("Consumed: " + item);
//                    Thread.sleep(1000); // Имитируем время обработки
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
//
//Задача 9: Многопоточная сортировка
//import java.util.Arrays;
//import java.util.concurrent.CountDownLatch;
//
//public class MultiThreadedSorting {
//    private static final int THREAD_COUNT = 4;
//
//    public static void main(String[] args) throws InterruptedException {
//        int[] array = {5, 2, 8, 1, 3, 7, 6, 4, 9, 0};
//        System.out.println("Original array: " + Arrays.toString(array));
//
//        int partSize = array.length / THREAD_COUNT;
//        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
//        int[][] parts = new int[THREAD_COUNT][];
//
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            int start = i * partSize;
//            int end = (i == THREAD_COUNT - 1) ? array.length : start + partSize;
//            parts[i] = Arrays.copyOfRange(array, start, end);
//            new Thread(new SortTask(parts[i], latch)).start();
//        }
//
//        latch.await(); // Ждем завершения всех потоков
//
//        // Объединяем отсортированные части
//        int[] sortedArray = merge(parts);
//        System.out.println("Sorted array: " + Arrays.toString(sortedArray));
//    }
//
//    private static int[] merge(int[][] parts) {
//        return Arrays.stream(parts).flatMapToInt(Arrays::stream).sorted().toArray();
//    }
//
//    static class SortTask implements Runnable {
//        private final int[] part;
//        private final CountDownLatch latch;
//
//        public SortTask(int[] part, CountDownLatch latch) {
//            this.part = part;
//            this.latch = latch;
//        }
//
//        @Override
//        public void run() {
//            Arrays.sort(part);
//            latch.countDown(); // Уменьшаем счетчик по завершении сортировки
//        }
//    }
//}
//
//
//Задача 10: Обед философов (*)
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class DiningPhilosophers {
//    private static final int NUM_PHILOSOPHERS = 5;
//    private static final Lock[] forks = new Lock[NUM_PHILOSOPHERS];
//
//    static {
//        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
//            forks[i] = new ReentrantLock();
//        }
//    }
//
//    public static void main(String[] args) {
//        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
//            final int philosopherId = i;
//            new Thread(() -> dine(philosopherId)).start();
//        }
//    }
//
//    private static void dine(int philosopherId) {
//        while (true) {
//            think(philosopherId);
//            eat(philosopherId);
//        }
//    }
//
//    private static void think(int philosopherId) {
//        System.out.println("Philosopher " + philosopherId + " is thinking.");
//        try {
//            Thread.sleep((long) (Math.random() * 1000));
//        } catch (InterruptedException e) { }
//    }
//
//    private static void eat(int philosopherId) {
//        int leftFork = philosopherId;
//        int rightFork = (philosopherId + 1) % NUM_PHILOSOPHERS;
//
//        // Избегаем взаимной блокировки, сначала берем меньший номер вилки
//        Lock firstFork = forks[leftFork].hashCode() < forks[rightFork].hashCode() ? forks[leftFork] : forks[rightFork];
//        Lock secondFork = firstFork == forks[leftFork] ? forks[rightFork] : forks[leftFork];
//
//        firstFork.lock();
//        secondFork.lock();
//
//        try {
//            System.out.println("Philosopher " + philosopherId + " is eating.");
//            Thread.sleep((long) (Math.random() * 1000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            firstFork.unlock();
//            secondFork.unlock();
//            System.out.println("Philosopher " + philosopherId + " has finished eating.");
//        }
//    }
//}
//
//
//Задача 11: Расчёт матрицы в параллельных потоках
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class MatrixMultiplication {
//    public static void main(String[] args) {
//        int[][] matrixA = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}
//        };
//
//        int[][] matrixB = {
//                {9, 8, 7},
//                {6, 5, 4},
//                {3, 2, 1}
//        };
//
//        int rowsA = matrixA.length;
//        int colsB = matrixB[0].length;
//        int[][] resultMatrix = new int[rowsA][colsB];
//
//        ExecutorService executor = Executors.newFixedThreadPool(rowsA);
//
//        for (int i = 0; i < rowsA; i++) {
//            final int row = i;
//            executor.submit(() -> multiplyRow(matrixA, matrixB, resultMatrix, row));
//        }
//
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//            // Ждем завершения всех потоков
//        }
//
//        // Выводим результат
//        System.out.println("Resulting Matrix:");
//        for (int[] row : resultMatrix) {
//            for (int value : row) {
//                System.out.print(value + " ");
//            }
//            System.out.println();
//        }
//    }
//
//    private static void multiplyRow(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int row) {
//        for (int j = 0; j < matrixB[0].length; j++) {
//            resultMatrix[row][j] = 0;
//            for (int k = 0; k < matrixA[0].length; k++) {
//                resultMatrix[row][j] += matrixA[row][k] * matrixB[k][j];
//            }
//        }
//    }
//}
//
//
//Задача 12: Таймер с многопоточностью
//public class TimerExample {
//    private static volatile boolean running = true;
//
//    public static void main(String[] args) {
//        Thread timerThread = new Thread(() -> {
//            while (running) {
//                System.out.println("Current time: " + System.currentTimeMillis());
//                try {
//                    Thread.sleep(1000); // Пауза на 1 секунду
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        });
//
//        timerThread.start();
//
//        // Второй поток для остановки таймера через 10 секунд
//        new Thread(() -> {
//            try {
//                Thread.sleep(10000); // Пауза на 10 секунд
//                running = false; // Останавливаем таймер
//                System.out.println("Timer stopped.");
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }).start();
//    }
//}
