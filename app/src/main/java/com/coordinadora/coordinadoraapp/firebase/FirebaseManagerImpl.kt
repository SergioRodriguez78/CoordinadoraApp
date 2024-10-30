package com.coordinadora.coordinadoraapp.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseManagerImpl : FirebaseManager {

    private val db = Firebase.firestore

    override fun createRemoteUser( validationPeriod: Int) {
        val timestamp = System.currentTimeMillis()
        val formattedUser = DEFAULT_USER.replace("%", timestamp.toString())

        db.collection(COLLECTION_NAME).document(formattedUser).set(
            hashMapOf(
                DATE_NAME to timestamp,
                USER_NAME to formattedUser,
                VALIDATION_PERIOD_NAME to validationPeriod
            )
        )
    }

    override fun deleteRemoteUser() {
        db.collection(COLLECTION_NAME)
            .get()
            .addOnSuccessListener { documents ->
                val userDocument = getDocument(documents)

                userDocument?.let { document ->
                    db.collection(COLLECTION_NAME).document(document.id)
                        .delete()
                        .addOnSuccessListener {
                            println("Usuario ${document.id} eliminado correctamente.")
                        }
                        .addOnFailureListener { e ->
                            println("Error al eliminar usuario ${document.id}: ${e.message}")
                        }
                } ?: println("No se encontró ningún usuario que contenga 'sergio' en su nombre.")
            }
            .addOnFailureListener { e ->
                println("Error al buscar usuarios: ${e.message}")
            }
    }

    override fun decreaseValidationPeriod() {
        db.collection(COLLECTION_NAME)
            .get()
            .addOnSuccessListener { documents ->
                val userDocument = getDocument(documents)

                userDocument?.let { document ->
                    val currentPeriod = document.getLong(VALIDATION_PERIOD_NAME) ?: 0
                    val newPeriod = currentPeriod - 1

                    db.collection(COLLECTION_NAME).document(document.id)
                        .update(VALIDATION_PERIOD_NAME, newPeriod)
                        .addOnSuccessListener {
                            println("Periodo de validación disminuido a $newPeriod para ${document.id}.")
                        }
                        .addOnFailureListener { e ->
                            println("Error al actualizar el periodo de validación: ${e.message}")
                        }
                } ?: println("No se encontró ningún usuario que contenga 'sergio' en su nombre.")
            }
            .addOnFailureListener { e ->
                println("Error al buscar usuarios: ${e.message}")
            }
    }

    private fun getDocument(documents: QuerySnapshot): DocumentSnapshot? {
        val userDocument = documents.documents.firstOrNull {
            it.getString(USER_NAME)?.contains("sergio") == true
        }
        return userDocument
    }


    companion object {
        private const val DEFAULT_USER = "sergio-%_login"
        private const val COLLECTION_NAME = "usuario_login"
        private const val DATE_NAME = "fecha_registro"
        private const val USER_NAME = "usuario"
        private const val VALIDATION_PERIOD_NAME = "periodo_validacion"
    }
}
