package com.example.fooddelieveryapp.models

class Token {

    private var token: String = ""

    companion object {
        private var instance: Token? = null

        fun getInstance(): Token {
            if (instance == null) {
                synchronized(Token::class.java) {
                    if (instance == null) {
                        instance = Token()
                    }
                }
            }
            return instance as Token
        }
    }

    fun setMyAttribute(value: String) {
        token = value
    }

    fun getMyAttribute(): String {
        return token
    }
}