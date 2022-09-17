package com.jasmeet.emailvalid.activities

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jasmeet.emailvalid.R
import com.jasmeet.emailvalid.adapter.GroceryRvAdapter
import com.jasmeet.emailvalid.databinding.ActivitySecondBinding
import com.jasmeet.emailvalid.repository.GroceryRepository
import com.jasmeet.emailvalid.roomDB.GroceryDatabase
import com.jasmeet.emailvalid.roomDB.GroceryItems
import com.jasmeet.emailvalid.viewModel.GroceryViewModel
import com.jasmeet.emailvalid.viewModel.GroceryViewModelFactory

class SecondActivity : AppCompatActivity(),GroceryRvAdapter.GroceryItemClickInterface {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryRvAdapter: GroceryRvAdapter
    lateinit var groceryViewModel: GroceryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding.root)

        list = ArrayList<GroceryItems>()

        groceryRvAdapter = GroceryRvAdapter(list,this)

        binding.rvItems.layoutManager = LinearLayoutManager(this)

        binding.rvItems.adapter =groceryRvAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))

        val factory = GroceryViewModelFactory(groceryRepository)

        groceryViewModel = ViewModelProvider(this,factory)[GroceryViewModel::class.java]

        groceryViewModel.getAllGroceryItems().observe(this, Observer {
            groceryRvAdapter.list = it
            groceryRvAdapter.notifyDataSetChanged()
        })

        binding.fabAdd.setOnClickListener{
            openDialog()
        }



    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.btn_cancel)
        val addBtn = dialog.findViewById<Button>(R.id.btn_add)

        val itemEdt = dialog.findViewById<EditText>(R.id.item_na)
        val itemPrice = dialog.findViewById<EditText>(R.id.item_pri)
        val itemQuantity = dialog.findViewById<EditText>(R.id.item_quant)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName :String = itemEdt.text.toString()
            val itemPrice :String = itemPrice.text.toString()
            val itemQuantity :String = itemQuantity.text.toString()

            val qty : Int = itemQuantity.toInt()
            val pr :Float = itemPrice.toFloat()

            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() &&itemQuantity.isNotEmpty()){
                val items = GroceryItems(itemName,qty,pr)

                groceryViewModel.insert(items)

                Snackbar.make(binding.root,"Item Inserted..",Snackbar.LENGTH_SHORT).show()

                groceryRvAdapter.notifyDataSetChanged()
                dialog.dismiss()

            }else{
                Snackbar.make(binding.root,"Empty fields are not allowed !!",Snackbar.LENGTH_SHORT).show()


            }

        }
        dialog.show()






    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRvAdapter.notifyDataSetChanged()
        Snackbar.make(binding.root,"Item Deleted !!",Snackbar.LENGTH_SHORT).show()

    }

}