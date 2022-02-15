package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gramodaytask.R
import com.example.gramodaytask.adapter.RepositoryListAdapter
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentRepositoryListBinding

class RepositoryListFragment : Fragment(),
    RepositoryListAdapter.RepositoryClickInterface {

    private var _fragmentRepositoryListBinding: FragmentRepositoryListBinding? = null
    private val fragmentRepositoryListBinding get() = _fragmentRepositoryListBinding!!
    private lateinit var repositoryViewModal: RepositoryViewModal
    private lateinit var repositoryListAdapter: RepositoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentRepositoryListBinding =
            FragmentRepositoryListBinding.inflate(inflater, container, false)

        val rootView = fragmentRepositoryListBinding.root

        setUpRecyclerView()
        return rootView
    }

    private fun setUpRecyclerView() {
        fragmentRepositoryListBinding.repositoryRecyclerView.layoutManager =
            LinearLayoutManager(context)

        repositoryListAdapter = RepositoryListAdapter(requireContext(), this)

        fragmentRepositoryListBinding.repositoryRecyclerView.adapter = repositoryListAdapter

        repositoryViewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RepositoryViewModal::class.java]

        repositoryViewModal.allRepositoryEntity.observe(this, { list ->
            list?.let {
                // on below line we are updating our list.
                repositoryListAdapter.updateList(it)
                checkIfEmpty()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentRepositoryListBinding.emptyRepoView.emptyRepoLayout.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentRepositoryList_to_AddRepositoryFragment)
        }
    }

    private fun checkIfEmpty() {
        if (repositoryListAdapter.getDataItemCount() == 0) {
            fragmentRepositoryListBinding.emptyRepoView.root.visibility = View.VISIBLE
        } else {
            fragmentRepositoryListBinding.emptyRepoView.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentRepositoryListBinding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // clear the Main Activity's menu
        menu.clear()
        inflater.inflate(R.menu.menu_repository_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_repo -> {
                findNavController().navigate(R.id.action_FragmentRepositoryList_to_AddRepositoryFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onDeleteIconClick(repository: RepositoryEntity) {
//        repositoryViewModal.deleteRepository(repository)
//    }

    override fun onRepositoryClick(repository: RepositoryEntity) {
        val bundle = bundleOf(
            "repoId" to repository.id
        )
        findNavController().navigate(
            R.id.action_FragmentRepositoryList_to_FragmentRepositoryDetails,
            bundle
        )
    }
}