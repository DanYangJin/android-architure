package com.shouzhan.design.binding;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.databinding.BindingAdapter;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author danbin
 * @version ImageBinds.java, v 0.1 2019-02-21 下午6:15 danbin
 */
public class ImageBinds {

    /**
     * 自定义图片绑定事件
     *
     * @param draweeView
     * @param url
     */
    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void bindImgUrl(SimpleDraweeView draweeView, String url, Drawable drawable) {
        Uri uri = UriUtil.parseUriOrNull(url);
        draweeView.getHierarchy().setPlaceholderImage(drawable);
        draweeView.setImageURI(uri);
    }


}
