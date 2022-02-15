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
import com.example.gramodaytask.adapter.RepositoryBranchIssuesAdapter
import com.example.gramodaytask.api.ApiInterface
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentRepositoryBranchIssuesBinding
import com.example.gramodaytask.model.RepositoryBranchIssues
import com.example.gramodaytask.utils.Constant
import com.example.gramodaytask.utils.extensions.showSnackBar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryBranchIssuesFragment : Fragment(),
    RepositoryBranchIssuesAdapter.RepositoryClickInterface {

    private var _fragmentRepositoryBranchIssuesBinding: FragmentRepositoryBranchIssuesBinding? =
        null
    private val fragmentRepositoryBranchIssuesBinding get() = _fragmentRepositoryBranchIssuesBinding!!
    private lateinit var repositoryViewModal: RepositoryViewModal
    private var repositoryId: Int? = null
    private var repository: RepositoryEntity? = null
    private var repositoryBranchAdapter: RepositoryBranchIssuesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentRepositoryBranchIssuesBinding =
            FragmentRepositoryBranchIssuesBinding.inflate(inflater, container, false)

        val rootView = fragmentRepositoryBranchIssuesBinding.root

        fragmentRepositoryBranchIssuesBinding.progressBar.visibility = View.VISIBLE
        val bundle = arguments
        repositoryId = bundle?.getInt("repoId")

        if (repositoryId != null) {
            lifecycleScope.launch {
                try {
                    repositoryViewModal = ViewModelProvider(
                        this@RepositoryBranchIssuesFragment,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                    )[RepositoryViewModal::class.java]

                    repository = repositoryViewModal.getRepository(repositoryId!!)
                    getRepositoryBranchIssues(
                        repository!!.repositoryOwner,
                        repository!!.repositoryTitle
                    )
                } catch (exception: Exception) {
                    Log.d(
                        RepositoryBranchIssuesFragment::class.java.simpleName,
                        exception.toString()
                    )
                    showSnackBar(requireActivity(), "Something went wrong")
                }

            }
        }
        return rootView
    }

    private fun setUpRecyclerView(body: ArrayList<RepositoryBranchIssues.RepositoryBranchNode>) {
        fragmentRepositoryBranchIssuesBinding.repositoryCommitsRecyclerView.layoutManager =
            LinearLayoutManager(context)

        repositoryBranchAdapter = RepositoryBranchIssuesAdapter(requireContext(), this, body)

        fragmentRepositoryBranchIssuesBinding.repositoryCommitsRecyclerView.adapter =
            repositoryBranchAdapter

        repositoryViewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RepositoryViewModal::class.java]
        fragmentRepositoryBranchIssuesBinding.progressBar.visibility = View.GONE
    }

    private fun getRepositoryBranchIssues(
        owner: String,
        repositoryName: String
    ) {
        val apiInterface = ApiInterface.create().getBranchIssues(owner, repositoryName)

        apiInterface.enqueue(object : Callback<RepositoryBranchIssues> {
            override fun onResponse(
                Call: Call<RepositoryBranchIssues>?,
                response: Response<RepositoryBranchIssues>?
            ) {
                if (response?.body() != null) {
                    val body = response.body()!!
                    Log.d(
                        Constant.TAG(AddRepositoryFragment::class.java).toString(), body.toString()
                    )

                    var size = body.size
                    var i = 0

                    while (i < size) {
                        if (body[i].pull_request != null) {
                            body.removeAt(i)
                            size--
                            i--
                        }
                        i++
                    }
                    setUpRecyclerView(body)

                }
            }

            override fun onFailure(call: Call<RepositoryBranchIssues>?, throwable: Throwable) {
                Log.d(
                    Constant.TAG(AddRepositoryFragment::class.java).toString(),
                    "requestFailed",
                    throwable
                )
            }
        })
    }

    override fun onRepositoryClick(branchIssue: RepositoryBranchIssues.RepositoryBranchNode) {
        showSnackBar(requireActivity(), branchIssue.title)
    }
}