package id.boytegar.moocow.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import id.boytegar.moocow.R
import id.boytegar.moocow.viewmodel.TransactionsViewModel

/**
 * A simple [Fragment] subclass.
 */
class TransactionsFragment : Fragment() {

    lateinit var transactionsViewModel: TransactionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        transactionsViewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_transactions, container, false)
        return v
    }


}
