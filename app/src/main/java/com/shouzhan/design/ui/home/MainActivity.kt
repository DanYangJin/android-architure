package com.shouzhan.design.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.fshows.android.stark.utils.FsLogUtil
import com.shouzhan.design.BR
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.callback.OnTakePhotoListener
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.dialog.DialogManager
import com.shouzhan.design.ui.home.contract.MainContract
import com.shouzhan.design.ui.home.presenter.MainPresenter
import com.shouzhan.design.ui.home.viewmodel.MainViewModel
import com.shouzhan.design.utils.TakePhotoManager
import org.apache.commons.lang3.StringUtils
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


/**
 * @author danbin
 * @version MainActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class MainActivity : BaseActivity<ActivityMainBinding>(), OnTakePhotoListener, MainContract.View {

    private lateinit var presenter: MainPresenter

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(MainViewModel::class.java)
    }

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
        presenter = MainPresenter(this, this, mBinding, viewModel)
        mBinding.setVariable(BR.presenter, this)
        getData()
    }

    override fun getData() {
//        dealRxJava2()
//        dealFastJson()
//        dealLruCache()
        dealDataStore()
    }

    override fun onClick(view: View) {
        FsLogUtil.error("TAG", "MainActivity onClick")
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

//    private fun showDialogFragment() {
//        lateinit var f1: AlertDialogFragment
//        for (i in 1..10) {
//            f1 = AlertDialogFragment.instantiate(mContext,
//                    AlertDialogFragment::class.java.name) as AlertDialogFragment
//            val b1 = DialogBuilder.builder(mContext)
//                    .setDialogCancel(com.shouzhan.design.R.string.common_cancel)
//                    .setDialogMsg("哈哈哈$i")
//                    .setDialogConfirm(com.shouzhan.design.R.string.common_confirm)
//            f1.builder = b1
//            var d1: PriorityQueueInfo = when {
//                i % 2 == 0 -> PriorityQueueInfo(Priority.PRIORITY_HEIGHT, f1, "first_$i")
//                i % 3 == 0 -> PriorityQueueInfo(f1, "first_$i")
//                else -> PriorityQueueInfo(Priority.PRIORITY_LOW, f1, "first_$i")
//            }
//            dialogManager.pushToQueue(d1)
//        }
//    }

    @SuppressLint("CheckResult")
    private fun dealRxJava2() {
//        Observable.create(ObservableOnSubscribe<Int> {
//            it.onNext(1)
//            it.onComplete()
//        }).map { t -> "this is result $t" }.subscribe(object : Observer<String> {
//            override fun onComplete() {
//                FsLogUtil.info("Catch", "onComplete")
//            }
//
//            override fun onSubscribe(d: Disposable) {
//                FsLogUtil.info("Catch", "onSubscribe")
//            }
//
//            override fun onError(e: Throwable) {
//                FsLogUtil.info("Catch", "onError")
//            }
//
//            override fun onNext(t: String) {
//                FsLogUtil.info("Catch", "onNext: $t")
//            }
//        })
//        Observable.zip(getStringObservable(), getIntObservable(),
//                BiFunction<String, Int, String> {
//                    p0, p1 -> p0 + p1
//                }
//        ).subscribe {
//            FsLogUtil.info("Catch", "zip value: $it")
//        }
//        Observable.concat(Observable.just(1, 2, 3),
//                Observable.just(2)).subscribe {
//            FsLogUtil.info("Catch", "concat value: $it")
//        }
//        Observable.create(ObservableOnSubscribe<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
//        }).concatMap {
//            val list = ArrayList<String>()
//            for (i in 0..2) {
//                list.add("I am value $i")
//            }
//            val delayTime = (1 + Math.random() * 10).toLong()
//            Observable.fromIterable<String>(list).delay(delayTime, TimeUnit.MILLISECONDS)
//        }.subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .subscribe {
//                    FsLogUtil.error("Catch", "this result is $it")
//                }
//        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
//                .distinct()
//                .subscribe {
//                    FsLogUtil.error("Catch", "this result is: $it")
//                }
//        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
//                .filter {
//                    it > 2
//                }
//                .subscribe {
//                    FsLogUtil.error("Catch", "this result is: $it")
//                }
//        Observable.just(1, 2, 3, 4, 5)
//                .buffer(3, 4)
//                .subscribe { integers ->
//                    Log.e("Catch", "buffer size : " + integers.size + "\n")
//                    for (i in integers) {
//                        Log.e("Catch", i.toString() + "")
//                    }
//                    Log.e("Catch", "\n")
//                }
//        Observable.interval(3, 2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe{
//                    Log.e("Catch", "timer : $it")
//                }

//        Observable.just(1, 2, 3)
//                .last(4)
//                .subscribe { integer ->
//                    Log.e("Catch", "last : $integer\n")
//                }

//        Observable.just(1, 2, 3)
//                .reduce(2, {
//                    integer, integer2 -> integer + integer2
//                })
//                .subscribe {
//                    integer -> Log.e("Catch", "accept: reduce : $integer\n")
//                }
    }

//    private fun getStringObservable() : Observable<String>{
//        return Observable.create {
//            it.onNext("A")
//            it.onNext("B")
//            it.onNext("C")
//            it.onNext("D")
//        }
//    }
//
//    private fun getIntObservable() : Observable<Int>{
//        return Observable.create {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
//        }
//    }


//    fun dealJsonObject(jsonObject: JSONObject?, fail: () -> Unit) {
//        jsonObject?.takeIf {
//            jsonObject.has("city_info")
//        }?.takeIf {
//            with(it.getJSONObject("city_info")) {
//                return@takeIf has("name") && has("data")
//            }
//        }?.let {
//            it.getJSONObject("city_info")
//        }.apply {
//
//        } ?: fail()
//    }

//    private fun dealFastJson() {
//        FsLogUtil.error("xss", JSON.toJSONString(
//                JsonStringTest("xsd")))
//        FsLogUtil.error("xss", JSON.toJSONString(
//                JsonStringKtTest("push", "15820798016")))
//    }

//    private fun dealLruCache() {
//        val linkedHashMap = LinkedHashMap<String, String>(0, 0.75f, true)
//        linkedHashMap["test1"] = "test1"
//        linkedHashMap["test2"] = "test2"
//        linkedHashMap["test3"] = "test3"
//        val value = linkedHashMap.put("test4", "test4")
//        logE("dealLruCache: " + (value == null))
//        logE(linkedHashMap.toString())
//        linkedHashMap["test2"]
//        logE(linkedHashMap.toString())
//    }

    private fun dealDataStore() {
        val dataStore: DataStore<Preferences> = createDataStore(
                name = "settings"
        )

    }

    override fun showLoading() {}

    override fun hideLoading() {}

}
