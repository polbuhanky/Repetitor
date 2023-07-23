package com.platovco.repetitor.managers

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    suspend fun getAllHighs(highsLD: MutableLiveData<java.util.ArrayList<String>>) {
        val client = AppwriteClient.getClient()
        val databases = Databases(client)

        try {
            val highs = ArrayList<String>()
            val documents = databases.listDocuments(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64b11c868421f3ecce18",
                queries = listOf(
                    Query.limit(25),
                )
            )
            documents.documents.forEach {
                highs.add((it.data as MutableMap<*, *>?)?.get("vuz").toString())
            }
            var size = documents.documents.size
            var lastId = documents.documents[documents.documents.size - 1].id

            while (size == 25) {
                val documents2 = databases.listDocuments(
                    databaseId = "64a845269d40bb3fd619",
                    collectionId = "64b11c868421f3ecce18",
                    queries = listOf(
                        Query.limit(25),
                        Query.cursorAfter(lastId),
                    )
                )
                documents2.documents.forEach {
                    highs.add((it.data as MutableMap<*, *>?)?.get("vuz").toString())
                }
                if (documents2.documents.isEmpty()) {
                    break;
                }
                lastId = documents2.documents[documents2.documents.size - 1].id
                size = documents2.documents.size
            }
            highsLD.postValue(highs)
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }

    suspend fun getAllDirections(
        brand: String,
        modelsLD: MutableLiveData<java.util.ArrayList<String>>
    ) {
        val client = AppwriteClient.getClient()
        val databases = Databases(client)

        try {
            val models = ArrayList<String>()
            val documents = databases.listDocuments(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64b11ca1e184ddbec98a",
                queries = listOf(
                    Query.limit(25),
                    //Query.equal("direction", brand)
                )
            )
            documents.documents.forEach {
                models.add((it.data as MutableMap<*, *>?)?.get("direction").toString())
            }
            var size = documents.documents.size
            var lastId = documents.documents[documents.documents.size - 1].id

            while (size == 25) {
                val documents2 = databases.listDocuments(
                    databaseId = "64a845269d40bb3fd619",
                    collectionId = "64b11ca1e184ddbec98ac",
                    queries = listOf(
                        Query.limit(25),
                        Query.cursorAfter(lastId),
                        //Query.equal("direction", brand)

                    )
                )
                documents2.documents.forEach {
                    models.add((it.data as MutableMap<*, *>?)?.get("direction").toString())
                }
                if (documents2.documents.isEmpty()) {
                    break;
                }
                lastId = documents2.documents[documents2.documents.size - 1].id
                size = documents2.documents.size
            }
            modelsLD.postValue(models)
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