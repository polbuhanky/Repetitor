package com.platovco.repetitor.managers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.platovco.repetitor.models.StudentAccount
import com.platovco.repetitor.models.TutorAccount
import com.platovco.repetitor.models.cardStudent
import com.platovco.repetitor.models.cardTutor
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.InputFile
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File
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
                    "Name" to tutorAccount.name,
                    "Education" to tutorAccount.education,
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
                    "Name" to studentAccount.name,
                    "Education" to studentAccount.high,
                    "Photo" to studentAccount.photoUrl,
                    "Direction" to studentAccount.direction,
                    "Experience" to studentAccount.experience,
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


    suspend fun getAllHighs(
        highsLD: MutableLiveData<ArrayList<String>>,
        textForSearch : String) {
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
        directionsLD: MutableLiveData<ArrayList<String>>,
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

    suspend fun getAllStudent(
        studentsLD: MutableLiveData<ArrayList<cardStudent>>
    ){
        val client = AppwriteClient.getClient()
        val databases = Databases(client)

        try {
            val students = ArrayList<cardStudent>()
            var queries = listOf(
                Query.limit(15),
            )

            val documents = databases.listDocuments(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64a992739f88da356852",
                queries = queries,
            )
            documents.documents.forEach {
                students.add(cardStudent(
                    (it.data as MutableMap<*, *>?)?.get("Photo").toString(),
                    (it.data as MutableMap<*, *>?)?.get("Experience").toString(),
                    (it.data as MutableMap<*, *>?)?.get("Name").toString())
                )
            }

            Log.e("AAA",  students[0].photo)
            studentsLD.postValue(students)
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }

    suspend fun getAllTutor(
        tutorsLD: MutableLiveData<ArrayList<cardTutor>>
    ){
        val client = AppwriteClient.getClient()
        val databases = Databases(client)

        try {
            val tutors = ArrayList<cardTutor>()
            var queries = listOf(
                Query.limit(15),
            )

            val documents = databases.listDocuments(
                databaseId = "64a845269d40bb3fd619",
                collectionId = "64a8452b80905db4197c",
                queries = queries,
            )
            documents.documents.forEach {
                tutors.add(cardTutor(
                    (it.data as MutableMap<*, *>?)?.get("Photo").toString(),
                    (it.data as MutableMap<*, *>?)?.get("Name").toString(),
                    (it.data as MutableMap<*, *>?)?.get("Direction").toString(),
                    (it.data as MutableMap<*, *>?)?.get("Education").toString(),
                    (it.data as MutableMap<*, *>?)?.get("Experience").toString())
                )
            }
            Log.e("AAA",  tutors[0].photo)
            tutorsLD.postValue(tutors)
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }

    suspend fun createFileTutorStorage(fileId: String, file: File){
        val client = AppwriteClient.getClient()
        val storage = Storage(client)

        storage.createFile(
            bucketId = "652510bc23dcaf951fff",
            fileId = fileId,
            file = InputFile.fromFile(file),
        )
    }

    suspend fun createFileStudentStorage(fileId: String, file: File){
        val client = AppwriteClient.getClient()
        val storage = Storage(client)

        storage.createFile(
            bucketId = "65251e9c04cdd06bcec8",
            fileId = fileId,
            file = InputFile.fromFile(file),
        )
    }

    suspend fun getTutor(tutorLD: MutableLiveData<TutorAccount>){
        val client = AppwriteClient.getClient()
        val databases = Databases(client)

        val response = databases.getDocument(
            databaseId = "64a845269d40bb3fd619",
            collectionId = "64a8452b80905db4197c",
            documentId = getAccount().id
        )
        val tutorAccount = TutorAccount(response.data as Map<*, *>)
        tutorLD.postValue(tutorAccount)
    }

    suspend fun getStudent(studentLD: MutableLiveData<StudentAccount>){
        val client = AppwriteClient.getClient()
        val databases = Databases(client)

        val response = databases.getDocument(
            databaseId = "64a845269d40bb3fd619",
            collectionId = "64a992739f88da356852",
            documentId = getAccount().id
        )
        val studentAccount = StudentAccount(response.data as Map<*, *>)
        studentLD.postValue(studentAccount)
    }

}