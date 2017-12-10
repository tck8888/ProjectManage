package com.healthmudi.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Description:
 * <p>
 * Created by tck on 2017/3/2.
 */

public abstract class BasicAdapter<T> extends BaseAdapter {

    public Context mContext;
    public List<T> mDataList;
    public LayoutInflater mInflater;

    public BasicAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    public List<T> getList() {
        return mDataList;
    }

    public void setList(List<T> list) {
        this.mDataList = list;
    }

    public void clear() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        if (null != list) {
            this.mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        if (null != mDataList) {
            this.mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflate(getContentView());
        }
        onInitView(convertView, position);
        return convertView;
    }


    private View inflate(int id) {
        View view = mInflater.inflate(id, null);
        return view;
    }

    @SuppressWarnings("unchecked")
    protected <E extends View> E get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);

        }
        return (E) childView;
    }

    protected abstract void onInitView(View convertView, int position);

    protected abstract int getContentView();


}
