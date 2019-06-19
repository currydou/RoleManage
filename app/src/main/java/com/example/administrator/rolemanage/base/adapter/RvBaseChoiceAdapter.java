package com.example.administrator.rolemanage.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.rolemanage.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RecyclerView
 * 更新全部item（notifyDataSetChanged）
 * 分单选、多选(更新全部item)
 * 使用单选、多选逻辑，需要重写onBindViewHolder、onClick、getItem，并调用父类的方法具体见其他使用
 */

public abstract class RvBaseChoiceAdapter<T extends Choice> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {
    protected int mChoiceMode;
    protected List<T> mList;
    protected HashMap<String, Boolean> mCheckedMap = new HashMap<>();


    // TODO2: 2018/10/13  重新定义一个抽象方法，父类必须的代码，在子类用super限制太松。参考别的
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
        holder.itemView.setId(R.id.tag_item_view);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tag_item_view && mChoiceMode != Choice.CHOICE_MODE_NONE) {
            int position = (int) v.getTag();
            T item = getItem(position);
            if (item == null) {
                return;
            }
            if (mChoiceMode == Choice.CHOICE_MODE_SINGLE) {
                mCheckedMap.clear();
            }
            if (mCheckedMap.get(item.getCheckedId()) != null && mCheckedMap.get(item.getCheckedId())) {
                mCheckedMap.remove(item.getCheckedId());//移除掉，只保存选中的数据
            } else {
                mCheckedMap.put(item.getCheckedId(), true);
            }
            notifyDataSetChanged();
        }
    }

    protected T getItem(int position) {
        if (mList == null || mList.size() == 0) {
            return null;
        }
        return mList.get(position);
    }

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


    public int getChoiceMode() {
        return mChoiceMode;
    }

    /**
     * 设置选中的id
     * @param checkedId
     */
    public void setCheckedId(String checkedId) {
        if (checkedId == null) {
            return;
        }
        //先清空之前选中的
        mCheckedMap.clear();
        mCheckedMap.put(checkedId, true);
    }

    /**
     * 设置选中的id
     * @param ids
     */
    public void setCheckedIds(List<String> ids) {
        if (ids == null) {
            return;
        }
        //先清空之前选中的
        mCheckedMap.clear();
        for (String checkedId : ids) {
            if (checkedId == null) {
                continue;
            }
            mCheckedMap.put(checkedId, true);
        }
    }

    /**
     * 得到选中的id的集合
     *
     * @return
     */
    public List<String> getSelectedIdList() {
        List<String> idList = new ArrayList<>();
        for (Map.Entry<String, Boolean> item : mCheckedMap.entrySet()) {
            if (item.getValue()) {
                idList.add(item.getKey());
            }
        }
        return idList;
    }

}
