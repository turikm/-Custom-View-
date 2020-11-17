package com.example.mycustomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MyCoustomView (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs)
{
    private var autocompletextview : AutoCompleteTextView
    private  var listview : ListView
    private var title :TextView
    private var add : ImageView

    private var selectedItems : MutableList<String> = ArrayList()
    private var allItems : MutableList<String> = ArrayList()
    ////////////////////////////////////////////////////
    init
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.my_customer_view_layout,this,true)
        autocompletextview = view.findViewById(R.id.autocompletextview)
        listview = view.findViewById(R.id.listview)
        title = view.findViewById(R.id.title)
        add = view.findViewById(R.id.add)

        autocompletextview.threshold = 1
        ////////////////////////////////////////////////////
        add.setOnClickListener{
            val selectedString = autocompletextview.text.trim().toString()
            when
            {
                selectedString.isEmpty() -> Toast.makeText(getContext(),"Please enter data.",Toast.LENGTH_SHORT).show()
                selectedItems.contains(selectedString) -> Toast.makeText(getContext(),"Item already added",Toast.LENGTH_SHORT).show()
                else ->
                {
                    selectedItems.add(0,selectedString)
                    refreshData(true)
                }
            }
        }
    }
    ////////////////////////////////////////////////////
    fun setData(data: MutableList<String>)
    {
        allItems = data
        autocompletextview.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, allItems))
    }
    ////////////////////////////////////////////////////
    fun setTitle(str : String)
    {
        title.text =str
    }
    ////////////////////////////////////////////////////
    fun getSelectedData() : MutableList<String>
    {
        return selectedItems
    }
    ////////////////////////////////////////////////////
    fun refreshData(clearData : Boolean)
    {
        listview.adapter = MyCustomViewAdapter(context,R.layout.my_custom_view_item,selectedItems)
        setListViewHeightBasedOnChildren(listview)
        if(clearData)
            autocompletextview.setText("")
    }
    ////////////////////////////////////////////////////
    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = listView.paddingTop + listView.paddingBottom
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            (listItem as? ViewGroup)?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }
    ////////////////////////////////////////////////////
    inner class MyCustomViewAdapter(context: Context?, var resource: Int, var objects: MutableList<String>?) : ArrayAdapter<String>(context!!, resource, objects!!)
    {
        private val inflater: LayoutInflater = LayoutInflater.from(context)
        override fun getCount(): Int
        {
            return objects!!.size
        }
        ////////////////////////////////////////////////////
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = inflater.inflate(resource,parent,false)
            val name = view.findViewById<TextView>(R.id.name)
            val delete = view.findViewById<ImageView>(R.id.delete)
            name.text = objects!!.get(position)
            delete.setOnClickListener {selectedItems.removeAt(position)
            refreshData(false)
            }
            return view
        }
    }
}