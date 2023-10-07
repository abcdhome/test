package DataTransfer;


import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("서버에 연결되었습니다.");

            String result;
            String problem;

            long startTime = System.currentTimeMillis(); // 클라이언트 시작 시간 기록
            long endTime = startTime + Server.PROGRAM_DURATION_MS; // 클라이언트 종료 시간 설정

            while (System.currentTimeMillis() < endTime) {
                problem = in.readLine();
                String serverTime = in.readLine(); // 서버에서 전송한 시간 받기
                System.out.println("서버: " + problem);

                System.out.print("답변: ");
                String answer = userInput.readLine();
                out.println(answer);

                if ("exit".equalsIgnoreCase(answer)) { // "exit" 입력 시 클라이언트 종료
                    break;
                }

                result = in.readLine();
                System.out.println("서버: " + result);

                // 클라이언트에서 시간을 확인하여 10분이 지나면 종료 메시지 출력
                if (System.currentTimeMillis() >= endTime) {
                    System.out.println("10분이 지나서 자동으로 종료됩니다.");
                    break;
                }
                // 클라이언트에서 시간 로그를 기록
                log("서버 시간: " + serverTime);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 로그를 파일에 기록하는 메서드

    private static void log(String message) {
        try (FileWriter fw = new FileWriter("client_log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}










/*import java.io.*;
import java.net.Socket;


public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // 서버 주소
    private static final int SERVER_PORT = 8080; // 서버 포트 번호
    private static SystemClock systemClock = new SystemClock(); // 시스템 시간을 관리하는 객체

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("서버에 연결되었습니다.");

            String result;
            String problem;

            while (systemClock.getCurrentTime() < 600) {
                problem = in.readLine();
                System.out.println("서버: " + problem);

                long startTime = systemClock.getCurrentTime();

                System.out.print("답변: ");
                String answer = userInput.readLine();
                out.println(answer);

                result = in.readLine();
                System.out.println("서버: " + result);

                long endTime = systemClock.getCurrentTime();
                long calculationTime = (endTime - startTime) * 1000; // 초 단위 변환

                if (result.equals("정답입니다!")) {

                    problem = in.readLine();
                    System.out.println("서버: " + problem);

                    if (calculationTime < 5000) {
                        Thread.sleep(5000 - calculationTime);
                    }

                    System.out.print("답변: ");
                    String nextAnswer = userInput.readLine();
                    out.println(nextAnswer);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}








/*


import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("서버에 연결되었습니다.");

            String result;
            String problem;

            while (true) {
                // 서버로부터 산술 문제를 받아와 출력합니다.
                problem = in.readLine();
                System.out.println("서버: " + problem);

                // 사용자의 답변을 읽고 서버로 전송합니다.
                System.out.print("답변: ");
                String answer = userInput.readLine();
                out.println(answer);

                // 서버로부터 결과를 받아와 출력합니다.
                result = in.readLine();
                System.out.println("서버: " + result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/


    /*public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");

            String result;

            while (true) {
                // Receive and print the arithmetic problem from the server
                String problem = in.readLine();
                System.out.println("Server: " + problem);

                // Read the user's answer and send it to the server
                System.out.print("Your answer: ");
                String answer = userInput.readLine();
                out.println(answer);

                // Receive and print the result from the server
                result = in.readLine();
                System.out.println("Server: " + result);

                // If the answer is correct, receive and print the new problem
                if (result.equals("Your answer is correct!")) {
                    problem = in.readLine();
                    System.out.println("Server (New Problem): " + problem);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/









/*import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");

            String result;

            while (true) {
                // Receive and print the arithmetic problem from the server
                String problem = in.readLine();
                System.out.println("Server: " + problem);

                // Read the user's answer and send it to the server
                System.out.print("Your answer: ");
                String answer = userInput.readLine();
                out.println(answer);

                // Receive and print the result from the server
                result = in.readLine();
                System.out.println("Server: " + result);

                if (result.equals("Your answer is correct!")) {
                    result = in.readLine();
                    System.out.println("Server: " + result);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}  //틀린 문제 재출제하는 코드*/







/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");

            // Receive and print the arithmetic problem from the server
            String problem = in.readLine();
            System.out.println("Server: " + problem);

            // Read the user's answer and send it to the server
            System.out.print("Your answer: ");
            String answer = userInput.readLine();
            out.println(answer);

            // Receive and print the result from the server
            String result = in.readLine();
            System.out.println("Server: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
