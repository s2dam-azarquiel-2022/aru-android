package net.azarquiel.friendroom.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.databinding.ActivityFriendBinding
import net.azarquiel.friendroom.model.Group
import net.azarquiel.friendroom.view.adapter.FriendAdapter
import net.azarquiel.friendroom.view.adapter.GroupAdapter
import net.azarquiel.friendroom.viewModel.FriendViewModel
import net.azarquiel.friendroom.viewModel.GroupViewModel

class FriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendBinding
    private lateinit var group: Group
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var friendViewModel: FriendViewModel

    private fun setup() {
        binding = ActivityFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        group = intent.getSerializableExtra("group") as Group

        friendAdapter = FriendAdapter(this, binding.contentMain.friendRecycler, R.layout.group_row)

        friendViewModel = ViewModelProvider(this)[FriendViewModel::class.java]
        friendViewModel.getAll().observe(this) { groups ->
            groups.let { friendAdapter.setData(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}