package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gramodaytask.adapter.RepositoryBranchCommitsAdapter
import com.example.gramodaytask.api.ApiInterface
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentRepositoryBranchCommitsBinding
import com.example.gramodaytask.model.RepositoryBranchCommits
import com.example.gramodaytask.utils.Constant
import com.example.gramodaytask.utils.extensions.showSnackBar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryBranchCommitsFragment : Fragment(),
    RepositoryBranchCommitsAdapter.RepositoryClickInterface {

    private var _fragmentRepositoryBranchCommitsBinding: FragmentRepositoryBranchCommitsBinding? =
        null
    private val fragmentRepositoryBranchCommitsBinding get() = _fragmentRepositoryBranchCommitsBinding!!
    private lateinit var repositoryViewModal: RepositoryViewModal
    private var branchSHA: String? = null
    private var repositoryId: Int? = null
    private var repository: RepositoryEntity? = null
    private var repositoryBranchAdapter: RepositoryBranchCommitsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentRepositoryBranchCommitsBinding =
            FragmentRepositoryBranchCommitsBinding.inflate(inflater, container, false)

        val rootView = fragmentRepositoryBranchCommitsBinding.root

        fragmentRepositoryBranchCommitsBinding.progressBar.visibility = View.VISIBLE
        val bundle = arguments
        branchSHA = bundle?.getString("branchSHA")
        repositoryId = bundle?.getInt("repoId")

        if (branchSHA != null && repositoryId != null) {
            lifecycleScope.launch {
                try {
                    repositoryViewModal = ViewModelProvider(
                        this@RepositoryBranchCommitsFragment,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                    )[RepositoryViewModal::class.java]

                    repository = repositoryViewModal.getRepository(repositoryId!!)
                    getRepositoryBranchesCommits(
                        repository!!.repositoryOwner,
                        repository!!.repositoryTitle,
                        branchSHA!!
                    )
                } catch (exception: Exception) {
                    Log.d(
                        RepositoryBranchCommitsFragment::class.java.simpleName,
                        exception.toString()
                    )
                    showSnackBar(requireActivity(), "Something went wrong")
                }

            }
        }
        return rootView
    }

    private fun setUpRecyclerView(body: ArrayList<RepositoryBranchCommits.RepositoryBranchNode>) {
        fragmentRepositoryBranchCommitsBinding.repositoryCommitsRecyclerView.layoutManager =
            LinearLayoutManager(context)

        repositoryBranchAdapter = RepositoryBranchCommitsAdapter(requireContext(), this, body)

        fragmentRepositoryBranchCommitsBinding.repositoryCommitsRecyclerView.adapter =
            repositoryBranchAdapter

        repositoryViewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RepositoryViewModal::class.java]
        fragmentRepositoryBranchCommitsBinding.progressBar.visibility = View.GONE
    }

    private fun getRepositoryBranchesCommits(
        owner: String,
        repositoryName: String,
        branchSHA: String
    ) {
        val apiInterface = ApiInterface.create().getBranchCommits(owner, repositoryName, branchSHA)

        apiInterface.enqueue(object : Callback<RepositoryBranchCommits> {
            override fun onResponse(
                Call: Call<RepositoryBranchCommits>?,
                response: Response<RepositoryBranchCommits>?
            ) {
                if (response?.body() != null) {
                    val body = response.body()!!
                    Log.d(
                        Constant.TAG(AddRepositoryFragment::class.java).toString(), body.toString()
                    )
                    setUpRecyclerView(body)

                }
            }

            override fun onFailure(call: Call<RepositoryBranchCommits>?, throwable: Throwable) {
                Log.d(
                    Constant.TAG(AddRepositoryFragment::class.java).toString(),
                    "requestFailed",
                    throwable
                )
            }
        })
    }


    override fun onRepositoryClick(branchCommit: RepositoryBranchCommits.RepositoryBranchNode) {
        showSnackBar(requireActivity(), branchCommit.sha)
    }
}