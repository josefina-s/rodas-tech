package com.example.rodastech.fragments.Cloth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rodastech.R

class DetailClothFragment : Fragment() {

    lateinit var v : View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detail_cloth, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()

    }

}