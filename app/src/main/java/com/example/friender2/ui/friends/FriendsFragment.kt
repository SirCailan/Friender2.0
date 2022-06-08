package com.example.friender2.ui.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friender2.R
import com.example.friender2.database.Profile

class FriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by activityViewModels()

    //Adapter Components
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendsAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FriendsAdapter({ profileToDelete ->
            setCurrentProfile(profileToDelete)
            //Saves the profile to delete.

            viewModel.removeFriend()
        }, { clickedProfile ->
            setCurrentProfile(clickedProfile)
            //Saves the clicked profile in the shared ViewModel, for easy retrieval on navigation.

            findNavController().navigate(
                FriendsFragmentDirections.actionFriendsFragmentToDetailsFragment()
            ) //Navigates to the details screen.
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
        viewModel.friendsList.observe(viewLifecycleOwner) { friendsList ->
            adapter.updateDataSet(friendsList)
        }
    }

    private fun setCurrentProfile(profile: Profile) {
        viewModel.selectedFriend = profile
    }
}