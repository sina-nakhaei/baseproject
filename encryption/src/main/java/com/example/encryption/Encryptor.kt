package com.example.encryption

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class Encryptor(private val keyAlias: String) {
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getOrCreateSecretKey(): SecretKey {
        // Check if the key already exists in the Android Keystore
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        return if (keyStore.containsAlias(keyAlias)) {
            val secretKeyEntry = keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
            secretKeyEntry.secretKey
        } else {
            // Generate a new key if it doesn't exist
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun encrypt(value: String): String {
        val secretKey = getOrCreateSecretKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")

        // Initialize the cipher with the secret key
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptionIv = cipher.iv
        val encryptedToken = cipher.doFinal(value.toByteArray(Charsets.UTF_8))

        // Encode to Base64 to make it easier to store or transmit
        val base64Iv = Base64.encodeToString(encryptionIv, Base64.DEFAULT)
        val base64EncryptedToken = Base64.encodeToString(encryptedToken, Base64.DEFAULT)

        return "$base64Iv:$base64EncryptedToken"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun decrypt(encryptedData: String): String {
        val parts = encryptedData.split(":")
        val base64Iv = parts[0]
        val base64EncryptedToken = parts[1]

        val iv = Base64.decode(base64Iv, Base64.DEFAULT)
        val encryptedToken = Base64.decode(base64EncryptedToken, Base64.DEFAULT)

        val secretKey = getOrCreateSecretKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")

        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val decryptedTokenBytes = cipher.doFinal(encryptedToken)
        return String(decryptedTokenBytes, Charsets.UTF_8)
    }
}