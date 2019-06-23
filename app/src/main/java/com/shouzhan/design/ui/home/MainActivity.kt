package com.shouzhan.design.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.fshows.android.stark.utils.FileCacheUtil
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.callback.OnTakePhotoListener
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.extens.logE
import com.shouzhan.design.ui.home.viewmodel.MainViewModel
import com.shouzhan.design.utils.Constants
import com.shouzhan.design.utils.TakePhotoManager
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.lang3.StringUtils
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


/**
 * @author danbin
 * @version MainActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class MainActivity : BaseActivity<ActivityMainBinding>(), OnTakePhotoListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(MainViewModel::class.java)
    }

    private val takePhotoManager by lazy(LazyThreadSafetyMode.NONE) {
        TakePhotoManager(this, this)
    }

    override fun getLayoutId(): Int {
        return com.shouzhan.design.R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.vm = viewModel
        getData()
    }

    override fun initView() {
        super.initView()
        select_btn.setOnClickListener(this)
    }

    override fun getData() {
        viewModel.refreshHeadImage("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1551108895&di=745684775de1e4b78f063fd0785ea90f&src=http://pic5.nipic.com/20100127/2177138_082501971985_2.jpg")
    }

    override fun onClick(view: View) {
        when(view.id) {
            com.shouzhan.design.R.id.select_btn -> {
                takePhotoManager.requestPickPhoto()
            }
        }
    }

    override fun onTakePath(path: String?) {
        logE(path)
        Luban.with(this)
            .load(path)
            .setTargetDir(FileCacheUtil.getFileDirPath(mContext, Constants.DIR_IMAGE_CACHE))
            .ignoreBy(100)
            .filter { path -> !(StringUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                }

                override fun onSuccess(file: File) {
                    viewModel.refreshHeadImage("file://" + file.absolutePath)
                }

                override fun onError(e: Throwable) {
                }
            }).launch()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        takePhotoManager.onActivityResult(mContext, requestCode, resultCode, data)
    }

}
