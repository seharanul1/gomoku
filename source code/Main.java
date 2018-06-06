package com.company;
import java.util.Scanner;

//2015410029 컴퓨터학과 김진희

public class Main {

    public static void main(String[] args) {
        Board board = new Board();                                                                                 // 오목판 생성
        Players pls = new Players();                                                                              // player 생성 : 컴퓨터는 Max player(Black), 사용자는 Min player(White)
        int turn = pls.Black;                                                                                    // 돌을 놓을 차례를 지정. 처음에는 컴퓨터 차례

        while (!board.isFull() && !board.gameOver(pls)) {                                                        // 승자가 정해지거나 오목판이 다찰때까지 번갈아 돌을 놓음
            if (turn == pls.Black) {
                int[] rslt = board.move(pls, pls.Black);                                                        // move 함수로 돌을 놓을 위치 좌표를 받아옴
                System.out.printf("컴퓨터가 돌을 놓은 자리는 (%d, %d) 입니다.\n", rslt[0], rslt[1]);         // 컴퓨터의 경우 자신이 놓은 돌의 위치 좌표를 출력
                turn = (turn+1)%2;                                                                               // 차례를 상대에게 넘김
            }
            else {
                Scanner input = new Scanner(System.in);
                int[] coord_get = new int[2];
                System.out.println("돌을 놓을 위치를 입력하십시오. ex. 3, 5");
                for (int n = 0; n < 2;) {                                                                        // 사용자의 경우 돌을 놓을 위치 좌표를 받아옴
                    int c = input.nextInt();
                    if (c >= 0 && c < 19) {
                        coord_get[n++] = c;
                    } else {
                        System.out.println("잘못 입력하셨습니다. 위치를 다시 입력하십시오.");
                        n = 0;
                    }
                    if (n == 2 && board.board[coord_get[0]][coord_get[1]] != -1) {
                        System.out.println("이미 돌이 놓여있는 자리입니다. 위치를 다시 입력하십시오.");
                        n = 0;
                    }
                }
                board.move(pls.White, coord_get[0], coord_get[1]);
                turn = (turn+1)%2;
            }
            for (int i = 0;  i < 19; i++) {                                                                        // 오목판 출력
                for (int j = 0; j < 19; j++) {
                    String c = "";
                    switch (board.board[i][j]) {
                        case -1:                                                                                  // 빈 곳은 . 으로 표시
                            c = ".";
                            break;
                        case 0:                                                                                   // Black player는 o로 표시
                            c = "o";
                            break;
                        case 1:
                            c = "x";                                                                             // White player는 x로 표시
                            break;
                    }
                    System.out.print(c + " ");
                }
                System.out.println();
            }
            System.out.println();
        }


        if (board.gameOver(pls))                                                                                // 게임이 끝나면 결과 출력
            System.out.println(pls.winner + " 의 승리입니다.");
        else
            System.out.println("무승부입니다.");
    }

}
