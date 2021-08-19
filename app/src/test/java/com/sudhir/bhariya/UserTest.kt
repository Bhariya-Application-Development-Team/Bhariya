package com.sudhir.bhariya

import android.util.JsonReader
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sudhir.bhariya.Repository.UserRepository
import com.sudhir.bhariya.entity.User
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import kotlin.reflect.typeOf


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
    fun geoLocationFare() = runBlocking {
        val jsonResponseLegData = JSONObject("""{
                   "distance" : {
                      "text" : "1.9 km",
                      "value" : 1853
                   },
                   "duration" : {
                      "text" : "6 mins",
                      "value" : 365
                   }
               }""")

        val distance = jsonResponseLegData.getJSONObject("distance")
        val distanceText = distance.getString("text")
        val total_fare = fare(distanceText,3)
        val expectedResult= 2095.0.toDouble()

        val actualResult =total_fare
        Assert.assertEquals(expectedResult,actualResult, 0.001)


    }

    private fun fare(distance: String, labour: Int): Double {
        var total_fare = 0.0
        val km = distance.split(" ")[0]
        total_fare = 500 + (km.toDouble() * 50) + (labour*500)
        return total_fare
    }
}