package DataTransfer;

import java.io.*;
import java.net.*;
import java.util.Random;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private static final int PORT = 8080;
    static final long PROGRAM_DURATION_MS = 10 * 60 * 1000; // 10 minutes in milliseconds
    static long startTime; // System Clock 시작 시간
    private static int clientCount = 0; // 클라이언트 카운트

    public static void main(String[] args) throws IOException {
        startTime = System.currentTimeMillis(); // 시작 시간 기록

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 실행 중이며 클라이언트 연결을 기다리고 있습니다...");

            long endTime = startTime + PROGRAM_DURATION_MS; // 종료 시간 설정

            while (System.currentTimeMillis() < endTime) {
                Socket clientSocket = serverSocket.accept();
                clientCount++; // 새로운 클라이언트 연결 시 카운트 증가
                System.out.println("클라이언트 " + clientCount + " 연결됨: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, clientCount);
                new Thread(clientHandler).start();

                // "exit" 입력 시 클라이언트 종료
                /*if ("exit".equalsIgnoreCase(answer)) {
                    System.out.println("서버를 종료합니다.");
                    break;
                } */
            }


            // 10분이 지나면 종료 메시지 출력
            if (System.currentTimeMillis() >= endTime) {
                System.out.println("10분이 지나서 자동으로 종료됩니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("서버 종료. 총 합계: " + ClientHandler.getTotalSum());

        // 프로그램 종료 시간을 로그로 기록
        log("프로그램 종료 시간: " + System.currentTimeMillis());
    }

    // 로그를 파일에 기록하는 메서드
    private static void log(String message) {
        try (FileWriter fw = new FileWriter("server_log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


   /* static class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random = new Random();
    private String problem;
    private int clientNumber; // 클라이언트 번호
    private static int totalSum = 0;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.problem = generateRandomProblem();
    }

    public static int getTotalSum() {
        return totalSum;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                long currentTime = System.currentTimeMillis(); // 현재 시간 기록

                // 프로그램 실행 시간이 초과되면 종료
                if (currentTime - Server.startTime >= Server.PROGRAM_DURATION_MS) {
                    break;
                }

                // 클라이언트에게 문제와 현재 시간을 전송
                out.println("클라이언트 " + clientNumber + ", 다음 산술 문제를 풀어보세요: " + problem);
                out.println("현재 시간 (System Clock): " + currentTime);

                String clientAnswer = in.readLine();
                int correctAnswer = evaluateProblem(problem);

                try {
                    int userAnswer = Integer.parseInt(clientAnswer.trim());
                    if (userAnswer == correctAnswer) {
                        out.println("클라이언트 " + clientNumber + ", 정답입니다!");

                        // 정답을 합산
                        totalSum += userAnswer;

                        // 새로운 문제 생성
                        problem = generateRandomProblem();
                    } else {
                        out.println("클라이언트 " + clientNumber + ", 오답입니다. 다시 시도하세요.");
                    }
                } catch (NumberFormatException e) {
                    out.println("클라이언트 " + clientNumber + ", 잘못된 답변 형식입니다. 정수를 입력하세요.");
                }
            }
        } catch (IOException e) {
            // 클라이언트 연결이 끊겼을 때 합계 출력
            System.out.println("클라이언트 " + clientNumber + " 연결 끊김, totalSum = " + totalSum);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 클라이언트 종료 시간을 로그로 기록
        log("클라이언트 " + clientNumber + " 종료 시간: " + System.currentTimeMillis());

    }


    private String generateRandomProblem() {
        int num1 = random.nextInt(101);
        int num2 = random.nextInt(101);
        int num3 = random.nextInt(101);

        char operator1 = getOperator(random);
        char operator2 = getOperator(random);

        return num1 + " " + operator1 + " " + num2 + " " + operator2 + " " + num3;
    }

    private char getOperator(Random random) {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }

    private int evaluateProblem(String problem) {
        String[] tokens = problem.split(" ");
        int result = Integer.parseInt(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            char operator = tokens[i].charAt(0);
            int operand = Integer.parseInt(tokens[i + 1]);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    result /= operand;
                    break;
            }
        }

        return result;
        }
    }
}
*/
















/*import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Server started.");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        try {
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random;
    private int currentOperand1;
    private int currentOperand2;
    private char currentOperator;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.random = new Random();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (currentOperand1 == 0 && currentOperand2 == 0) {
                    // Generate a new question if the current question has been answered correctly
                    currentOperand1 = random.nextInt(101);
                    currentOperand2 = random.nextInt(101);
                    currentOperator = getRandomOperator();
                }

                String question = currentOperand1 + " " + currentOperator + " " + currentOperand2 + " = ?";
                out.println(question);

                String clientAnswer = in.readLine();
                System.out.println("Client's answer: " + clientAnswer);

                int expectedAnswer = calculateExpectedAnswer(currentOperand1, currentOperand2, currentOperator);
                System.out.println("Expected answer: " + expectedAnswer);

                // Check the client's answer
                if (Integer.toString(expectedAnswer).equals(clientAnswer)) {
                    out.println("Correct! Here's a new question.");
                    // Reset current question
                    currentOperand1 = currentOperand2 = 0;
                } else {
                    out.println("Incorrect. Try again with the same question.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private char getRandomOperator() {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }

    private int calculateExpectedAnswer(int operand1, int operand2, char operator) {
        int answer = 0;
        switch (operator) {
            case '+':
                answer = operand1 + operand2;
                break;
            case '-':
                answer = operand1 - operand2;
                break;
            case '*':
                answer = operand1 * operand2;
                break;
            case '/':
                if (operand2 != 0) {
                    answer = operand1 / operand2;
                } else {
                    answer = Integer.MIN_VALUE; // Indicate division by zero
                }
                break;
        }
        return answer;
    }
}





/*import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

class SystemClock {
    private long currentTime; // 현재 시간을 나타내는 변수

    public SystemClock() {
        this.currentTime = 0; // 초기 시간은 0으로 설정
    }

    public synchronized void tick() {
        currentTime++; // 시간을 1초씩 증가시킴
    }

    public synchronized long getCurrentTime() {
        return currentTime; // 현재 시간 반환
    }
}

public class Server {
    private static final int PORT = 8080; // 서버 포트 번호
    private static SystemClock systemClock = new SystemClock(); // 시스템 시간을 관리하는 객체
    private static long Sum = 0; // 클라이언트의 결과를 누적하는 변수

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 실행 중이며 클라이언트 연결을 기다리고 있습니다...");

            Thread clockThread = new Thread(systemClock::tick); // 시계 스레드
            clockThread.start(); // 시계 스레드 시작

            while (systemClock.getCurrentTime() < 600) { // 현재 시간이 600초 미만인 동안
                Socket clientSocket = serverSocket.accept(); // 클라이언트의 연결을 기다림
                System.out.println("클라이언트 연결됨: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, systemClock);
                new Thread(clientHandler).start(); // 클라이언트 핸들러 스레드 시작
            }

            clockThread.interrupt(); // 시계 스레드 중지
            clockThread.join(); // 시계 스레드 종료 대기

            System.out.println("모든 클라이언트의 연산 결과 누적 합계: " + Sum);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addToTotalSum(long result) {
        Sum += result; // 클라이언트의 결과를 누적
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random = new Random(); // 랜덤 값을 생성하기 위한 객체
    private String problem; // 산술 문제를 저장하는 변수
    private SystemClock systemClock; // 시스템 시간을 관리하는 객체

    public ClientHandler(Socket socket, SystemClock systemClock) {
        this.socket = socket;
        this.problem = generateRandomProblem(); // 랜덤 산술 문제 생성
        this.systemClock = systemClock;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            boolean problemSolved = false;

            while (!problemSolved && systemClock.getCurrentTime() < 600) {
                out.println("다음 산술 문제를 풀어보세요: " + problem);

                long startTime = systemClock.getCurrentTime();

                String clientAnswer = in.readLine();
                int correctAnswer = evaluateProblem(problem);

                long endTime = systemClock.getCurrentTime();
                long calculationTime = (endTime - startTime) * 1000; // 초 단위 변환

                try {
                    int userAnswer = Integer.parseInt(clientAnswer.trim());
                    if (userAnswer == correctAnswer) {
                        out.println("정답입니다!");
                        Server.addToTotalSum(userAnswer); // 결과 누적

                        problem = generateRandomProblem(); // 새로운 문제 생성

                        if (calculationTime < 5000) {
                            Thread.sleep(5000 - calculationTime);
                        }
                    } else {
                        out.println("오답입니다. 다시 시도하세요.");
                    }
                } catch (NumberFormatException | InterruptedException e) {
                    out.println("잘못된 답변 형식입니다. 정수를 입력하세요.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String generateRandomProblem() {
        int num1 = random.nextInt(101);
        int num2 = random.nextInt(101);
        int num3 = random.nextInt(101);

        char operator1 = getOperator(random);
        char operator2 = getOperator(random);

        return num1 + " " + operator1 + " " + num2 + " " + operator2 + " " + num3; // 랜덤 산술 문제 생성
    }

    public char getOperator(Random random) {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)]; // 랜덤 연산자 반환
    }

    public int evaluateProblem(String problem) {
        String[] tokens = problem.split(" ");
        int result = Integer.parseInt(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            char operator = tokens[i].charAt(0);
            int operand = Integer.parseInt(tokens[i + 1]);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    result /= operand;
                    break;
            }
        }

        return result; // 산술 문제를 계산한 결과 반환
    }
}
*/










/*import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 실행 중이며 클라이언트 연결을 기다리고 있습니다...");

            while (true) {
                Socket clientSocket = serverSocket.accept();  // 클라이언트의 연결을 대기하고 클라이언트가 연결되면 소켓을 생성합니다.
                System.out.println("클라이언트 연결됨: " + clientSocket);

                // 클라이언트 처리를 담당할 새 스레드를 생성합니다.
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// 클라이언트를 처리하는 핸들러 클래스입니다.
class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random = new Random();
    private String problem;  // 현재 문제를 저장합니다.

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.problem = generateRandomProblem();  // 랜덤 문제를 생성합니다.
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            boolean problemSolved = false;

            while (!problemSolved) {
                // 클라이언트에게 문제를 보냅니다.
                out.println("다음 산술 문제를 풀어보세요: " + problem);

                String clientAnswer = in.readLine();  // 클라이언트의 답변을 받습니다.
                int correctAnswer = evaluateProblem(problem);  // 정답을 계산합니다.

                // 클라이언트의 답변을 확인하고 결과를 전송합니다.
                try {
                    int userAnswer = Integer.parseInt(clientAnswer.trim());
                    if (userAnswer == correctAnswer) {
                        out.println("정답입니다!");
                    } else {
                        out.println("오답입니다. 다시 시도하세요.");
                    }
                } catch (NumberFormatException e) {
                    out.println("잘못된 답변 형식입니다. 정수를 입력하세요.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 랜덤 문제를 생성합니다.
    private String generateRandomProblem() {
        int num1 = random.nextInt(101);
        int num2 = random.nextInt(101);
        int num3 = random.nextInt(101);

        char operator1 = getOperator(random);
        char operator2 = getOperator(random);

        // 3개 이상의 연산자를 사용하여 문제를 생성합니다.
        return num1 + " " + operator1 + " " + num2 + " " + operator2 + " " + num3;
    }

    // 랜덤 연산자를 반환합니다.
    private char getOperator(Random random) {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }

    // 산술 문제를 평가하여 정답을 계산합니다.
    private int evaluateProblem(String problem) {
        String[] tokens = problem.split(" ");
        int result = Integer.parseInt(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            char operator = tokens[i].charAt(0);
            int operand = Integer.parseInt(tokens[i + 1]);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    result /= operand;
                    break;
            }
        }

        return result;
    }
}*/










/*import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 실행 중이며 클라이언트 연결을 기다리고 있습니다...");

            while (true) {
                Socket clientSocket = serverSocket.accept();  // 클라이언트의 연결을 대기하고 클라이언트가 연결되면 소켓을 생성합니다.
                System.out.println("클라이언트 연결됨: " + clientSocket);

                // 클라이언트 처리를 담당할 새 스레드를 생성합니다.
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// 클라이언트를 처리하는 핸들러 클래스입니다.
class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random = new Random();
    private String problem;  // 현재 문제를 저장합니다.

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.problem = generateRandomProblem();  // 랜덤 문제를 생성합니다.
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            boolean problemSolved = false;

            while (!problemSolved) {
                // 클라이언트에게 문제를 보냅니다.
                out.println("다음 산술 문제를 풀어보세요: " + problem);

                String clientAnswer = in.readLine();  // 클라이언트의 답변을 받습니다.
                int correctAnswer = evaluateProblem(problem);  // 정답을 계산합니다.

                // 클라이언트의 답변을 확인하고 결과를 전송합니다.
                try {
                    int userAnswer = Integer.parseInt(clientAnswer.trim());
                    if (userAnswer == correctAnswer) {
                        out.println("정답입니다!");
                    } else {
                        out.println("오답입니다. 다시 시도하세요.");
                    }
                } catch (NumberFormatException e) {
                    out.println("잘못된 답변 형식입니다. 정수를 입력하세요.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 랜덤 문제를 생성합니다.
    private String generateRandomProblem() {
        int num1 = random.nextInt(101);
        int num2 = random.nextInt(101);
        int num3 = random.nextInt(101);

        char operator1 = getOperator(random);
        char operator2 = getOperator(random);

        // 3개 이상의 연산자를 사용하여 문제를 생성합니다.
        return num1 + " " + operator1 + " " + num2 + " " + operator2 + " " + num3;
    }

    // 랜덤 연산자를 반환합니다.
    private char getOperator(Random random) {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }

    // 산술 문제를 평가하여 정답을 계산합니다.
    private int evaluateProblem(String problem) {
        String[] tokens = problem.split(" ");
        int result = Integer.parseInt(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            char operator = tokens[i].charAt(0);
            int operand = Integer.parseInt(tokens[i + 1]);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    result /= operand;
                    break;
            }
        }

        return result;
    }
}











/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for client connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Generate a random arithmetic problem
            Random random = new Random();
            int operand1 = random.nextInt(10) + 1;
            int operand2 = random.nextInt(10) + 1;
            String[] operators = {"+", "-", "*", "/"};
            String operator = operators[random.nextInt(operators.length)];
            String problem = operand1 + " " + operator + " " + operand2;

            // Send the problem to the client
            out.println("Solve the following arithmetic problem: " + problem);

            String answer = in.readLine();
            try {
                int clientAnswer = Integer.parseInt(answer.trim());

                int correctAnswer;
                switch (operator) {
                    case "+":
                        correctAnswer = operand1 + operand2;
                        break;
                    case "-":
                        correctAnswer = operand1 - operand2;
                        break;
                    case "*":
                        correctAnswer = operand1 * operand2;
                        break;
                    case "/":
                        if (operand2 == 0) {
                            out.println("Invalid problem: Division by zero.");
                            return;
                        }
                        correctAnswer = operand1 / operand2;
                        break;
                    default:
                        out.println("Invalid operator.");
                        return;
                }

                // Check the client's answer and send the result
                if (clientAnswer == correctAnswer) {
                    out.println("Your answer is correct!");
                } else {
                    out.println("Your answer is incorrect. The correct answer is: " + correctAnswer);
                }
            } catch (NumberFormatException e) {
                out.println("Invalid answer format. Please enter an integer.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}*/
