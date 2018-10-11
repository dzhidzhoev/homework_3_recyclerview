package com.tinkoff.androidcourse;

interface ItemTouchAdapter {
    void onItemDismiss(int position);

    void onItemMoved(int index, int target);
}