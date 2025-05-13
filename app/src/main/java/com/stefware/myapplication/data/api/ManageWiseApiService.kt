package com.stefware.myapplication.data.api

import com.stefware.myapplication.data.model.Task
import com.stefware.myapplication.data.model.Issue
import com.stefware.myapplication.data.model.Member
import com.stefware.myapplication.data.model.Sprint
import com.stefware.myapplication.data.model.UserStory

import com.stefware.myapplication.data.model.*
import com.stefware.myapplication.ui.meetings.Meeting
import retrofit2.http.*

interface ManageWiseApiService {
    // User Stories
    @GET("user-stories")
    suspend fun getUserStories(): List<UserStory>

    @POST("user-stories")
    suspend fun createUserStory(@Body userStory: UserStory): UserStory

    @PUT("user-stories/{id}")
    suspend fun updateUserStory(@Path("id") id: Int, @Body userStory: UserStory): UserStory

    @DELETE("user-stories/{id}")
    suspend fun deleteUserStory(@Path("id") id: Int)

    // Tasks
    @POST("user-stories/{id}/tasks")
    suspend fun addTask(@Path("id") userStoryId: Int, @Body task: Task): UserStory

    // Sprints
    @GET("sprints")
    suspend fun getSprints(): List<Sprint>

    @POST("sprints")
    suspend fun createSprint(@Body sprint: Sprint): Sprint

    // Issues
    @GET("issues")
    suspend fun getIssues(): List<Issue>

    // Members
    @GET("members")
    suspend fun getMembers(): List<Member>

    // Authentication
    @POST("authentication/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest): SignInResponse

    @POST("authentication/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    // Meetings
    @GET("meetings")
    suspend fun getMeetings(): List<Meeting>

    @POST("meetings")
    suspend fun createMeeting(@Body meeting: Meeting): Meeting

    @PUT("meetings/{id}")
    suspend fun updateMeeting(@Path("id") id: Int, @Body meeting: Meeting): Meeting

    @DELETE("meetings/{id}")
    suspend fun deleteMeeting(@Path("id") id: Int)
}