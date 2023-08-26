package com.platovco.repetitor.managers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.platovco.repetitor.models.StudentAccount
import com.platovco.repetitor.models.TutorAccount
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.ArrayList
import java.util.function.BiConsumer
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

object AppwriteManager {

    suspend fun addTutorAccount(tutorAccount: TutorAccount) {
        val client = AppwriteClient.getClient()
        val databases = Databases(client)
        val uuid = getAccount().id
        try {
            databases.createDocument(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64a8452b80905db4197c",
                documentId = uuid,
                data = mapOf(
                    "Full_name" to tutorAccount.name,
                    "Edication" to tutorAccount.high,
                    "Photo" to tutorAccount.photoUrl,
                    "Direction" to tutorAccount.direction,
                    "Experience" to tutorAccount.experience,
                )
            )
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }

    suspend fun addStudentAccount(studentAccount: StudentAccount) {
        val client = AppwriteClient.getClient()
        val databases = Databases(client)
        val uuid = getAccount().id
        try {
            databases.createDocument(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64a992739f88da356852",
                documentId = uuid,
                data = mapOf(
                    "Photo" to studentAccount.photoUrl,
                    "Age" to studentAccount.age,
                    "Name" to studentAccount.name,
                )
            )
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }
    suspend fun signOut() {
        val client = AppwriteClient.getClient()
        val account = Account(client)
        account.deleteSessions()
    }


    suspend fun getAllHighs(highsLD: MutableLiveData<ArrayList<String>>, textForSearch : String) {
        val client = AppwriteClient.getClient()
        val databases = Databases(client)
        var queries = listOf(
            Query.limit(10),
            Query.search("vuz", textForSearch)
        )

        try {
            val highs = ArrayList<String>()
            if(textForSearch.isEmpty()){
                queries = listOf(
                    Query.limit(10)
                )
            }
            val documents = databases.listDocuments(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64b11c868421f3ecce18",
                queries = queries
            )
            documents.documents.forEach {
                highs.add((it.data as MutableMap<*, *>?)?.get("vuz").toString())
            }
            highsLD.postValue(highs)
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }

    suspend fun getAllDirections(
        directionsLD: MutableLiveData<java.util.ArrayList<String>>,
        textForSearch: String
    ) {
        val client = AppwriteClient.getClient()
        val databases = Databases(client)
        var queries = listOf(
            Query.limit(10),
            Query.search("direction", textForSearch)
        )

        try {
            val directions = ArrayList<String>()
            if(textForSearch.isEmpty()){
                queries = listOf(
                    Query.limit(10)
                )
            }
            val documents = databases.listDocuments(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64b11ca1e184ddbec98a",
                queries = queries,
            )
            documents.documents.forEach {
                directions.add((it.data as MutableMap<*, *>?)?.get("direction").toString())
            }
            directionsLD.postValue(directions)
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }

    private suspend fun getAccount(): User<Map<String, Any>> {
        val client = AppwriteClient.getClient()
        val account = Account(client)
        return account.get()
    }

    @JvmOverloads
    fun <R> getContinuation(
        onFinished: BiConsumer<String?, Throwable?>,
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ): Continuation<R> {
        return object : Continuation<R> {
            override val context: CoroutineContext
                get() = dispatcher

            override fun resumeWith(result: Result<R>) {
                onFinished.accept(result.getOrNull().toString(), result.exceptionOrNull())
            }
        }
    }

    suspend fun getAccount(accountLD: MutableLiveData<User<Map<String, Any>>>) {
        val client = AppwriteClient.getClient()
        val account = Account(client)
        try {
            accountLD.postValue(account.get())
        } catch (e: Exception) {
            accountLD.postValue(null)
        }
    }
}