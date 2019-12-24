package com.example.androidarchitecture.ui.image


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidarchitecture.R
import com.example.androidarchitecture.common.toast
import com.example.androidarchitecture.data.repository.NaverRepoImpl
import com.example.androidarchitecture.data.response.ImageData
import com.example.androidarchitecture.ui.base.BaseContract
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class ImageFragment : Fragment(), BaseContract.View<ImageData> {

    private lateinit var imageAdapter: ImageAdapter
    private val presenter by lazy { ImagePresent(this, NaverRepoImpl) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        activity?.let {
            imageAdapter = ImageAdapter()
                .also {
                    recycle.adapter = it
                    recycle.addItemDecoration(
                        DividerItemDecoration(
                            activity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
        }



        btn_search.setOnClickListener {
            if (edit_text != null) {
                presenter.requestList(edit_text.text.toString())
            }
        }
    }


    override fun renderItems(items: List<ImageData>) {
        imageAdapter.setData(items)
    }

    override fun errorToast(msg: String?) {
        msg?.let { requireContext().toast(it) }
    }

}
