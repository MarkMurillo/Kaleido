package com.example.kaleido.data.modules

import android.content.Context
import com.example.kaleido.R
import com.example.kaleido.data.AppData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /**
     * Any dependencies that require a context object can use
     * hilt's @ApplicationContext annotation which will automatically grab
     * the application context.
     */
    @Provides
    @Named("AppName") // We can name our dependencies for easier reference
    fun provideAppName(
        @ApplicationContext context: Context
    ) = context.getString(R.string.app_name)

    @Provides
    @Named("Sentence")
    fun provideSentence(
        @Named("AppName") appName: String
    ) = "The app name is $appName"

    @Provides
    @Named("SecondActivity")
    fun provideSecondActivityLabel() = "This is the second activity"

    @Provides
    @Named("SecondFragment")
    fun provideSecondFragmentLabel() = "This is the second fragment"

    /**
     * We need to add the @Singleton annotation if we want
     * to retain the same object throughout the app.
     */
    @Provides
    @Singleton
    fun provideAppData(): AppData = AppData()
}