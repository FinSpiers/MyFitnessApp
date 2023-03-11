package uniks.cc.myfitnessapp.core.domain.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.ActivityTransitionResult


class ActivityTransitionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)
            result?.let {
                result.transitionEvents.forEach { event ->
                    val info =
                        "Transition: ${ActivityTransitions.toActivityString(event.activityType)} - ${
                            ActivityTransitions.toTransitionType(
                                event.transitionType
                            )
                        }"
                    Log.e("AT", info)
                }
            }
        }
    }
}
