package id.boytegar.moocow.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import id.boytegar.moocow.R
import id.boytegar.moocow.viewmodel.TransactionsViewModel
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import id.boytegar.moocow.adapter.TransactionsAdapter
/**
 * A simple [Fragment] subclass.
 */
class TransactionsFragment : Fragment() {

    lateinit var transactionsViewModel: TransactionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        transactionsViewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)
        transactionsViewModel.filterTextAll.value = ""
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_transactions, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar4)
        v.toolbar4.title = "Transaction"
        transactionsViewModel.getAllData().observe(this, Observer {
            val linearLayoutManager = LinearLayoutManager(activity)
            v.list_transactions.layoutManager = linearLayoutManager
            v.list_transactions.hasFixedSize()
            val transactionsAdapter = TransactionsAdapter(activity!!, R.layout.list_transaction)
            transactionsAdapter.submitList(it)
            v.list_transactions.adapter = transactionsAdapter
            transactionsAdapter.onItemClick = { menu ->
                // menuItemViewModel.deleteMenu(menu)
            }
        })
        return v
    }


}
