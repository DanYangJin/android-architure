package com.shouzhan.design.compontent.picker

class StringWheelAdapter(private var items: List<String>) : WheelAdapter<String> {

    override fun getItemsCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): String {
        return items[index]
    }

    override fun indexOf(o: String?): Int {
        return items.indexOf(o)
    }

}