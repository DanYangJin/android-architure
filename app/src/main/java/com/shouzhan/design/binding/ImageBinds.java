package com.shouzhan.design.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author danbin
 * @version ImageBinds.java, v 0.1 2019-02-21 下午6:15 danbin
 */
public class ImageBinds {

    /**
     * 自定义图片绑定事件
     * https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1551108895&di=745684775de1e4b78f063fd0785ea90f&src=http://pic5.nipic.com/20100127/2177138_082501971985_2.jpg
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
