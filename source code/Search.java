package com.company;

//2015410029 컴퓨터학과 김진희

import java.util.ArrayList;

public class Search {                                                                    // Alpha-Beta Pruning with Iterative Deepening Search
    final static long timeLimit = 30000;                                               // 한번 돌을 놓을 때 30초의 시간제한을 가짐
    public static long startTime;                                                       // search를 시작했을 때의 시간

    public static boolean timeout () {                                                 // search를 처음 시작할 때 설정한 startTime을 기준으로 시간 제한을 넘겼는지를 확인
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= (timeLimit - 1000))
            return true;
        return false;
    }

    public static boolean cutoff_test (int depth, ArrayList<int[]> possible_move) {   // Iterative deepening으로 제한한 depth를 넘겼는지, 오목판이 다 차지 않았는지, 시간이 제한이 다 되었는지 확인
        if (depth == 0 || possible_move.isEmpty() || timeout())
            return true;
        return false;
    }

    public static Object[] ID_search (Board b, Players pls) {                                     // Iterative Deepening Search
        startTime = System.currentTimeMillis();
        Object[] nextMove = null;

        for (int depth = 1; !timeout() && !b.isFull(); ++depth)                                   // 제한 시간까지 depth를 늘려가며 반복적으로 search
            nextMove = AB_pruning_search(depth, b, pls);                                           // depth만큼 Alpha Beta pruning search를 함
        return nextMove;                                                                          // 최종적으로 결정된 돌을 놓을 좌표 return
    }

    public static Object[] AB_pruning_search (int depth, Board b, Players pls) {                                           //Alpha Beta pruning Search
        Object[] nextMove = Max_value_move(depth, b, pls, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);          //컴퓨터가 Max player(alpha), 사용자가 Min player(beta)
        return nextMove;                                                                                                    // 주어진 depth만큼 search하여 결정된 돌을 놓을 좌표 return
    }

    public static Object[] Max_value_move (int depth, Board b, Players pls, double alpha, double beta) {                 //Max player의 돌을 놓고 alpha와 돌을 놓을 좌표를 update
        ArrayList<int[]> tryMove = b.near_possible_move(b.latest_move[0], b.latest_move[1]);                            // 상대가 가장 최근에 놓은 돌을 기점으로 자신이 놓을 수 있는 돌의 위치들 파악
        if (cutoff_test(depth, tryMove)) {                                                                                 //cutoff가 되었다면 현재 오목판에서 자신이 놓은 돌과 그 오목판의 eval_value를 return
            Object[] rslt = {b.prev_move, eval_func(b, pls)};
            return rslt;
        }
        Object[] rslt = {b.prev_move, Double.NEGATIVE_INFINITY};
        while (tryMove.size() > 0){                                                                                        //tryMove에 저장되어있는 자신이 돌을 놓을 수 있는 위치에 모두 돌을 놓음
            Board temp_board = b.copy();                                                                                   //받아온 오목판을 복사하여 임시로 이용
            int[] move = tryMove.get(0);
            tryMove.remove(0);
            temp_board.move(pls.Black, move[0], move[1]);

            rslt[1] = Math.max((double)rslt[1], (double)Min_value_move (depth - 1, temp_board, pls, alpha, beta)[1]);   //돌을 놓은 오목판을 Min_value_move 함수에 넘겨주어 Min이 돌을 놓을 수 있게 함
            if ((double)rslt[1] >= beta)                                                                                // 그 결과로 돌아온 evaluation value가 beta보다 크면 pruning
                return rslt;
            if (alpha < (double)rslt[1]) {                                                                              // evaluation value가 alpha보다 크면
                alpha = (double)rslt[1];                                                                               // alpha와 자신이 돌을 놓을 가장 좋은 좌표를 update
                rslt[0] = move;
            }
        }
        return rslt;
    }

    public static Object[] Min_value_move (int depth, Board b, Players pls, double alpha, double beta) {              //Min player의 돌을 놓고 beta와 돌을 놓을 좌표를 update
        ArrayList<int[]> tryMove = b.near_possible_move(b.latest_move[0], b.latest_move[1]);
        if (cutoff_test(depth, tryMove)) {
            Object[] rslt = {b.prev_move, eval_func(b,pls)};
            return rslt;
        }
        Object[] rslt = {b.prev_move, Double.POSITIVE_INFINITY};
        while (tryMove.size() > 0){
            Board temp_board = b.copy();
            int[] move = tryMove.get(0);
            tryMove.remove(0);
            temp_board.move(pls.White, move[0], move[1]);

            rslt[1] = Math.min((double)rslt[1], (double)Max_value_move (depth - 1, temp_board, pls, alpha, beta)[1]);
            if ((double)rslt[1] <= alpha)
                return rslt;
            if (beta > (double)rslt[1]) {
                beta = (double)rslt[1];
                rslt[0] = move;
            }
        }
        return rslt;
    }

    public static double eval_func(Board b, Players pls) {
        int[] stB = CheckBoard.checkState(b, pls.Black);            //CheckBoard.checkState() 함수는 연속으로 돌이 2개, 3개, 4개, 5개 놓인 수를 세어 각각 배열에 그 겂을 저장하여 return
        int[] stW = CheckBoard.checkState(b, pls.White);
                                                                     //연속적으로 돌이 놓여있는 개수가 클수록 더 큰 weight를 부여하여 evaluation value를 결정
        double eval_val = stB[0] * 50 + stB[1] * 150 + stB[2] * 1000 + stB[3] * 20000 - stW[1] * 10000 - stW[2] * 15000 - stW[3] * 20000;
        return eval_val;
    }
}
