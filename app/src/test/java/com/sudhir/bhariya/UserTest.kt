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
        val response=userRepository.signupUser(User("9863841998","wangchhu","kapan","wangchhu123"))
        val expectResult = true
        val actualResult =response.success
        Assert.assertEquals(expectResult,actualResult)
    }

    @Test
    fun loginCheck()= runBlocking {
        userRepository= UserRepository()
        val response =userRepository.checkUser("9863841998","wangchhu123")
        val expectedResult= true
        val actualResult =response.success
        Assert.assertEquals(expectedResult,actualResult)
    }

    @Test
    fun profileTest()= runBlocking {
        userRepository= UserRepository()
        val response=User("9863841998","wangchhu","kapan","wangchhu123")
        ServiceBuilder.token="Bearer"+userRepository.checkUser("9863841998","wangchhu123").token
        val expectedResult= "9863841998"
        val actualResult =response.Phonenumber
        Assert.assertEquals(expectedResult,actualResult)
    }

    @Test
    fun editprofileTest()= runBlocking {
        userRepository= UserRepository()
        val response = userRepository.updateUserText("9821455357","GauravTest","Manipal","9821455350")
        val expectedResult= true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
}