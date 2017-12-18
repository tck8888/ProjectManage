package com.healthmudi.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by dengdayi
 * Date: 2016/08/04 15：39
 * 解决ScrollView嵌入ListView 显示不全问题
 */
public class AutoListView extends ListView {
    public AutoListView(Context context) {
        super(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
