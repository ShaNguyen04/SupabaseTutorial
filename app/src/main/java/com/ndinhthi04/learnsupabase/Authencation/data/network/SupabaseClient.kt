package com.ndinhthi04.learnsupabase.Authencation.data.network


import com.ndinhthi04.learnsupabase.BuildConfig
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue

object SupabaseClient {
    @OptIn(SupabaseInternal::class)
    //Client Connect Supabase
    val client = createSupabaseClient(
        supabaseUrl = "https://htebzdbwccrzqaujgued.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imh0ZWJ6ZGJ3Y2NyenFhdWpndWVkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjExMTk5MDAsImV4cCI6MjAzNjY5NTkwMH0.jwK84rDeDtr3-jd5HimipAYZ-7zIBn0axSJzgR4lAYY"
    ){
        //Install GoTrue
        install(GoTrue)
    }
}