package com.marries.atoolkit.common.reflect

import com.marries.atoolkit.android.ALog
import com.marries.atoolkit.android.TAG
import java.lang.reflect.*

class AReflect private constructor(
    private val clazz: Class<*>, private val obj: Any = clazz
) {

    private constructor(clazz: Class<*>) : this(clazz, clazz)

    companion object {

        @Throws(Exception::class)
        fun withClass(className: String) = try {
            AReflect(Class.forName(className))
        } catch (e: Exception) {
            throw Exception(e)
        }

        @Throws(Exception::class)
        fun withClass(name: String, classLoader: ClassLoader) = try {
            AReflect(Class.forName(name, true, classLoader))
        } catch (e: Exception) {
            throw Exception(e)
        }

        fun withClass(clazz: Class<*>) = AReflect(clazz)
    }

    private fun withClass(obj: Any?) = obj?.let { AReflect(it.javaClass, it) } ?: AReflect(Any::class.java)

    @Throws(Exception::class)
    fun construct(): AReflect {
        return construct(*arrayOf())
    }

    @Throws(Exception::class)
    fun construct(vararg parameters: Any): AReflect {
        val parameterTypes: Array<Class<*>> = types(*parameters)

        return try {
            val constructor: Constructor<*> = clazz.getDeclaredConstructor(*parameterTypes)
            withClass(constructor, *parameters)
        } catch (e: NoSuchMethodException) {
            for (constructor in clazz.declaredConstructors) {
                if (match(constructor.parameterTypes, parameterTypes)) {
                    return withClass(constructor, *parameters)
                }
            }

            throw Exception(e)
        }
    }

    @Throws(Exception::class)
    private fun withClass(constructor: Constructor<*>, vararg args: Any) = try {
        AReflect(constructor.declaringClass, accessible(constructor).newInstance(*args))
    } catch (e: Exception) {
        throw Exception(e)
    }

    @Throws(Exception::class)
    fun invoke(methodName: String) = invoke(methodName, *arrayOf())

    @Throws(Exception::class)
    fun invoke(methodName: String, vararg parameters: Any): AReflect {
        val parameterTypes: Array<Class<*>> = types(*parameters)

        return try {
            invoke(getMethod(methodName, parameterTypes), obj, *parameters)
        } catch (e: NoSuchMethodException) {
            try {
                invoke(getSimilarMethod(methodName, parameterTypes), obj, *parameters)
            } catch (e1: NoSuchMethodException) {
                throw Exception(e1)
            }
        }
    }

    private fun types(vararg parameters: Any): Array<Class<*>> = Array(parameters.size) { i -> parameters[i].javaClass }

    @Throws(Exception::class)
    private fun invoke(method: Method, obj: Any, vararg parameters: Any): AReflect = try {
        accessible(method)
        if (method.returnType == Void.TYPE) {
            method.invoke(obj, *parameters)
            withClass(obj)
        } else {
            withClass(method.invoke(obj, *parameters))
        }
    } catch (e: Exception) {
        throw Exception(e)
    }

    private fun <T : AccessibleObject> accessible(method: T): T {
        if (method is Member) {
            if (Modifier.isPublic(method.modifiers) && Modifier.isPublic(method.declaringClass.modifiers)) {
                return method
            }
        }
        method.isAccessible = true
        return method
    }

    @Throws(NoSuchMethodException::class)
    private fun getMethod(methodName: String, parameters: Array<Class<*>>): Method {
        try {
            return clazz.getMethod(methodName, *parameters)
        } catch (e: NoSuchMethodException) {
            var tempClazz: Class<*>? = clazz
            do {
                try {
                    return tempClazz!!.getDeclaredMethod(methodName, *parameters)
                } catch (e: NoSuchMethodException) {
                    ALog.e(TAG, "exactMethod NoSuchMethodException!", e)
                }

                tempClazz = tempClazz!!.superclass
            } while (tempClazz != null)

            throw NoSuchMethodException()
        }
    }

    @Throws(NoSuchMethodException::class)
    private fun getSimilarMethod(methodName: String, parameterTypes: Array<Class<*>>): Method {
        for (method in clazz.methods) {
            if (isSimilarMethod(method, methodName, parameterTypes)) {
                return method
            }
        }
        var tempClazz: Class<*>? = clazz
        do {
            for (method in tempClazz!!.declaredMethods) {
                if (isSimilarMethod(method, methodName, parameterTypes)) {
                    return method
                }
            }
            tempClazz = tempClazz.superclass
        } while (tempClazz != null)

        throw NoSuchMethodException("No similar method $methodName with params ${parameterTypes.contentToString()} could be found on type $clazz.")
    }

    private fun isSimilarMethod(declaredMethod: Method, desiredMethodName: String, desiredParamTypes: Array<Class<*>>) =
        declaredMethod.name == desiredMethodName && match(declaredMethod.parameterTypes, desiredParamTypes)

    private fun match(declaredTypes: Array<Class<*>>, desiredTypes: Array<Class<*>>): Boolean {
        return if (declaredTypes.size == desiredTypes.size) {
            for (i in desiredTypes.indices) {
                if (desiredTypes[i] == Unit::class.java) continue
                if (wrapper(desiredTypes[i]).isAssignableFrom(wrapper(declaredTypes[i]))) continue
                return false
            }
            true
        } else {
            false
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> wrapper(type: Class<T>): Class<T> {
        if (type.isPrimitive) {
            when (type) {
                Boolean::class.javaPrimitiveType -> return java.lang.Boolean::class.java as Class<T>
                Int::class.javaPrimitiveType -> return java.lang.Integer::class.java as Class<T>
                Long::class.javaPrimitiveType -> return java.lang.Long::class.java as Class<T>
                Short::class.javaPrimitiveType -> return java.lang.Short::class.java as Class<T>
                Byte::class.javaPrimitiveType -> return java.lang.Byte::class.java as Class<T>
                Double::class.javaPrimitiveType -> return java.lang.Double::class.java as Class<T>
                Float::class.javaPrimitiveType -> return java.lang.Float::class.java as Class<T>
                Char::class.javaPrimitiveType -> return java.lang.Character::class.java as Class<T>
                Void.TYPE -> return java.lang.Void::class.java as Class<T>
            }
        }
        return type
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(): T {
        return obj as T
    }
}