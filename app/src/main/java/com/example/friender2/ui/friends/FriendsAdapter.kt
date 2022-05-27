package com.example.friender2.ui.friends

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.friender2.R
import com.example.friender2.Utils
import com.example.friender2.database.Profile
import com.squareup.picasso.Picasso

class FriendsAdapter(
    var dataSet: List<Profile>,
    val removeClicked: ((Profile) -> Unit),
    val cardClicked: ((Long) -> Unit)
) :
    RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    inner class FriendsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val friendImage: ImageView = view.findViewById(R.id.friend_profile_picture)
        val friendName: TextView = view.findViewById(R.id.friend_name_text)
        val friendLocation: TextView = view.findViewById(R.id.friend_place_text)
        val removeButton: ImageButton = view.findViewById(R.id.friend_cross_button)
        val profileCard: CardView = view.findViewById(R.id.friend_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_list_card, parent, false)

        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val profile = dataSet[position]

        Picasso.get().load(profile.imageUrl).into(holder.friendImage)

        holder.friendName.text = Utils.getFullName(profile.firstName, profile.surname)
        holder.friendLocation.text = Utils.getPlaceText(profile.address)

        holder.removeButton.setOnClickListener {
            removeItem(position)
            removeClicked.invoke(profile)
        }

        holder.profileCard.setOnClickListener {
            cardClicked.invoke(profile.id)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(newDataSet: List<Profile>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        val newDataSet = dataSet as MutableList

        newDataSet.removeAt(position)
        dataSet = newDataSet

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}