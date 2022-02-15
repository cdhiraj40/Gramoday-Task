package com.example.gramodaytask.utils

class Constant {
    companion object {
        fun <T> TAG(className: Class<T>?): String? {
            return className?.name
        }
    }
}