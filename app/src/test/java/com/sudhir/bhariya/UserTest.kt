package com.sudhir.bhariya

import com.sudhir.bhariya.Repository.UserRepository
import com.sudhir.bhariya.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UserTest {
    private lateinit var userRepository: UserRepository

//    Test

    @Test
    fun checkSignup()= runBlocking {
        userRepository= UserRepository()
        val response=userRepository.signupUser(User("9813910902","wangchhu","kapan","1234wang"))
        val expectResult = true
        val actualResult =response.success
        Assert.assertEquals(expectResult,actualResult)
    }

    @Test
    fun checklogin()= runBlocking {
        userRepository= UserRepository()
        val response =userRepository.checkUser("9813910902","1234wang")
        val expectedResult= true
        val actualResult =response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
}