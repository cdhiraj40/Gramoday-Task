package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gramodaytask.api.ApiInterface
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentAddRepositoryBinding
import com.example.gramodaytask.model.Repository
import com.example.gramodaytask.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRepositoryFragment : Fragment() {

    private var _fragmentAddRepositoryBinding: FragmentAddRepositoryBinding? = null

    private val fragmentAddRepositoryBinding get() = _fragmentAddRepositoryBinding!!

    private lateinit var repositoryViewModal: RepositoryViewModal
    private var repositoryID = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentAddRepositoryBinding =
            FragmentAddRepositoryBinding.inflate(inflater, container, false)
        val rootView = fragmentAddRepositoryBinding.root


        repositoryViewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RepositoryViewModal::class.java]

        fragmentAddRepositoryBinding.addRepositoryButton.setOnClickListener {
            val owner = fragmentAddRepositoryBinding.ownerEditText.text.toString()
            val repositoryName = fragmentAddRepositoryBinding.repoNameEditText.text.toString()
            getRepository(owner, repositoryName)
        }
        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentAddRepositoryBinding = null
    }


    private fun getRepository(owner: String, repositoryName: String) {
        val apiInterface = ApiInterface.create().getData(owner, repositoryName)

        apiInterface.enqueue(object : Callback<Repository> {
            override fun onResponse(Call: Call<Repository>?, response: Response<Repository>?) {
                if (response?.body() != null) {
                    val body = response.body()!!
                    Log.d(
                        Constant.TAG(AddRepositoryFragment::class.java).toString(), body.toString()
                    )

                    val repository = RepositoryEntity(body.name!!, body.description!!, body.html_url!!)
                    repositoryViewModal.addRepository(repository)
                }
            }

            override fun onFailure(call: Call<Repository>?, throwable: Throwable) {
                Log.d(
                    Constant.TAG(AddRepositoryFragment::class.java).toString(),
                    "requestFailed",
                    throwable
                )
            }
        })
    }
}