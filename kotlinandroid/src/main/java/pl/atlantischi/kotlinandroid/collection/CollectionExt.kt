package pl.atlantischi.kotlinandroid.collection

import java.util.*

/**
 * Created on 29/09/2018.

 * @author lx
 *
 *  https://blog.csdn.net/al4fun/article/details/74356229
 */


fun create() {

    val list1 = listOf<Int>()           //返回的是kotlin的List，只读
    val list2 = mutableListOf<Int>()    //返回的是kotlin的MutableList，可读写
    val list3 = arrayListOf<Int>()      //返回的是java的ArrayList，java的ArrayList是可读写的

    setOf<Int>()
    mutableSetOf<Int>()
    hashSetOf<Int>()

    mapOf<String, Int>()
    mutableMapOf<String, Int>()
    hashMapOf<String, Int>()

    val arrayList = ArrayList<Int>()
    val linkedList = LinkedList<String>()
}

fun common() {
    val items = listOf(1, 2, 3, 4)
    print(items.first())
    print(items.last())
    //filter接受一个函数为参数，函数类型为“(T) -> Boolean”
    //filter会将集合中的每一个元素分别传入该函数，如果函数返回true，则保留该元素
    items.filter { it % 2 == 0 } // 返回 [2, 4]

    val rwList = mutableListOf(1, 2, 3)
    //返回集合中非null的元素，这里会返回[1, 2, 3]
    rwList.requireNoNulls()
    //rwList.none { it > 6 }:集合中是否没有大于6的元素
    if (rwList.none { it > 6 }) println("No items above 6") //输出“No items above 6”
    //返回集合中的第一个元素，如果集合为空则返回null
    val item = rwList.firstOrNull()

    val readWriteMap = hashMapOf("foo" to 1, "bar" to 2)
    println(readWriteMap["foo"]) //输出“1”

}

fun condition() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    //只要集合中的任何一个元素满足条件(使得lambda表达式返回true)，any函数就返回true
    list.any {
        it >= 0
    }

    //集合中的全部元素都满足条件(使得lambda表达式返回true)，all函数才返回true
    list.all {
        it >= 0
    }

    //若集合中没有元素满足条件（使lambda表达式返回true），则none函数返回true
    list.none {
        it < 0
    }

    //count函数的返回值为：集合中满足条件的元素的总数
    list.count {
        it >= 0
    }
}

fun sum() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    //对所有元素求和
    list.sum()

    //将集合中的每一个元素代入lambda表达式，然后对lambda表达式的返回值求和
    list.sumBy {
        it % 2
    }

    //在一个初始值的基础上,从第一项到最后一项通过一个函数累计所有的元素
    //accumulator的初始值为100，element从集合的第一个元素开始，lambda表达式的返回值就是accumulator的新值
    list.fold(100) { accumulator, element ->
        accumulator + element / 2
    }

    //同fold，只是迭代的方向相反
    list.foldRight(100) { accumulator, element ->
        accumulator + element / 2
    }

    //同fold，只是accumulator的初始值就是集合的第一个元素，element从第二个元素开始
    list.reduce { accumulator, element ->
        accumulator + element / 2
    }

    //同reduce但方向相反：accumulator的初始值就是集合的最后一个元素，element从倒数第二个元素开始往前迭代
    list.reduceRight { accumulator, element ->
        accumulator + element / 2
    }
}

fun max_min() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    //返回集合中最大的元素，集合为空(empty)则返回null
    list.max()

    //返回集合中使得lambda表达式返回值最大的元素，集合为空(empty)则返回null
    list.maxBy { it }

    //返回集合中最小的元素，集合为空(empty)则返回null
    list.min()

    //返回集合中使得lambda表达式返回值最小的元素，集合为空(empty)则返回null
    list.minBy { it }
}

fun filter() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    //返回一个新List，去除集合的前n个元素
    list.drop(2)
    //返回一个新List，去除集合的后n个元素
    list.dropLast(2)
    //返回一个新List，去除集合中满足条件(lambda返回true)的第一个元素
    list.dropWhile {
        it > 3
    }
    //返回一个新List，去除集合中满足条件(lambda返回true)的最后一个元素
    list.dropLastWhile {
        it > 3
    }


    //返回一个新List，包含前面的n个元素
    list.take(2)
    //返回一个新List，包含最后的n个元素
    list.takeLast(2)
    //返回一个新List，仅保留集合中满足条件(lambda返回true)的第一个元素
    list.takeWhile {
        it > 3
    }
    //返回一个新List，仅保留集合中满足条件(lambda返回true)的最后一个元素
    list.takeLastWhile {
        it > 3
    }


    //返回一个新List，仅保留集合中满足条件(lambda返回true)的元素，其他的都去掉
    list.filter {
        it > 3
    }
    //返回一个新List，仅保留集合中不满足条件的元素，其他的都去掉
    list.filterNot {
        it > 3
    }
    //返回一个新List，仅保留集合中的非空元素
    list.filterNotNull()


    //返回一个新List，仅保留指定索引处的元素
    list.slice(listOf(0, 1, 2))
}

fun mapping() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    //将集合中的每一个元素代入lambda表达式，lambda表达式必须返回一个元素
    //map的返回值是所有lambda表达式的返回值所组成的新List
    //例如下面的代码和listOf(2,4,6,8,10,12)将产生相同的List
    list.map {
        it * 2
    }

    //和map一样，只是lambda表达式的参数多了一个index
    list.mapIndexed { index, it ->
        index * it
    }

    //和map一样，只不过只有lambda表达式的非空返回值才会被包含在新List中
    list.mapNotNull {
        it * 2
    }

    //将集合中的每一个元素代入lambda表达式，lambda表达式必须返回一个集合
    //而flatMap的返回值是所有lambda表达式返回的集合中的元素所组成的新List
    //例如下面的代码和listOf(1,2,2,3,3,4,4,5,5,6,6,7)将产生相同的List
    list.flatMap {
        listOf(it, it + 1)
    }

    //根据lambda表达式对集合元素进行分组，返回一个Map
    //lambda表达式的返回值就是map中元素的key
    //例如下面的代码和mapOf("even" to listOf(2,4,6),"odd" to listOf(1,3,5))将产生相同的map
    list.groupBy {
        if (it % 2 == 0) "even" else "odd"
    }

}

fun element() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    list.contains(2)

    list.elementAt(0)
    //返回指定索引处的元素，若索引越界，则返回null
    list.elementAtOrNull(10)
    //返回指定索引处的元素，若索引越界，则返回lambda表达式的返回值
    list.elementAtOrElse(10) { index ->
        index * 2
    }

    //返回list的第一个元素
    list.first()
    //返回list中满足条件的第一个元素
    list.first {
        it > 1
    }
    //返回list的第一个元素,list为empty则返回null
    list.firstOrNull()
    //返回list中满足条件的第一个元素，没有满足条件的则返回null
    list.firstOrNull {
        it > 1
    }

    list.last()
    list.last { it > 1 }
    list.lastOrNull()
    list.lastOrNull { it > 1 }

    //返回元素2第一次出现在list中的索引，若不存在则返回-1
    list.indexOf(2)
    //返回元素2最后一次出现在list中的索引，若不存在则返回-1
    list.lastIndexOf(2)
    //返回满足条件的第一个元素的索引
    list.indexOfFirst {
        it > 2
    }
    //返回满足条件的最后一个元素的索引
    list.indexOfLast {
        it > 2
    }

    //返回满足条件的唯一元素，如果没有满足条件的元素或满足条件的元素多于一个，则抛出异常
    list.single {
        it == 5
    }
    //返回满足条件的唯一元素，如果没有满足条件的元素或满足条件的元素多于一个，则返回null
    list.singleOrNull {
        it == 5
    }
}

fun merge_decompose() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val list2 = listOf(5, 6, 7, 8, 9, 0)

    //拼接两个集合，返回一个新集合
    //本质是调用list.plus(list2)
    list + list2

    //把一个给定的集合分割成两个list，一个list中是代入lambda表达式后返回true的元素，另一个list中是代入后返回false的元素
    val (list3, list4) = list.partition {
        it % 2 == 0
    }

    //返回一个由pair组成的list，每一个pair由两个集合中相同index的元素组成
    val pairList: List<Pair<Int, Int>> = list.zip(list2)

    //unzip与zip的功能相反，将一个由pair组成的list分解成两个list
    val (list5, list6) = pairList.unzip()
}

fun sort() {
    val list = listOf(1, 2, 3, 4, 5, 6)

    //返回一个颠倒元素顺序的新集合
    list.reversed()

    /**
     * 返回一个升序排序后的新集合
     */
    list.sorted()
    //将每个元素代入lambda表达式，根据lambda表达式返回值的大小来对集合进行排序
    list.sortedBy {
        it * 2
    }

    /**
     * 返回一个降序排序后的新集合
     */
    list.sortedDescending()
    list.sortedByDescending {
        it * 2
    }
}