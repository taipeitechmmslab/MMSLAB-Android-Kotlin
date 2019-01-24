package bluenet.com.lab15

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.e("Firebase", "onNewToken  $token")
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        Log.e("Firebase","onMessageReceived")

        Log.e("Firebase", msg.from)
        for(entry in msg.data.entries)
            Log.e("message","${entry.key}/${entry.value}")
    }
}