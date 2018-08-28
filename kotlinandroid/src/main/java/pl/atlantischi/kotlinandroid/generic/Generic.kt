package pl.atlantischi.kotlinandroid.generic

import java.util.HashMap
import java.util.concurrent.ExecutorService

/**
 * Created on 27/08/2018.

 * @author lx
 *
 *  lambda 参数带泛型结合 typealias 类型别名的使用
 *
 *
 */

class LongNameMessage<T>
enum class CmdType

class Generic1 {

    companion object {

        private val bodySetter = HashMap<CmdType, (requestBody: LongNameMessage<*>, service: ExecutorService) -> Boolean>()

        @Suppress("UNCHECKED_CAST")
        fun <Body : LongNameMessage<*>> addBody(cmdType: CmdType, setter: (requestBody: Body, service: ExecutorService) -> Boolean) {
            bodySetter[cmdType] = setter as (requestBody: LongNameMessage<*>, service: ExecutorService) -> Boolean
        }

    }

}

typealias BodySetter<Body> = (requestBody: Body, service: ExecutorService) -> Boolean

//使用类型别名简化
class Generic2 {

    companion object {

        private val bodySetter = HashMap<CmdType, BodySetter<LongNameMessage<*>>>()

        @Suppress("UNCHECKED_CAST")
        fun <Body : LongNameMessage<*>> addBody(cmdType: CmdType, setter: BodySetter<Body>) {
            bodySetter[cmdType] = setter as BodySetter<LongNameMessage<*>>
        }

    }
}

