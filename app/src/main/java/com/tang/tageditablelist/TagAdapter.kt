package com.tang.tageditablelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TagAdapter : RecyclerView.Adapter<TagAdapter.MyHolder>() {

    var list: MutableList<TagBean> = mutableListOf()
    // 选中列表
    var selectedList = mutableListOf<Int>()
    var maxSelected = 5
    // 是否显示删除按钮
    var isVisibility = false

    fun setData(list: MutableList<TagBean>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun isVisibility(isVisibility: Boolean) {
        this.isVisibility = isVisibility
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tag,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvTag.text = list?.get(position)?.data
        onTagClick(holder, position)
        LongClick(holder, position)
        setDeleteButtonShow(holder, position)
        delete(holder, position)
        holder.tvTag.isSelected = list?.get(position)?.isSelected!!
    }

    private fun onTagClick(holder: MyHolder, position: Int) {
        holder.tvTag.setOnClickListener {
            setChecked(it as TextView, position)
        }
    }

    fun delete(holder: MyHolder, position: Int) {
        holder.ivDelete.setOnClickListener {
            list?.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list!!.size - position)
            // 删除后会刷新列表，需要重新设置已选中列表
            selectedList.clear()
            for (i in list!!.indices){
                if (list?.get(i)?.isSelected!!){
                    selectedList.add(i)
                }
            }
        }
    }

    /**
     * 设置删除按钮显示或隐藏
     */
    private fun setDeleteButtonShow(holder: TagAdapter.MyHolder, position: Int) {
        if (isVisibility)
            holder.ivDelete.visibility = View.VISIBLE
        else
            holder.ivDelete.visibility = View.INVISIBLE
    }

    private fun LongClick(holder: MyHolder, position: Int) {
        holder.tvTag?.setOnLongClickListener {
            isVisibility(true)
            true
        }
    }

    /**
     * 设置选中样式
     */
    private fun setChecked(text: TextView, position: Int) {
        if (list?.get(position)?.isSelected!!) {
            unSelected(text, position)
        } else {
            selected(text, position)
        }
    }

    /**
     * 选中
     */
    private fun selected(text: TextView, position: Int) {
        if (selectedList.size >= maxSelected) {
            return
        }
        selectedList.add(position)
        list?.get(position)?.isSelected = true
        text.isSelected = true
    }

    /**
     * 未选中
     */
    private fun unSelected(text: TextView, position: Int) {
        deleteSelectedListItem(position)
        list?.get(position)?.isSelected = false
        text.isSelected = false
    }

    fun deleteSelectedListItem(position: Int) {
        for (i in selectedList.indices) {
            if (position == selectedList[i]) {
                selectedList.removeAt(i)
                break
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTag = itemView.findViewById<TextView>(R.id.tv_tag)
        var ivDelete = itemView.findViewById<ImageView>(R.id.iv_delete)
    }
}