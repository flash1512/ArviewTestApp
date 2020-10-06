package com.ja.arviewtestapp

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_game.view.*
import okhttp3.OkHttpClient

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(MainViewModel::class.java)
    }
    private val adapter = GameInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayoutManager = LinearLayoutManager(this)

        topGamesRecycler.layoutManager = linearLayoutManager
        topGamesRecycler.addEndlessScrollListener(linearLayoutManager) { _, _, _ ->
            viewModel.loadNextPage(isNetworkAvailable())
        }
        topGamesRecycler.adapter = adapter

        viewModel.topGames.observe(this, {
            adapter.differ.submitList(it)
        })

        viewModel.getTopGames(isNetworkAvailable())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuReview) {
            AlertDialog.Builder(this)
                .setView(R.layout.review_layout)
                .create()
                .show()
        }
        return super.onOptionsItemSelected(item)
    }
}

class GameInfoAdapter: RecyclerView.Adapter<GameInfoVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameInfoVH(v)
    }

    override fun onBindViewHolder(holder: GameInfoVH, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<TopGameInfo>() {
        override fun areItemsTheSame(oldItem: TopGameInfo, newItem: TopGameInfo): Boolean {
            return oldItem.game.id == newItem.game.id
        }

        override fun areContentsTheSame(oldItem: TopGameInfo, newItem: TopGameInfo): Boolean {
            return oldItem.game.id == newItem.game.id
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}

class GameInfoVH(view: View): RecyclerView.ViewHolder(view) {
    fun bind(gameInfo: TopGameInfo) {
        Glide.with(itemView.context)
            .load(gameInfo.game.box["medium"])
            .into(itemView.gameLogo)

        itemView.gameName.text = gameInfo.game.name
        itemView.channelsCount.text = itemView.context.getString(R.string.channels_count, gameInfo.channels)
        itemView.viewersCount.text = itemView.context.getString(R.string.viewers_count, gameInfo.viewers)
    }
}
