package com.example.administrator.rolemanage.utils.organized;

import android.content.Intent;

import com.example.administrator.rolemanage.constant.PerfUtilConstant;


/**
 * <p/>
 * 编辑者
 */
@SuppressWarnings("unused")
public class Editor {
    public enum Key {

        TITLE, CONTENT, SELECTED, HINT, INPUT_TYPE, UNIT, LENGTH

    }
    public static String getEditText(Intent data) {
        return data.getStringExtra(Key.CONTENT.name());
    }

    public static String getEditRemark(Intent data) {
        return data.getStringExtra(PerfUtilConstant.KEY_LONG_CONTENT);
    }

    public static String getEditLongContent(Intent data) {
        return getEditRemark(data);
    }

    public static String getEditUnit(Intent data) {
        return data.getStringExtra(Key.UNIT.name());
    }

//    public static void intentForEdit(
//            FkbBaseActivity activity, int title, int hint, String content, int maxLength, int requestCode) {
//        intentForEdit(activity, title, hint, content, null, maxLength, requestCode);
//    }
//
//    public static void intentForEdit(
//            FkbBaseActivity activity, int title, int hint, String content, Integer inputType, int maxLength, int requestCode) {
//        intentForEdit(activity, activity.getString(title), activity.getString(hint), content, inputType, false, maxLength, requestCode);
//    }
//
//    public static void intentForEdit(
//            FkbBaseActivity activity, int title, int hint, Integer inputType, int requestCode) {
//        intentForEdit(activity, title, hint, null, inputType, requestCode);
//    }
//
//    public static void intentForEdit(
//            FkbBaseActivity activity, int title, int hint, String content, Integer inputType, int requestCode) {
//        intentForEdit(activity, title, hint, content, inputType, false, requestCode);
//    }
//
//    public static void intentForEdit(
//            FkbBaseActivity activity, int title, int hint, String content, Integer inputType, boolean isSelectedAll, int requestCode) {
//        intentForEdit(activity, activity.getString(title), activity.getString(hint), content, inputType, isSelectedAll, requestCode);
//    }
//
//    public static void intentForEdit(
//            FkbBaseActivity activity, String title, String hint, String content, Integer inputType, int requestCode) {
//        intentForEdit(activity, title, hint, content, inputType, false, requestCode);
//    }
//
//    public static void intentForEdit(
//            FkbBaseActivity activity, String title, String hint, String content,
//            Integer inputType, boolean isSelectedAll, int requestCode) {
//        intentForEdit(activity, title, hint, content, inputType, isSelectedAll, 0, requestCode);
//    }

//    public static void intentForEdit(
//            FkbBaseActivity activity, String title, String hint, String content,
//            Integer inputType, boolean isSelectedAll, int maxLength, int requestCode) {
//        Bundle bundle = new Bundle();
//        bundle.putString(Key.TITLE.name(), title);
//        bundle.putString(Key.HINT.name(), hint);
//        bundle.putString(Key.CONTENT.name(), content);
//        if (inputType != null) {
//            bundle.putInt(Key.INPUT_TYPE.name(), inputType);
//        }
//        bundle.putBoolean(Key.SELECTED.name(), isSelectedAll);
//        bundle.putInt(Key.LENGTH.name(), maxLength);
//        activity.intentForResult(class, bundle, requestCode);
//    }

//    public static void intentForUnitEdit(
//            FkbBaseActivity activity, int title, int hint, String unit, Integer inputType, int requestCode) {
//        intentForUnitEdit(activity, activity.getString(title), activity.getString(hint), unit, inputType, requestCode);
//    }
//
//    public static void intentForUnitEdit(
//            FkbBaseActivity activity, String title, String hint, String unit,
//            Integer inputType, int requestCode) {
//        intentForUnitEdit(activity, title, hint, null, unit, inputType, requestCode);
//    }
//
//    public static void intentForUnitEdit(
//            FkbBaseActivity activity, int title, int hint, String content,
//            String unit, Integer inputType, int requestCode) {
//        intentForUnitEdit(activity, activity.getString(title), activity.getString(hint), content, unit, inputType, requestCode);
//    }
//
//    public static void intentForUnitEdit(
//            FkbBaseActivity activity, String title, String hint, String content,
//            String defUnit, Integer inputType, int requestCode) {
//        Bundle bundle = new Bundle();
//        bundle.putString(Key.TITLE.name(), title);
//        bundle.putString(Key.HINT.name(), hint);
//        bundle.putString(Key.CONTENT.name(), content);
//        if (inputType != null) {
//            bundle.putInt(Key.INPUT_TYPE.name(), inputType);
//        }
//        bundle.putString(HasUnitUnitKey.UNIT.name(), defUnit);
//        activity.intentForResult(HasUnitclass, bundle, requestCode);
//    }

//    public static void intentForEditRemark(
//            FkbBaseActivity activity, int title, int hint, String content, int number, int requestCode) {
//        intentForEditLongContent(activity, title, hint, content, number, EditLongContentActivity.Status.EDIT, requestCode);
//    }
//
//    public static void intentForEditLongContent(
//            FkbBaseActivity activity, int title, int hint, String content, int number,
//            EditLongContentActivity.Status status, int requestCode) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constant.KEY_EDIT_STATUS, EditLongContentActivity.Status.EDIT);
//        bundle.putString(Constant.KEY_TITLE, activity.getString(title));
//        bundle.putString(Constant.KEY_HINT, activity.getString(hint));
//        bundle.putString(Constant.KEY_CONTENT, content);
//        bundle.putInt(Constant.KEY_NUMBER, number);
//        bundle.putSerializable(Constant.KEY_EDIT_STATUS, status);
//        activity.intentForResult(EditLongContentActivity.class, bundle, requestCode);
//    }
}
