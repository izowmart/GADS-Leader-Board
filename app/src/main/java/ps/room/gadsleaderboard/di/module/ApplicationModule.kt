package ps.room.gadsleaderboard.di.module

import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ps.room.gadsleaderboard.BuildConfig
import ps.room.gadsleaderboard.api.ApiHelper
import ps.room.gadsleaderboard.api.ApiHelperImpl
import ps.room.gadsleaderboard.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideBaseUrl() : String{
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    fun provideOKHTTPClient(): OkHttpClient{
        return if (BuildConfig.DEBUG){
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            }else{
                OkHttpClient.Builder()
                    .build()
            }
    }
    @Provides
    @Singleton
    fun provideGson(): Gson{
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson:Gson,okHttpClient: OkHttpClient,baseUrl:String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideApiHelperImpl(apiHelperImpl: ApiHelperImpl):ApiHelper{
        return apiHelperImpl
    }
}