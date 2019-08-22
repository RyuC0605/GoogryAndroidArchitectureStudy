package com.example.architecturestudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturestudy.R
import com.example.architecturestudy.model.CoinInfo
import com.example.architecturestudy.ui.adapter.CoinAdapter
import com.example.architecturestudy.network.UpbitListener
import com.example.architecturestudy.network.UpbitRequest
import kotlinx.android.synthetic.main.fragment_list_coin.*

class CoinFragment : Fragment() {


    private val coinAdapter by lazy { CoinAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return return inflater.inflate(R.layout.fragment_list_coin, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencyList = arguments?.getString(CURRENCY_LIST) ?: ""
        getTickerInfo(currencyList)

        setCoinAdapter()

    }

    private fun setCoinAdapter() {

        rv_coin_list.run {
            addItemDecoration(DividerItemDecoration(context, 1))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coinAdapter
        }

    }

    private fun getTickerInfo(name: String) {

        UpbitRequest().apply {
            getTickerInfo(name, object : UpbitListener<CoinInfo> {

                override fun onResponse(dataList: List<CoinInfo>) {
                    coinAdapter.setData(dataList)
                }
                override fun onFailure(str: String) {

                }

            })
        }
    }

    companion object {

        private const val CURRENCY_LIST = "currency_list"

        fun newInstance(currencyList: String) = CoinFragment().apply {
            arguments = Bundle().apply {
                putString(CURRENCY_LIST, currencyList)
            }
        }
    }


}