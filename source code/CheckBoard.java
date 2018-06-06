package com.company;

//2015410029 컴퓨터학과 김진희

public class CheckBoard {
    static int num_2 = 0, num_3 = 0, num_4 = 0, num_5 = 0;                                 // 2, 3, 4, 5만큼 연속적으로 놓인 돌돌의 개수

    public static int[] checkState (Board b, int player) {                               //오목판에 놓인 돌의 개수를 파악하는 함수
        for (int i=0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                checkRow(b, player, i, j);                                                 //가로로 놓인 돌 파악
                checkColumn(b, player, i, j);                                              //세로로 놓인 돌 파악
                checkL2R(b, player, i, j);                                                 //왼쪽 위에서 오른쪽 아래를 향하는 대각선으로 놓인 돌 파악
                checkR2L(b, player, i, j);                                                 //오른쪽 위에서 왼쪽 아래를 향하는 대각선으로 놓인 돌 파악
            }
        }
        int[] rslt = {num_2, num_3, num_4, num_5};
        num_2 = num_3 = num_4 = num_5 = 0;
        return rslt;
    }

    public static int checkState (Board b, int player, int num2check) {                  // num2check만큼 연속적으로 놓인 돌들의 개수만 return
        return checkState(b, player)[num2check-2];
    }

    public static void checkRow (Board b, int player, int x, int y) {
        int count = 0;
        for (int i = 0; i < 5; i++){
            if(y + i > 18)
                return;
            if(b.board[x][y+i] == (player+1)%2)
                return;
            if (b.board[x][y+i] == player)
                ++count;
        }
        switch(count) {
            case 2: num_2++; break;
            case 3: num_3++; break;
            case 4: num_4++; break;
            case 5: num_5++; break;
        }
    }

    public static void checkColumn (Board b, int player, int x, int y) {
        int count = 0;
        for (int i = 0; i < 5; i++){
            if (x + i > 18)
                return;
            if (b.board[x+i][y] == (player+1)%2)
                return;
            if (b.board[x+i][y] == player)
                ++count;
        }
        switch(count) {
            case 2: num_2++; break;
            case 3: num_3++; break;
            case 4: num_4++; break;
            case 5: num_5++; break;
        }
    }

    public static void checkL2R (Board b, int player, int x, int y)
    {
        int count=0;
        for (int i = 0, j = 0; i < 5 && j < 5; i++, j++) {
            if (x+i > 18 || y+j > 18)
                return;
            if (b.board[x+i][y+i] == (player+1)%2)
                return;
            if (b.board[x+i][y+i] == player)
                ++count;
        }

        switch(count) {
            case 2: num_2++; break;
            case 3: num_3++; break;
            case 4: num_4++; break;
            case 5: num_5++; break;
        }
    }


    public static void checkR2L (Board b, int player, int x, int y) {
        int count = 0;
        for (int i = 0, j = 0; i < 5 && j < 5; ++i, ++j) {
            if (x + i > 18 || y - j < 0)
                return;
            if (b.board[x + i][y - j] == (player+1)%2)
                return;
            if (b.board[x + i][y - j] == player)
                ++count;
        }

        switch(count) {
            case 2: num_2++; break;
            case 3: num_3++; break;
            case 4: num_4++; break;
            case 5: num_5++; break;
        }
    }
}
