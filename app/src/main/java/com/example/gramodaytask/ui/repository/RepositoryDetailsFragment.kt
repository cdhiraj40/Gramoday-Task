package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gramodaytask.databinding.FragmentRepositoryDetaiksBinding

class RepositoryDetailsFragment : Fragment() {

    private var _fragmentRepositoryDetailsBinding: FragmentRepositoryDetaiksBinding? = null

    private val fragmentRepositoryDetailsBinding get() = _fragmentRepositoryDetailsBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentRepositoryDetailsBinding =
            FragmentRepositoryDetaiksBinding.inflate(inflater, container, false)

        val rootView = fragmentRepositoryDetailsBinding.root

        return rootView
    }
}