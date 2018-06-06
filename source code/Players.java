package com.company;

//2015410029 컴퓨터학과 김진희

public class Players {
    final int Black = 0;                                // 오목판에 돌을 놓을 경우 Black player는 0, White plyaer는 1의 값을 가짐
    final int White = 1;
    public String winner;                               // 게임이 끝났을 경우 승리자를 나타내는 변수

    Players () {
    }

    public void setWinner(int player) {                 // 승리자가 결정되었을 경우 설정
        if (player == Black)
            winner = "컴퓨터(Black)";
        else if (player == White)
            winner = "사용자(White)";
    }
}
