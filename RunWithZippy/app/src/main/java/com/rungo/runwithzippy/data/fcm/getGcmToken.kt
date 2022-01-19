package com.rungo.runwithzippy.data.fcm

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit

class getGcmToken {
//    fun getFirebaseToken(): Single<String> {
//        return Single.create<String> { emitter ->
//            FirebaseMessaging.getInstance().instanceId
//                .addOnSuccessListener { result ->
//                    if (emitter.isDisposed) {
//                        return@addOnSuccessListener
//                    }
//                    emitter.onSuccess(result.token)
//                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//                        if (emitter.isDisposed) {
//                            return@OnCompleteListener
//                        }
//                        emitter.onSuccess(task.result)
//                    })
//                        .addOnFailureListener { e ->
//                            if (emitter.isDisposed) {
//                                return@addOnFailureListener
//                            }
//                            emitter.onError(e)
//                        }
//                }
//                .timeout(2, TimeUnit.SECONDS)
//                .retry(3)
//                .onErrorReturnItem("")
//        }
//    }
}