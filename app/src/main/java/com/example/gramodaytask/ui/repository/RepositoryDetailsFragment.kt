package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gramodaytask.R
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentRepositoryDetailsBinding
import com.example.gramodaytask.utils.dialog.AlertDialogShower
import com.example.gramodaytask.utils.dialog.AppDialogs
import com.example.gramodaytask.utils.extensions.openBrowserLink
import com.example.gramodaytask.utils.extensions.showSnackBar
import kotlinx.coroutines.launch

class RepositoryDetailsFragment : Fragment() {

    private var _fragmentRepositoryDetailsBinding: FragmentRepositoryDetailsBinding? = null
    private var repositoryId: Int? = null
    private var repository: RepositoryEntity? = null
    private lateinit var repositoryViewModal: RepositoryViewModal


    private val fragmentRepositoryDetailsBinding get() = _fragmentRepositoryDetailsBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentRepositoryDetailsBinding =
            FragmentRepositoryDetailsBinding.inflate(inflater, container, false)

        val rootView = fragmentRepositoryDetailsBinding.root

        val bundle = arguments

        repositoryId = bundle?.getInt("repoId")

        if (repositoryId != null) {
            lifecycleScope.launch {
                try {
                    repositoryViewModal = ViewModelProvider(
                        this@RepositoryDetailsFragment,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                    )[RepositoryViewModal::class.java]

                    repository = repositoryViewModal.getRepository(repositoryId!!)
                    setUpRepository(repository!!)

                } catch (exception: Exception) {
                    showSnackBar(requireActivity(), "Something went wrong")
                }

            }
        }

        fragmentRepositoryDetailsBinding.branchesLayout.setOnClickListener {
            findNavController().navigate(
                R.id.RepositoryBranchFragment, bundleOf(
                    "repoId" to repository?.id
                )
            )
        }

        fragmentRepositoryDetailsBinding.issuesLayout.setOnClickListener {
            if (repository?.openIssuesCount!! > 0) {
                findNavController().navigate(
                    R.id.RepositoryBranchIssuesFragment, bundleOf(
                        "repoId" to repository?.id
                    )
                )
            }else{
                showSnackBar(requireActivity(),"There are no Issues.")
            }
        }

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setUpRepository(repository: RepositoryEntity) {
        fragmentRepositoryDetailsBinding.repositoryTitle.text = repository.repositoryTitle
        fragmentRepositoryDetailsBinding.repositoryDescription.text =
            repository.repositoryDescription
        fragmentRepositoryDetailsBinding.issuesText.text = "ISSUES(${repository.openIssuesCount})"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // clear the Main Activity's menu
        menu.clear()
        inflater.inflate(R.menu.menu_repository_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_repo -> {
                deleteRepo(repository!!)
                true
            }
            R.id.action_open_repo_browser -> {
                openRepoOnline(repository!!)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteRepo(repository: RepositoryEntity) {
        AlertDialogShower(requireActivity()).show(AppDialogs.DeleteRepository, {
            repositoryViewModal.deleteRepository(repository)
            findNavController().navigate(R.id.action_FragmentRepositoryDetails_to_FragmentRepositoryList)
            return@show
        }, {

        })
    }

    private fun openRepoOnline(repository: RepositoryEntity) {
        requireContext().openBrowserLink(repository.repositoryUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentRepositoryDetailsBinding = null
    }
}