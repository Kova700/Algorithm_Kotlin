package DAY02.P1072

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P1072/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (X,Y) = br.readLine().split(" ").map { it.toLong() }

    //시간 제한 2초 ( 선형탐색 하면 바로 초과 ) ==> 이분탐색 하자.
    //X : 게임 횟수 (1~10억), Y : 이긴 게임 횟수 (0~X)
    //Z : 승률(소수점 버림)
    //앞으로 모든 게임은 이긴다 가정 (== X, Y같이 증가)
    // "최소" 몇번을 더 해야 Z가 변하는가
    var answer :Long = Long.MAX_VALUE
    val currentRate :Long =(Y*100) / X
    var findAnswer = false

    if(currentRate == 99L || currentRate == 100L){
        println(-1)
        return
    }

    //승률이 변화는 곳의 최저치를 찾자
    var highPointer :Long = 1_000_000_000 ; var lowPointer :Long = 0
    while (true){
        val mid :Long = (highPointer + lowPointer) / 2
        val tempRate :Long =(Y+mid)*100 / (X+mid)

        //이렇게 풀면 안됨 (Rate 는 항상 1씩 커지지 않기 때문에
        //문제에서 물어봤던 Rate를 올리려면 몇판을 해야할까에서 목표 Rate는 Rate+1이 아니라
        //승률이 현재 승률보다 높은 승률중에 최소값을 찾아야 하는 것이다.

        //승률이 목표 승률(현재 승률 +1) 보다 클 때
        if(tempRate > currentRate+1) {
            println("highPointer : $highPointer , lowPointer : $lowPointer, mid : $mid , tempRate : $tempRate")
            findAnswer = true
            answer =mid
            highPointer = mid
            println("highPointer 아래로 내림 : $highPointer , lowPointer : $lowPointer")
            //승률이 목표 승률(현재 승률 +1)과 같을 때
        }else if(tempRate == currentRate+1){
            findAnswer = true
            answer =mid
            highPointer = mid
            println("answer 갱신 : $answer lowPointer :$lowPointer , highPointer :$highPointer ,tempRate : $tempRate")
            //승률이 목표 승률(현재 승률 +1)보다 낮을 때 == 현재 승률과 같을 때
        }else{
            println("lowPointer : $lowPointer , highPointer : $highPointer , mid : $mid , tempRate : $tempRate")
            lowPointer = mid + 1
            println("lowPointer 위로 올림 : $lowPointer , highPointer : $highPointer")
        }
        //탈출 조건
        if (highPointer == lowPointer) break

    }
    //승률이 절대 변하지 않는다면 -1을 출력
    if(findAnswer == false) println(-1)
    else println(answer)
}