package sample.nackun.com.studyfirst.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.main_fragment.view.*
import sample.nackun.com.studyfirst.R
import sample.nackun.com.studyfirst.TickerAdapter
import sample.nackun.com.studyfirst.data.Repository
import sample.nackun.com.studyfirst.data.remote.RemoteDataSource
import sample.nackun.com.studyfirst.vo.Ticker

class MainFragment : Fragment(), MainContract.View {
    override lateinit var presenter: MainContract.Presenter
    private val tickerAdapter = TickerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        val mainPresenter = MainPresenter(Repository(RemoteDataSource()), this)
        mainPresenter.start()

        with(root) {
            val onClickListener = object : View.OnClickListener {
                override fun onClick(v: View?) {
                    marketKRW.setTextColor(resources.getColor(R.color.grey))
                    marketBTC.setTextColor(resources.getColor(R.color.grey))
                    marketETH.setTextColor(resources.getColor(R.color.grey))
                    marketUSDT.setTextColor(resources.getColor(R.color.grey))
                    var selectedMarket = v as TextView
                    selectedMarket.setTextColor(resources.getColor(R.color.indigo))
                    presenter.requestTickers(selectedMarket.text.toString())
                }
            }
            marketKRW.setOnClickListener(onClickListener)
            marketBTC.setOnClickListener(onClickListener)
            marketETH.setOnClickListener(onClickListener)
            marketUSDT.setOnClickListener(onClickListener)

            tickerRecyclerView.adapter = tickerAdapter
            marketKRW.callOnClick()
        }
        return root
    }

    override fun showTickes(tickers: ArrayList<Ticker>) {
        tickerAdapter.setItems(tickers)

    }

    override fun showToast(str: String?) {
        Toast.makeText(context, str, LENGTH_SHORT).show()
    }
}