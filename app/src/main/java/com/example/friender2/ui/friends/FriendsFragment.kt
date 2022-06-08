package com.example.friender2.ui.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friender2.R

class FriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FriendsAdapter({ profileToDelete ->
            viewModel.removeFriend(profileToDelete)
        }, { clickedProfileId ->
            findNavController().navigate(
                FriendsFragmentDirections.actionFriendsFragmentToDetailsFragment(
                    clickedProfileId
                )
            )
        })
        //Instantiated before we get the friends list to ensure that the data is updated.

        viewModel.getFriendsList()
        //Done in onCreate to get list as soon as possible. Done after the adapter, to be safe.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)

        setObservers()

        layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.friends_recyclerview)
    }

    private fun setObservers() {
        viewModel.friendsList.observe(viewLifecycleOwner) { friends ->
            adapter.updateDataSet(friends)
        }
    }
}