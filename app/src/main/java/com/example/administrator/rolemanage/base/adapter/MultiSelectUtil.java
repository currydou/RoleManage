package com.example.administrator.rolemanage.base.adapter;

public class MultiSelectUtil {

    public static boolean isMultipleMode(@Choice.ChoiceMode int choiceMode) {
        if (choiceMode == Choice.CHOICE_MODE_MULTI) {
            return true;
        } else if (choiceMode == Choice.CHOICE_MODE_NONE) {
            return false;
        }
        return false;
    }

}
