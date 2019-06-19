package com.example.administrator.rolemanage.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.rolemanage.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  RecyclerView
 *
 * 场景1：(每个item显示的是网络图片，优化视觉效果，不会闪动)
 * 只更新部分item，（notifyItemChanged）
 * 分单选、多选;
 * 使用单选、多选逻辑，需要重写onBindViewHolder、onClick、getItem，并调用父类的方法具体见其他使用
 *
 * (嵌套viewpager的，额外逻辑子类添加；
 * todo FeeTagSelectAdapter改造 的单选多选基类
 * @see FeeTagSelectAdapter )
 *
 */


public abstract class RvBaseChoiceAdapter2<T extends Choice> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {
    protected int mChoiceMode;
    protected List<T> mList;
    protected HashMap<String, Boolean> mCheckedMap = new HashMap<>();
    protected int mLastCheckedPosition;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
        holder.itemView.setId(R.id.tag_item_view);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (v.getId() == R.id.tag_item_view && mChoiceMode != Choice.CHOICE_MODE_NONE) {
            T item = getItem(position);
            if (item == null) {
                return;
            }
            if (mChoiceMode == Choice.CHOICE_MODE_SINGLE) {
                mCheckedMap.clear();
            }
            if (mCheckedMap.get(item.getCheckedId()) != null && mCheckedMap.get(item.getCheckedId())) {
                mCheckedMap.put(item.getCheckedId(), false);
            } else {
                mCheckedMap.put(item.getCheckedId(), true);
            }
            notifyItemChangedCustom(position);
            itemChecked(item);
        }
    }

    protected T getItem(int position) {
        if (mList == null || mList.size() == 0) {
            return null;
        }
        return mList.get(position);
    }

    public void notifyItemChangedCustom(int currentClickPosition) {
        //每次都刷新当前holder
        notifyItemChanged(currentClickPosition, "11");
        //简单处理，不管什么模式，都刷新上一个位置，影响不大
        notifyItemChanged(mLastCheckedPosition, "11");
        //刷新完后记录上一个的位置
        mLastCheckedPosition = currentClickPosition;
    }

    /*----------------------------------------setter、getter--------------------------------------------*/

    protected boolean isChecked(T t) {
        if (t == null) {
            return false;
        }
        Boolean selectedFlag = mCheckedMap.get(t.getCheckedId());
        return selectedFlag != null && selectedFlag;
    }

    /**
     * 设置单选还是多选模式
     *
     * @param mode
     */
    public void setChoiceMode(@Choice.ChoiceMode int mode) {
        mChoiceMode = mode;
    }

    /**
     * 设置选中的id
     * @param checkedId
     */
    public void setCheckedId(String checkedId) {
        if (checkedId != null) {
            //单选模式，
            if (mChoiceMode == Choice.CHOICE_MODE_SINGLE) {
                // 先清空之前选中的
                mCheckedMap.clear();
                //记录上一次的
                setCheckedPosition(checkedId);
            }
            mCheckedMap.put(checkedId, true);
        }
    }

    private void setCheckedPosition(String checkedId) {
        if (mList == null || mList.size() == 0) {
            return ;
        }
        //记录每一页的选中位置
        int position = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (TextUtils.equals(checkedId, mList.get(i).getCheckedId())) {
                position = i;
                break;
            }
        }
        mLastCheckedPosition = position;
    }

    /**
     * 设置选中的ids
     * @param ids
     */
    public void setCheckedIds(List<String> ids) {
        if (ids == null) {
            return;
        }
        //先清空之前选中的
        mCheckedMap.clear();
        for (String checkedId : ids) {
            setCheckedId(checkedId);
        }
    }

    /**
     * 得到选中的id的集合
     *
     * @return
     */
    public List<String> getCheckedIdList() {
        List<String> idList = new ArrayList<>();
        for (Map.Entry<String, Boolean> item : mCheckedMap.entrySet()) {
            if (item.getValue()) {
                idList.add(item.getKey());
            }
        }
        return idList;
    }


    /*---------------------------------------------------------------------------------*/

    protected void itemChecked(T t) {

    }

}
