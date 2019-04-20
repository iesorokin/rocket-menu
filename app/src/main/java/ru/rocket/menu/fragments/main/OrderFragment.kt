package ru.rocket.menu.fragments.main


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import ru.rocket.menu.R

class OrderFragment : Fragment() {

    //Text
    private var mTitle: TextView? = null
    private var mDescribe: TextView? = null
    private var mKind: TextView? = null
    private var mTime: TextView? = null
    private var mDate: TextView? = null

    private var mImage: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_order, container, false)

//        mTitle = v.findViewById(R.id.namePreviewEvent)
//        mDescribe = v.findViewById(R.id.describePreviewEvent)
//        mAddressView = v.findViewById(R.id.addressPreviewEvent)
//        mKind = v.findViewById(R.id.previewKind)
//        mTime = v.findViewById(R.id.previewTime)
//        mDate = v.findViewById(R.id.previewDate)
//        mImage = v.findViewById(R.id.previewImage)
//
//        var kind = EventActivity.sEventPreview.getKind()
//        if (kind == "Активный") {
//
//            kind += " отдых"
//
//        }
//
//        mKind!!.setText(kind)
//        mTime!!.setText(EventActivity.sEventPreview.getTime())
//        mDate!!.setText(EventActivity.sEventPreview.getDate())
//        mImage!!.setImageBitmap(getBitmap(EventActivity.sEventPreview.getImage()))
//
//        mTitle!!.setText(EventActivity.sEventPreview.getTitle())
//        mDescribe!!.setText(EventActivity.sEventPreview.getDescribe())

        return v

    }


    private fun getBitmap(image: ByteArray): Bitmap {

        return BitmapFactory.decodeByteArray(image, 0, image.size)

    }

}
