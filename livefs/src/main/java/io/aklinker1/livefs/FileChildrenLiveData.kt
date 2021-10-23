package io.aklinker1.livefs

import android.os.FileObserver
import android.util.Log
import androidx.lifecycle.LiveData

class FileChildrenLiveData(val parent: LiveFile, private val mask: Int = FileObserver.ALL_EVENTS) :
    LiveData<List<LiveFile>>() {

    private val observer: FileObserver = object : FileObserver(parent.file, mask) {
        override fun onEvent(event: Int, childPath: String?) {
            if (!doesEventEffectList(event)) return

            if (parent.file.exists()) postValue(parent.children())
            else postValue(emptyList())
        }

        private fun doesEventEffectList(event: Int): Boolean {
            return event == CREATE
                    || event == DELETE
                    || event == DELETE_SELF
                    || event == MODIFY
                    || event == MOVED_FROM
                    || event == MOVED_TO
                    || event == MOVE_SELF
        }
    }

    override fun onActive() {
        super.onActive()
        observer.startWatching()
    }

    override fun onInactive() {
        super.onInactive()
        observer.stopWatching()
    }

}
