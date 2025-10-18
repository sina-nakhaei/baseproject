package ir.thesinaa.qrcodereader.di.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.thesinaa.qrcodereader.di.QrCodeReader
import ir.thesinaa.qrcodereader.di.QrCodeReaderDefault
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface Module {
    @Binds
    @Singleton
    fun bindQrCodeReader(q: QrCodeReaderDefault): QrCodeReader
}