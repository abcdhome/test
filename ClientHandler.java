package DataTransfer;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random = new Random();
    private String problem;
    private int clientNumber; // 클라이언트 번호
    private static int totalSum = 0;

    // 클라이언트별로 로그 파일을 생성하도록 수정
    private PrintWriter clientLogFile;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.problem = generateRandomProblem();

        try {
            // 클라이언트별로 로그 파일 생성 (클라이언트 번호로 구분)
            clientLogFile = new PrintWriter("client" + clientNumber + "_log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 클라이언트 종료 시 로그 파일을 닫아야 함
    private void closeClientLogFile() {
        if (clientLogFile != null) {
            clientLogFile.close();
        }
    }

    // 클라이언트 핸들러 객체 종료 시 로그 파일을 닫음
    private void cleanup() {
        closeClientLogFile();
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

                // 클라이언트에게 문제 전송
                out.println("클라이언트 " + clientNumber + ", 다음 산술 문제를 풀어보세요: " + problem);
                out.println("현재 시간 (System Clock): " + currentTime);

                String clientAnswer = in.readLine();
                int correctAnswer = Problem(problem);

                try {
                    int userAnswer = Integer.parseInt(clientAnswer.trim());
                    if (userAnswer == correctAnswer) {
                        out.println("클라이언트 " + clientNumber + ", 정답입니다!");

                        // 정답을 합산
                        totalSum += userAnswer;

                        // 새로운 문제 생성
                        problem = RandomProblem();
                    } else {
                        out.println("클라이언트 " + clientNumber + ", 오답입니다. 다시 시도하세요.");
                    }

                    // 클라이언트별로 로그 파일에 로그 기록
                    clientLogFile.println("클라이언트 " + clientNumber + " - 문제: " + problem + ", 답변: " + userAnswer);

                } catch (NumberFormatException e) {
                    out.println("클라이언트 " + clientNumber + ", 잘못된 답변 형식입니다. 정수를 입력하세요.");
                }
            }
        } catch (IOException e) {
            // 클라이언트 연결이 끊겼을 때 합계 출력
            System.out.println("클라이언트 " + clientNumber + " 연결 끊김, totalSum = " + totalSum);
        } finally {
            // 클라이언트 핸들러 종료 시 로그 파일을 닫음
            cleanup();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String RandomProblem() {
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

    private int Problem(String problem) {
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
