package ETC.P2234_BFS_DFS_Bitmasking

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Math.max
import java.util.*

private val MY = arrayOf(0,-1,0,1) //서(좌) 북(아래) 동(우) 남(위)
private val MX = arrayOf(-1,0,1,0) //서(좌) 북(아래) 동(우) 남(위)

private var N = 0 //가로 //(1 ≤ M, N ≤ 50)
private var M = 0 //세로
private lateinit var map : List<IntArray>
private lateinit var isVisited : List<BooleanArray>
private val Q :Queue<Point> = LinkedList()
private var roomAreaWidth = 1
private val roomAreaWidthList = mutableListOf<Int>()
private var biggestAreaWidth = 1

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2234_BFS_DFS_Bitmasking/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    map = List(M){br.readLine().split(" ").map { it.toInt() }.toIntArray()}
    isVisited = List(M) { BooleanArray(N){false} }

    for (i in 0 until M){
        for (j in 0 until N){
            if (isVisited[i][j] == false){
                Q.add(Point(i, j))
                isVisited[i][j] = true
                bfs()
                roomAreaWidthList.add(roomAreaWidth)
                roomAreaWidth = 1
            }
        }
    }

    for (i in 0 until M){
        for (j in 0 until N){
            clearVisited()
            //벽이 있는 경우만 (비트 연산)
            var k = 1
            for (z in 0 until 4){
                k = (1 shl z) // 1,2,4,8

                if((map[i][j] and k) == k) { //해당 벽을 가지고 있다면
                    isVisited[i][j] = true

                    //벽 없애기
                    map[i][j] = map[i][j] - k

                    Q.add(Point(i, j))
                    bfs()
                    biggestAreaWidth = max(roomAreaWidth, biggestAreaWidth)
                    roomAreaWidth = 1

                    //벽 다시 만들기
                    map[i][j] = map[i][j] + k
                }

            }
        }
    }

    println(roomAreaWidthList.size)
    println(roomAreaWidthList.maxOf { it })
    println(biggestAreaWidth)

    /*
    출력물 :
    이 성에 있는 방의 개수 ==> 기존 DFS, BFS로 구하기 가능
    가장 넓은 방의 넓이 ==> 기존 DFS, BFS로 구하기 가능
    하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기
    ==> 벽을 하나씩 제거한 상태로 DFS 혹은 DFS
    */
}
private fun bfs(){
    while (Q.isNotEmpty()){
        val point = Q.poll()
        var k = 1
        for (i in 0 until 4) {
            k = (1 shl i) // 1,2,4,8 //서,북,동,남
            if ((map[point.y][point.x] and k) != 0) continue // 벽 체크 (벽이 있으면 무시)
            val nextY = point.y + MY[i]
            val nextX = point.x + MX[i]
            //맵 안인가? , 둘 사이에 벽은 없는가? , 가보지 않은 곳인가?
            if (isInMap(nextY,nextX) && (isVisited[nextY][nextX] == false)){
                Q.add(Point(nextY, nextX))
                roomAreaWidth++
                isVisited[nextY][nextX] = true
            }
        }
    }
}

private fun isInMap(y :Int, x :Int) = (y in 0 until M) && (x in 0 until N)

private fun clearVisited(){
    for (i in isVisited.indices){
        for (j in isVisited[i].indices){
            isVisited[i][j] = false
        }
    }
}

data class Point(
    val y :Int,
    val x :Int
)