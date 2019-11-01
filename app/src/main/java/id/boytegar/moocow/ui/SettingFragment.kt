package id.boytegar.moocow.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.boytegar.moocow.R
import kotlinx.android.synthetic.main.fragment_setting.view.*
import id.boytegar.moocow.AddPrinterActivity
import id.boytegar.moocow.MenuActivity
/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_setting, container, false)
        v.btn_add_device.setOnClickListener {
            val intent = Intent(activity!!, AddPrinterActivity::class.java)
            startActivity(intent)
        }
        v.btn_menu.setOnClickListener {
            val intent = Intent(activity!!, MenuActivity::class.java)
            startActivity(intent)
        }
        return v
    }


}
