package com.shouzhan.design.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.callback.OnTakePhotoListener
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.dialog.*
import com.shouzhan.design.ui.home.contract.MainContract
import com.shouzhan.design.ui.home.presenter.MainPresenter
import com.shouzhan.design.utils.TakePhotoManager
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.lang3.StringUtils
import org.json.JSONObject
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


/**
 * @author danbin
 * @version MainActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class MainActivity : BaseActivity<ActivityMainBinding>(), OnTakePhotoListener, MainContract.View {

    private lateinit var presenter: MainPresenter

    private val takePhotoManager by lazy(LazyThreadSafetyMode.NONE) {
        TakePhotoManager(this, this)
    }

    private val dialogManager by lazy(LazyThreadSafetyMode.NONE) {
        DialogManager.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainPresenter(this, this, mBinding)
        getData()
    }

    override fun initView() {
        super.initView()
        select_btn.setOnClickListener(this)
        update_btn.setOnClickListener(this)
    }

    override fun getData() {
        showDialogFragment()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.select_btn -> {
                takePhotoManager.requestPickPhoto()
            }
            R.id.update_btn -> {
                presenter.showLiveData()
            }
        }
    }

    override fun onTakePath(path: String?) {
        Luban.with(this)
                .load(path)
                .ignoreBy(100)
                .filter { path -> !(StringUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                        Log.e("Catch", "onStart")
                    }

                    override fun onSuccess(file: File) {
                        Log.e("Catch", "onSuccess")
                        presenter.refreshHeadImage("file://" + file.absolutePath)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Catch", "onError: " + e.message)
                    }
                }).launch()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        takePhotoManager.onActivityResult(mContext, requestCode, resultCode, data)
    }

    private fun showDialogFragment() {
        lateinit var f1: AlertDialogFragment
        for (i in 1..10) {
            f1 = AlertDialogFragment.instantiate(mContext,
                    AlertDialogFragment::class.java.name) as AlertDialogFragment
            val b1 = CommonDialogBuilder.builder(mContext)
                    .setDialogCancel(R.string.common_cancel)
                    .setDialogMsg("哈哈哈$i")
                    .setDialogConfirm(R.string.common_confirm)
            f1.builder = b1
            var d1: PriorityQueueInfo = when {
                i % 2 == 0 -> PriorityQueueInfo(Priority.PRIORITY_HEIGHT, f1, "first_$i")
                i % 3 == 0 -> PriorityQueueInfo(f1, "first_$i")
                else -> PriorityQueueInfo(Priority.PRIORITY_LOW, f1, "first_$i")
            }
            dialogManager.pushToQueue(d1)
        }
    }


    fun dealJsonObject(jsonObject: JSONObject?, fail: () -> Unit) {
        jsonObject?.takeIf {
            jsonObject.has("city_info")
        }?.takeIf {
            with(it.getJSONObject("city_info")) {
                return@takeIf has("name") && has("data")
            }
        }?.let {
            it.getJSONObject("city_info")
        }.apply {

        } ?: fail()
    }

    fun readFile() {
        File("readme").readLines().forEach(::print)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

}
