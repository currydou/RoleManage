package com.example.administrator.rolemanage.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

/**
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {


    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

//    public abstract void setData(int position, Object data);

    public void setVisibility(boolean isVisible){
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
        //会为空，判断后，能隐藏掉
        if (param == null) {
            return;
        }
        if (isVisible){
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        }else{
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }

    /*---------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------*/

    public interface HolderListener {
        void delete(int position);
    }

    protected HolderListener mHoldListener;

    public void setHoldListener(HolderListener listener) {
        mHoldListener = listener;
    }
}