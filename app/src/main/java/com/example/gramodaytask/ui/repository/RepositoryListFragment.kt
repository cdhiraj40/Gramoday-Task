package com.example.gramodaytask.ui.repository

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gramodaytask.R
import com.example.gramodaytask.adapter.RepositoryAdapter
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.viewModal.RepositoryViewModal
import com.example.gramodaytask.databinding.FragmentRepositoryListBinding

class RepositoryListFragment : Fragment(), RepositoryAdapter.RepositoryClickDeleteInterface,
    RepositoryAdapter.RepositoryClickInterface {

    private var _fragmentRepositoryListBinding: FragmentRepositoryListBinding? = null

    private val fragmentRepositoryListBinding get() = _fragmentRepositoryListBinding!!

    private lateinit var repositoryViewModal: RepositoryViewModal

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

        val adapter = RepositoryAdapter(requireContext(), this, this)

        fragmentRepositoryListBinding.repositoryRecyclerView.adapter = adapter

        repositoryViewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RepositoryViewModal::class.java]

        repositoryViewModal.allRepositoryEntity.observe(this, { list ->
            list?.let {
                // on below line we are updating our list.
                adapter.updateList(it)
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

    override fun onDeleteIconClick(repository: RepositoryEntity) {
        repositoryViewModal.deleteRepository(repository)
    }

    override fun onRepositoryClick(repository: RepositoryEntity) {

        TODO("Not yet implemented")
    }
}