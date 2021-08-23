package kr.msleague.message.api

import kr.msleague.message.MSMessageLib
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.util.regex.Matcher

internal object MSMessageRegistry {

    private val adapterMap: MutableMap<String, MutableMap<MSMessageAdapter<*>, Method>> = HashMap()

    internal fun registerAdapters(vararg adapter: MSMessageAdapter<*>) {
        adapter.forEach {
            val superClassIterator = it.javaClass.genericInterfaces.iterator()
            while (superClassIterator.hasNext()) {
                val messageAdapterClass = superClassIterator.next()
                if (messageAdapterClass is ParameterizedType) {
                    if (messageAdapterClass.rawType == MSMessageAdapter::class.java) {
                        val genericIterator = messageAdapterClass.actualTypeArguments.iterator()
                        if (genericIterator.hasNext()) {
                            val generic = genericIterator.next()
                            val set = adapterMap[generic.typeName]
                                ?: HashMap<MSMessageAdapter<*>, Method>().apply { adapterMap[generic.typeName] = this }
                            set[it] =
                                it::class.java.getMethod("request", Class.forName(generic.typeName), String::class.java)
                        }
                    }
                }
            }
        }
    }

    internal fun reformat(origin: String, vararg objs: Any): String {
        val matcher: Matcher = MSMessageLib.placeHolder.pattern.matcher(origin)
        val result = StringBuffer()
        while (matcher.find()) {
            var param = matcher.group()
            param = param.substring(1, param.length - 1)
            objs.forEach {
                val iterator = adapterMap[it::class.java.typeName]?.iterator() ?: return@forEach
                while (iterator.hasNext()) {
                    val entry = iterator.next()
                    val temp = entry.value.invoke(entry.key, it, param)
                    if (temp != null) {
                        matcher.appendReplacement(result, temp as String)
                        return@forEach
                    }
                }
            }
        }
        matcher.appendTail(result)
        return result.toString()
    }

}