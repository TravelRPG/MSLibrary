package kr.msleague.message.api

import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

internal object MSMessageRegistry {

    private val adapterMap: MutableMap<String,MutableMap<MSMessageAdapter<*>,Method>> = HashMap()

    internal fun registerAdapters(vararg adapter: MSMessageAdapter<*>) {
        adapter.forEach {
            val superClassIterator = it.javaClass.genericInterfaces.iterator()
            while(superClassIterator.hasNext()) {
                val messageAdapterClass = superClassIterator.next()
                if(messageAdapterClass is ParameterizedType) {
                    if(messageAdapterClass.rawType == MSMessageAdapter::class.java) {
                        val genericIterator = messageAdapterClass.actualTypeArguments.iterator()
                        if(genericIterator.hasNext()) {
                            val generic = genericIterator.next()
                            val set = adapterMap[generic.typeName]?: HashMap<MSMessageAdapter<*>,Method>().apply { adapterMap[generic.typeName] = this }
                            set[it] = it::class.java.getMethod("reformat",String::class.java, Class.forName(generic.typeName))
                        }
                    }
                }
            }
        }
    }

    internal fun reformat(origin: String, vararg objs: Any): String {
        var result = origin
        objs.forEach {
            adapterMap[it::class.java.typeName]
                ?.forEach { (key, value) -> result = value.invoke(key, result, it) as String }
        }
        return result
    }

}