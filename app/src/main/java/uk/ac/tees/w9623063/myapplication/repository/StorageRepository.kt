package uk.ac.tees.w9623063.myapplication.repository

import uk.ac.tees.w9623063.myapplication.models.Scores
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val SCORES_COLLECTION_REF = "scores"

class StorageRepository() {

    fun user() = Firebase.auth.currentUser
    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val scoresRef:CollectionReference = Firebase.firestore.collection(SCORES_COLLECTION_REF)

    fun getUserNotes(
        userId:String
    ):Flow<Resources<List<Scores>>> = callbackFlow {
        var snapshotStateListener:ListenerRegistration? = null

        try {
            snapshotStateListener = scoresRef.orderBy("timestamp")
                .whereEqualTo("userId", userId).addSnapshotListener { value, error ->
                    val response = if(value != null){
                        val notes = value.toObjects(Scores::class.java)
                        Resources.Success(data = notes)
                    }else{
                        Resources.Error(throwable = error?.cause)
                    }
                    trySend(response)
                }

        }catch (e:Exception){
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getNote(
        noteId:String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Scores?) -> Unit
    ){
        scoresRef.document(noteId).get().addOnSuccessListener {
            onSuccess.invoke(it?.toObject(Scores::class.java))
        }.addOnFailureListener { result ->
            onError.invoke(result)
        }
    }

    fun addNote(
        userId:String,
        title:String,
        description:String,
        timestamp:Timestamp,
        color:Int = 0,
        onComplete:(Boolean) -> Unit
    ){
        val documentId = scoresRef.document().id
        val note = Scores(userId,title,description,timestamp,color,documentId)

        scoresRef.document(documentId).set(note).addOnCompleteListener { result ->
            onComplete.invoke(result.isSuccessful)
        }
    }

    fun deleteNote(
        noteId: String, onComplete: (Boolean) -> Unit
    ){
        scoresRef.document(noteId).delete().addOnCompleteListener {
            onComplete.invoke(it.isSuccessful)
        }
    }

    fun updateNote(
        noteId:String,
        title:String,
        description:String,
        color: Int,
        onResult: (Boolean) -> Unit
    ){
        val updateData = hashMapOf<String,Any>(
            "colorIndex" to color,
            "description" to description,
            "title" to title
        )

        scoresRef.document(noteId).update(updateData).addOnCompleteListener {
            onResult.invoke(it.isSuccessful)
        }
    }

    fun signOut() = Firebase.auth.signOut()
}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable?? = null,
){
    class Loading<T>:Resources<T>()
    class Success<T>(data: T?):Resources<T>(data= data)
    class Error<T>(throwable: Throwable?):Resources<T>(throwable = throwable)
}
