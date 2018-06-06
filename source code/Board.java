package com.company;

//2015410029 컴퓨터학과 김진희

import java.util.ArrayList;

public class Board {
    public int setNumber = 0;                                                             // 오목판에 돌이 몇개 놓여있는지 trace
    public int[][] board = new int[19][19];                                               // 19x19 오목판 생성
    public int[] prev_move = new int[2];                                                 // 오목판에 두번째로 가장 최근에 놓인 돌 (호출한 player가 가장 최근에 놓은 돌)
    public int[] latest_move = new int[2];                                               // 오목판에 가장 최근에 놓인 돌 (호출한 player의 상대가 가장 최근에 놓은 돌)

    Board () {                                                                              // 오목판을 초기화(-1)
        for (int i = 0; i < 19; i++)                                                       // 돌을 놓을 경우 player에 따라 해당 좌표에 Black, White 값이 채워짐
            for (int j = 0; j < 19; j++)
                board[i][j] = -1;
    }

    public int[] move (Players p, int pls) {
        int row = this.latest_move[0] , column = this.latest_move[1];
        if (this.isEmpty())  {                                                            // 게임 시작 후 처음 돌을 놓을 경우 정중앙에 돌을 놓음
            row = column = 9;
        }
        else {
            Object[] nextMove = Search.ID_search(this, p);                               // 그 외의 경우에는 ID_search함수로 돌을 놓을 곳의 위치를 찾음
            if (nextMove != null) {
                row = ((int[]) nextMove[0])[0];
                column = ((int[]) nextMove[0])[1];
            }
        }
        this.prev_move = latest_move;
        this.latest_move[0] = row;
        this.latest_move[1] = column;
        this.add(pls, row, column);                                                      // 오목판에 찾아온 위치 좌표대로 돌을 놓음
        return latest_move;                                                            // 놓은 돌의 좌표를 return
    }

    public int[] move (int pls, int row, int column) {                                // 받아온 row와 column의 좌표대로 돌을 놓음
        this.add(pls, row, column);
        this.prev_move = latest_move;
        this.latest_move[0] = row;
        this.latest_move[1] = column;
        return latest_move;
    }

    public ArrayList<int[]> near_possible_move (int row, int column) {                 // 받아온 좌표 근처의 돌을 놓을 수 있는 좌표들을 찾아냄
        ArrayList<int[]> rslt = new ArrayList<int[]>();

        for (int k = -5; k < 6; k++) {                                                  // 받아온 좌표를 기준으로 모든 방향으로 5칸 떨어진 곳까지 고려
            for (int x = -5; x < 6; x++) {
                if (row-k >= 0 && column-x >= 0 && row-k < 19 && column-x < 19 && !(k == 0 && x == 0)) {
                    if (board[row - k][column - x] == -1) {
                        int[] temp = {row - k, column - x};
                        rslt.add(temp);
                    }
                    else if (row - k+1 >=0 && row - k+1 < 19 && board[row - k+1][column - x] == -1 && !(k == 1 && x == 0)) {    // 고려한 좌표에 이미 돌이 놓여져있을 경우 바로 옆의 좌표를 한번 더 고려
                        int[] temp = {row - k+1, column - x};
                        rslt.add(temp);
                    }
                    else if (column-x+1 >=0 && column-x+1 < 19 && board[row - k][column - x + 1] == -1 && !(k == 0 && x == 1)) {
                        int[] temp = {row - k, column - x + 1};
                        rslt.add(temp);
                    }
                }
            }
        }
        if (rslt.isEmpty())                                                              // 받아온 좌표 주변에서 돌을 놓을 수 있는 곳을 못찾았다면 all_possible_move로 다른 모든 가능한 좌표들 받아옴
            rslt = this.all_possible_move();
        return rslt;
    }

    public ArrayList<int[]> all_possible_move() {                                       // 오목판에 돌을 놓을 수 있는 모든 좌표들을 찾음
        ArrayList<int[]> rslt = new ArrayList<int[]>();;
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 19; j++)
                if (board[i][j] == -1) {
                    int[] temp = {i, j};
                    rslt.add(temp);
                }
        return rslt;
    }

    public boolean gameOver (Players p) {                                               // 게임이 끝났는지 체크
        if (CheckBoard.checkState(this, p.Black, 5) != 0) {                             // 게임이 끝났다면 승리자를 설정
            p.setWinner(p.Black);
            return true;
        }
        else if (CheckBoard.checkState(this, p.White, 5) != 0) {
            p.setWinner(p.White);
            return true;
        }
        return false;
    }

    public void add (int player, int row, int column) {                               // 주어진 좌표에 해당 player의 돌을 놓음
        board[row][column] = player;
        ++setNumber;
    }

    public Board copy () {                                                             // 현재 오목판을 복사해서 return
        Board copied = new Board();
        copied.setNumber = this.setNumber;
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 19; j++)
                copied.board[i][j] = this.board[i][j];
        return copied;
    }

    public boolean isEmpty () {                                                        // 현재 오목판이 비어있는지 확인
        if (setNumber == 0)
            return true;
        else
            return false;
    }

    public boolean isFull () {                                                         // 현재 오목판이 다 찼는지 확인
        if (setNumber == 19*19)
            return true;
        else
            return false;
    }

}
