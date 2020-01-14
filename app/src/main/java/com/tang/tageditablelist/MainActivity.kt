package com.tang.tageditablelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tagAdapter : TagAdapter ?= null
    var list = mutableListOf<TagBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        initData()
        btn_complete.setOnClickListener { tagAdapter?.isVisibility(false) }
    }

    private fun initRecycler() {
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
            .setOrientation(ChipsLayoutManager.HORIZONTAL)
            .build()
        rv_tag.layoutManager = chipsLayoutManager
        tagAdapter = TagAdapter()
        rv_tag.adapter = tagAdapter
    }

    private fun initData() {
        for (i in 0..20){
            list.add(TagBean("哈哈$i"))
        }
        tagAdapter?.setData(list)
    }
}
