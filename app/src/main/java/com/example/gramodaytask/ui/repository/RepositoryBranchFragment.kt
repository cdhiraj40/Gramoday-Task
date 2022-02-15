package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gramodaytask.R
import com.example.gramodaytask.adapter.RepositoryBranchAdapter
import com.example.gramodaytask.api.ApiInterface
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentRepositoryBranchBinding
import com.example.gramodaytask.model.RepositoryBranches
import com.example.gramodaytask.utils.Constant
import com.example.gramodaytask.utils.extensions.showSnackBar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryBranchFragment : Fragment(), RepositoryBranchAdapter.RepositoryClickInterface {

    private var _fragmentRepositoryBranchBinding: FragmentRepositoryBranchBinding? = null
    private val fragmentRepositoryBranchBinding get() = _fragmentRepositoryBranchBinding!!
    private lateinit var repositoryViewModal: RepositoryViewModal
    private var repositoryId: Int? = null
    private var repository: RepositoryEntity? = null
    private var repositoryBranchAdapter: RepositoryBranchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentRepositoryBranchBinding =
            FragmentRepositoryBranchBinding.inflate(inflater, container, false)

        val rootView = fragmentRepositoryBranchBinding.root

        fragmentRepositoryBranchBinding.progressBar.visibility = View.VISIBLE
        val bundle = arguments
        repositoryId = bundle?.getInt("repoId")

        if (repositoryId != null) {
            lifecycleScope.launch {
                try {
                    repositoryViewModal = ViewModelProvider(
                        this@RepositoryBranchFragment,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                    )[RepositoryViewModal::class.java]

                    repository = repositoryViewModal.getRepository(repositoryId!!)
                    getRepositoryBranches(
                        repository!!.repositoryOwner,
                        repository!!.repositoryTitle
                    )
                } catch (exception: Exception) {
                    showSnackBar(requireActivity(), "Something went wrong")
                }

            }
        }
        return rootView
    }

    private fun setUpRecyclerView(body: ArrayList<RepositoryBranches.RepositoryBranchNode>) {
        fragmentRepositoryBranchBinding.repositoryBranchRecyclerView.layoutManager =
            LinearLayoutManager(context)

        repositoryBranchAdapter = RepositoryBranchAdapter(requireContext(), this, body)

        fragmentRepositoryBranchBinding.repositoryBranchRecyclerView.adapter =
            repositoryBranchAdapter

        repositoryViewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RepositoryViewModal::class.java]
        fragmentRepositoryBranchBinding.progressBar.visibility = View.GONE
    }

    private fun getRepositoryBranches(owner: String, repositoryName: String) {
        val apiInterface = ApiInterface.create().getBranches(owner, repositoryName)

        apiInterface.enqueue(object : Callback<RepositoryBranches> {
            override fun onResponse(
                Call: Call<RepositoryBranches>?,
                response: Response<RepositoryBranches>?
            ) {
                if (response?.body() != null) {
                    val body = response.body()!!
                    Log.d(
                        Constant.TAG(AddRepositoryFragment::class.java).toString(), body.toString()
                    )
                    setUpRecyclerView(body)

                }
            }

            override fun onFailure(call: Call<RepositoryBranches>?, throwable: Throwable) {
                Log.d(
                    Constant.TAG(AddRepositoryFragment::class.java).toString(),
                    "requestFailed",
                    throwable
                )
            }
        })
    }

    override fun onRepositoryClick(
        repositoryBranch: RepositoryBranches.RepositoryBranchNode,
        repositoryBranchSHA: String
    ) {

        findNavController().navigate(
            R.id.action_FragmentRepositoryDetails_to_RepositoryBranchCommitsFragment, bundleOf(
                "branchSHA" to repositoryBranchSHA,
                "repoId" to repositoryId
            )
        )
    }
}