package com.temporaryorgname.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        job = SupervisorJob()
        super.onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
    }
}